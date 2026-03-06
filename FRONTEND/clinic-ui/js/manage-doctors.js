// =============================================================
// manage-doctors.js  —  Admin: Full CRUD for Doctors
// API Gateway : http://localhost:8083/api/doctors
// Auth        : JWT token + role read from localStorage
// =============================================================

const API_BASE = "http://localhost:8083/api/doctors";

let pendingDeleteId = null;

// ─────────────────────────────────────────────────────────────
// Auth headers
// ─────────────────────────────────────────────────────────────
function getHeaders() {
    return {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        role: localStorage.getItem("role") || "ADMIN",
    };
}

// ─────────────────────────────────────────────────────────────
// Toast
// ─────────────────────────────────────────────────────────────
function showToast(message, type = "success") {
    const toast = document.getElementById("toast");
    if (!toast) return;
    toast.textContent = message;
    toast.style.background = type === "success" ? "var(--success)" : "var(--danger)";
    toast.style.display = "block";
    clearTimeout(toast._t);
    toast._t = setTimeout(() => (toast.style.display = "none"), 3000);
}

// ─────────────────────────────────────────────────────────────
// Load & render table
// ─────────────────────────────────────────────────────────────
async function loadDoctors() {
    setTableContent(`
        <tr>
            <td colspan="4" style="text-align:center; padding:2rem; color:var(--text-muted);">
                Loading doctors…
            </td>
        </tr>`);

    try {
        const res = await fetch(API_BASE, { headers: getHeaders() });
        if (!res.ok) throw new Error(`Server error: ${res.status}`);

        const doctors = await res.json();

        if (!doctors.length) {
            setTableContent(`
                <tr>
                    <td colspan="4" style="text-align:center; padding:2rem; color:var(--text-muted);">
                        No doctors found. Click <strong>Add New Doctor</strong> to get started.
                    </td>
                </tr>`);
            return;
        }

        document.getElementById("doctorTableBody").innerHTML =
            doctors.map(buildRow).join("");

    } catch (err) {
        setTableContent(`
            <tr>
                <td colspan="4" style="text-align:center; padding:2rem; color:var(--danger);">
                    Failed to load doctors. ${err.message}
                </td>
            </tr>`);
        showToast("Failed to load doctors", "error");
    }
}

// ─────────────────────────────────────────────────────────────
// Build a single table row
// ─────────────────────────────────────────────────────────────
function buildRow(d) {
    const initials = (d.name || "?")
        .split(" ")
        .map((w) => w[0])
        .join("")
        .toUpperCase()
        .slice(0, 2);

    const deptName = d.department?.departmentName || "—";
    const deptCode = d.department?.departmentCode || "";

    // Build dept code span separately to avoid nested template literals
    const deptCodeHtml = deptCode
        ? '<span style="color:var(--text-muted); font-weight:400;">(' + deptCode + ')</span>'
        : "";

    return `
        <tr>
            <td>
                <div class="flex items-center gap-2">
                    <div class="avatar" style="width:32px; height:32px; font-size:0.8rem;">
                        ${initials}
                    </div>
                    <strong>Dr. ${d.name}</strong>
                </div>
            </td>
            <td>
                <span class="badge badge-info text-main"
                    style="background: rgba(14, 165, 233, 0.1);">
                    ${d.specialization}
                </span>
            </td>
            <td class="font-500">
                ${deptName} ${deptCodeHtml}
            </td>
            <td style="text-align:right;">
                <div class="flex gap-2" style="justify-content:flex-end;">
                    <button class="btn btn-outline"
                        style="padding:0.35rem 0.75rem; font-size:0.85rem;"
                        onclick="openEditForm(${d.doctorId})">Edit</button>
                    <button class="btn btn-danger"
                        style="padding:0.35rem 0.75rem; font-size:0.85rem;"
                        onclick="openDeleteModal(${d.doctorId})">Delete</button>
                </div>
            </td>
        </tr>`;
}

// ─────────────────────────────────────────────────────────────
// Open add form
// ─────────────────────────────────────────────────────────────
function openAddForm() {
    document.getElementById("formTitle").textContent = "Add New Doctor";
    document.getElementById("editDoctorId").value = "";
    resetForm();
    showCard();
}

// ─────────────────────────────────────────────────────────────
// Open edit form — fetch doctor data then populate fields
// ─────────────────────────────────────────────────────────────
async function openEditForm(id) {
    try {
        const res = await fetch(`${API_BASE}/${id}`, { headers: getHeaders() });
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const d = await res.json();

        document.getElementById("formTitle").textContent    = "Edit Doctor";
        document.getElementById("editDoctorId").value       = d.doctorId;
        document.getElementById("doctor-name").value        = d.name           || "";
        document.getElementById("specialization").value     = d.specialization || "";
        document.getElementById("department").value         = d.department?.id || "";
        document.getElementById("doc-email").value          = d.email          || "";
        document.getElementById("doc-phone").value          = d.phone          || "";
        hideFormError();
        showCard();

    } catch (err) {
        showToast("Could not load doctor details", "error");
    }
}

