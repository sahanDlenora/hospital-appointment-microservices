// Backend API URL
const API_URL = "http://localhost:8083/api/departments";


// Load all departments when page loads
function loadDepartments() {

    fetch(API_URL)
        .then(response => response.json())
        .then(data => {

            const tableBody = document.getElementById("departmentTable");

            // Clear table before adding new rows
            tableBody.innerHTML = "";

            data.forEach(dept => {

                const row = `
                <tr>
                    <td>${dept.id}</td>
                    <td><strong>${dept.departmentName}</strong></td>
                    <td style="text-align:right;">
                        <button onclick="deleteDepartment(${dept.id})" class="btn btn-danger">
                            Delete
                        </button>
                    </td>
                </tr>
                `;

                tableBody.innerHTML += row;

            });

        })
        .catch(error => console.error("Error loading departments:", error));

}


// Add new department
function addDepartment() {

    const name = document.getElementById("dept-name").value;

    if (!name) {
        alert("Please enter department name");
        return;
    }

    fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "role": "ADMIN"
        },
        body: JSON.stringify({
            departmentName: name,
            departmentCode: "",
            departmentDescription: ""
        })
    })
    .then(response => response.json())
    .then(() => {

        // Clear input field
        document.getElementById("dept-name").value = "";

        // Hide add form
        document.getElementById("add-dept-card").style.display = "none";

        // Reload department list
        loadDepartments();

    })
    .catch(error => console.error("Error adding department:", error));

}


// Delete department
function deleteDepartment(id) {

    if (!confirm("Are you sure you want to delete this department?")) {
        return;
    }

    fetch(API_URL + "/" + id, {
        method: "DELETE",
        headers: {
            "role": "ADMIN"
        }
    })
    .then(() => {

        // Reload list after delete
        loadDepartments();

    })
    .catch(error => console.error("Error deleting department:", error));

}


// Load departments when page loads
window.onload = loadDepartments;