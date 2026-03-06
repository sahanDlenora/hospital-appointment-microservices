
const API_BASE = "http://localhost:8083/api/doctors";

let allDoctors = [];


// Auth headers

function getHeaders() {
    return {
        "Content-Type": "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        role: localStorage.getItem("role") || "PATIENT",
    };
}


// Load doctors from API

async function loadDoctors() {
    setTableContent(`
        <tr>
            <td colspan="3" style="text-align: center; padding: 2rem; color: var(--text-muted);">
                Loading doctors...
            </td>
        </tr>`);

    try {
        const res = await fetch(API_BASE, { headers: getHeaders() });
        if (!res.ok) throw new Error(`Server error: ${res.status}`);

        allDoctors = await res.json();
        populateDeptFilter();
        renderTable(allDoctors);

    } catch (err) {
        setTableContent(`
            <tr>
                <td colspan="3" style="text-align: center; padding: 2rem; color: var(--danger);">
                    Failed to load doctors. ${err.message}
                </td>
            </tr>`);
    }
}


// Build department dropdown from API data

function populateDeptFilter() {
    const select = document.getElementById("deptFilter");
    if (!select) return;
    const seen = new Set();

    allDoctors.forEach((d) => {
        const name = d.department?.departmentName;
        if (name && !seen.has(name)) {
            seen.add(name);
            const opt       = document.createElement("option");
            opt.value       = name;
            opt.textContent = name;
            select.appendChild(opt);
        }
    });
}


// Filter — fires on search keypress or dept dropdown change

function filterDoctors() {
    const query = document.getElementById("searchInput")?.value.toLowerCase().trim() || "";
    const dept  = document.getElementById("deptFilter")?.value || "";

    const filtered = allDoctors.filter((d) => {
        const matchSearch = !query || d.name.toLowerCase().includes(query);
        const matchDept   = !dept  || d.department?.departmentName === dept;
        return matchSearch && matchDept;
    });

    renderTable(filtered);
}


// Render rows into tbody

function renderTable(doctors) {
    if (!doctors.length) {
        setTableContent(`
            <tr>
                <td colspan="3" style="text-align: center; padding: 2rem; color: var(--text-muted);">
                    No doctors found matching your search.
                </td>
            </tr>`);
        return;
    }

    document.getElementById("doctorTableBody").innerHTML =
        doctors.map(buildRow).join("");
}


// Build a single table row

function buildRow(d) {
    const initials = (d.name || "?")
        .split(" ")
        .map((w) => w[0])
        .join("")
        .toUpperCase()
        .slice(0, 2);

    const deptName = d.department?.departmentName || "—";
    const deptCode = d.department?.departmentCode || "";

    return `
        <tr>
            <td>
                <div class="flex items-center gap-2">
                    <div class="avatar" style="width: 32px; height: 32px; font-size: 0.8rem;">
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
                ${deptName}
                ${deptCode
                    ? `<span style="color: var(--text-muted); font-weight: 400;">(${deptCode})</span>`
                    : ""}
            </td>
        </tr>`;
}

// Helper — safely write HTML into tbody

function setTableContent(html) {
    const tbody = document.getElementById("doctorTableBody");
    if (!tbody) {
        console.error("[doctor.js] #doctorTableBody not found in DOM. Check view-doctor.html has no conflicting <main> tags.");
        return;
    }
    tbody.innerHTML = html;
}


document.addEventListener("layoutReady", function () {
    const searchInput = document.getElementById("searchInput");
    const deptFilter  = document.getElementById("deptFilter");

    if (searchInput) searchInput.addEventListener("input", filterDoctors);
    if (deptFilter)  deptFilter.addEventListener("change", filterDoctors);

    loadDoctors();
});