// ─────────────────────────────────────────────────────────────
// Show / hide the form card
// ─────────────────────────────────────────────────────────────
function showCard() {
    const card = document.getElementById("add-doctor-card");
    card.style.display = "block";
    card.scrollIntoView({ behavior: "smooth", block: "start" });
}

function hideCard() {
    document.getElementById("add-doctor-card").style.display = "none";
    resetForm();
}

function resetForm() {
    document.getElementById("addDoctorForm").reset();
    document.getElementById("editDoctorId").value = "";
    hideFormError();
}

// ─────────────────────────────────────────────────────────────
// Form submit — create or update
// ─────────────────────────────────────────────────────────────
async function handleFormSubmit(e) {
    e.preventDefault();

    const name           = document.getElementById("doctor-name").value.trim();
    const specialization = document.getElementById("specialization").value.trim();
    const departmentId   = parseInt(document.getElementById("department").value);
    const email          = document.getElementById("doc-email").value.trim();
    const phone          = document.getElementById("doc-phone").value.trim();
    const editId         = document.getElementById("editDoctorId").value;

    // Validate
    if (!name || !specialization || !departmentId) {
        showFormError("Name, specialization and department ID are required.");
        return;
    }

    const payload = { name, specialization, departmentId, email, phone };

    try {
        let res;

        if (editId) {
            // UPDATE
            res = await fetch(`${API_BASE}/${editId}`, {
                method: "PUT",
                headers: getHeaders(),
                body: JSON.stringify(payload),
            });
        } else {
            // CREATE
            res = await fetch(API_BASE, {
                method: "POST",
                headers: getHeaders(),
                body: JSON.stringify(payload),
            });
        }

        if (!res.ok) {
            const msg = await res.text();
            throw new Error(msg || `HTTP ${res.status}`);
        }

        showToast(editId ? "Doctor updated successfully" : "Doctor added successfully");
        hideCard();
        loadDoctors();

    } catch (err) {
        showFormError(`Error: ${err.message}`);
        showToast("Save failed", "error");
    }
}

// ─────────────────────────────────────────────────────────────
// Delete
// ─────────────────────────────────────────────────────────────
function openDeleteModal(id) {
    pendingDeleteId = id;
    const overlay = document.getElementById("deleteOverlay");
    overlay.style.display = "flex";
}

function closeDeleteModal() {
    pendingDeleteId = null;
    document.getElementById("deleteOverlay").style.display = "none";
}

async function confirmDelete() {
    if (!pendingDeleteId) return;
    try {
        const res = await fetch(`${API_BASE}/${pendingDeleteId}`, {
            method: "DELETE",
            headers: getHeaders(),
        });
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        showToast("Doctor deleted successfully");
        loadDoctors();
    } catch (err) {
        showToast(`Delete failed: ${err.message}`, "error");
    } finally {
        closeDeleteModal();
    }
}

// ─────────────────────────────────────────────────────────────
// Form error helpers
// ─────────────────────────────────────────────────────────────
function showFormError(msg) {
    const el = document.getElementById("formError");
    if (!el) return;
    el.textContent = msg;
    el.style.display = "block";
}

function hideFormError() {
    const el = document.getElementById("formError");
    if (!el) return;
    el.textContent = "";
    el.style.display = "none";
}

// ─────────────────────────────────────────────────────────────
// Helper
// ─────────────────────────────────────────────────────────────
function setTableContent(html) {
    const tbody = document.getElementById("doctorTableBody");
    if (!tbody) return;
    tbody.innerHTML = html;
}

// ─────────────────────────────────────────────────────────────
// Init
// ─────────────────────────────────────────────────────────────
// layout.js is async — it fetches navbar/sidebar/footer over
// HTTP and injects them after DOMContentLoaded fires.
// We listen for "layoutReady" which layout.js dispatches only
// after ALL components are fully injected into the DOM.
// ─────────────────────────────────────────────────────────────
document.addEventListener("layoutReady", function () {

    // Wire buttons
    document.getElementById("addDoctorBtn")
        .addEventListener("click", openAddForm);

    document.getElementById("closeAddDoctor")
        .addEventListener("click", hideCard);

    document.getElementById("addDoctorForm")
        .addEventListener("submit", handleFormSubmit);

    document.getElementById("confirmDeleteBtn")
        .addEventListener("click", confirmDelete);

    document.getElementById("cancelDeleteBtn")
        .addEventListener("click", closeDeleteModal);

    // Close delete overlay on backdrop click
    document.getElementById("deleteOverlay")
        .addEventListener("click", function (e) {
            if (e.target === this) closeDeleteModal();
        });

    // Load table data
    loadDoctors();
});