// Load all patients from user-service
async function loadAllPatients() {
    try {
        const response = await fetch('http://localhost:8081/users');
        
        if (!response.ok) {
            throw new Error('Failed to fetch patients');
        }

        const patients = await response.json();
        displayPatients(patients);
    } catch (error) {
        console.error('Error loading patients:', error);
    }
}

function displayPatients(patients) {
    const tbody = document.querySelector('table tbody');
    tbody.innerHTML = '';

    if (patients.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" style="text-align: center;">No patients found</td></tr>';
        return;
    }

    patients.forEach(patient => {
        const initials = getInitials(patient.name);
        const row = document.createElement('tr');
        row.innerHTML = `
            <td><span class="badge badge-neutral">PT-${patient.id}</span></td>
            <td>
                <div class="flex items-center gap-3">
                    <div class="avatar" style="width: 36px; height: 36px; font-size: 0.9rem;">${initials}</div>
                    <strong>${patient.name || 'N/A'}</strong>
                </div>
            </td>
            <td>
                <span style="display: block;">${patient.username || 'N/A'}</span>
                <span class="text-muted" style="font-size: 0.85rem;">${patient.phone || 'N/A'}</span>
            </td>
            <td style="text-align: right;">
                <div class="flex gap-2" style="justify-content: flex-end;">
                    <button class="btn btn-outline" style="padding: 0.35rem 0.75rem; font-size: 0.85rem;" onclick="editPatient(${patient.id})">Edit</button>
                    <button class="btn btn-danger" style="padding: 0.35rem 0.75rem; font-size: 0.85rem;" onclick="deletePatient(${patient.id})">Delete</button>
                </div>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function getInitials(name) {
    if (!name) return 'NA';
    const parts = name.trim().split(' ');
    return parts.length > 1 ? parts[0][0] + parts[parts.length - 1][0] : name.substring(0, 2);
}

async function deletePatient(patientId) {
    if (!confirm('Are you sure you want to delete this patient?')) {
        return;
    }

    try {
        const response = await fetch(`http://localhost:8081/users/${patientId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to delete patient');
        }

        alert('Patient deleted successfully!');
        loadAllPatients();
    } catch (error) {
        console.error('Error deleting patient:', error);
        alert('Failed to delete patient');
    }
}

function editPatient(patientId) {
    alert('Edit functionality - redirect to edit page or open modal');
}

// Load patients when page loads
document.addEventListener('DOMContentLoaded', loadAllPatients);
