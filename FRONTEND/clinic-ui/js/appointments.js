// Fetch and display user appointments
async function loadUserAppointments() {
    const userId = localStorage.getItem('userId');
    
    if (!userId) {
        console.error('No user ID found');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8081/users/${userId}/appointments`);
        
        if (!response.ok) {
            throw new Error('Failed to fetch appointments');
        }

        const appointments = await response.json();
        displayAppointments(appointments);
    } catch (error) {
        console.error('Error loading appointments:', error);
    }
}

function displayAppointments(appointments) {
    const tbody = document.querySelector('table tbody');
    tbody.innerHTML = '';

    if (appointments.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">No appointments found</td></tr>';
        return;
    }

    appointments.forEach(appointment => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>
                <div class="flex items-center gap-2">
                    <div class="avatar" style="width: 32px; height: 32px; font-size: 12px;">DR</div>
                    <strong>Doctor ${appointment.doctorId}</strong>
                </div>
            </td>
            <td><span class="text-muted">Department</span></td>
            <td class="font-500">${new Date(appointment.appointmentDate).toLocaleDateString()}</td>
            <td>${appointment.appointmentTime || 'N/A'}</td>
            <td><span class="badge badge-${getStatusBadge(appointment.status)}">${appointment.status}</span></td>
            <td>
                ${appointment.status === 'CONFIRMED' ? 
                    '<button class="btn btn-danger" style="padding: 0.35rem 0.75rem; font-size: 0.85rem;">Cancel</button>' :
                    appointment.status === 'COMPLETED' ?
                    '<a href="feedback.html" class="btn btn-outline" style="padding: 0.35rem 0.75rem; font-size: 0.85rem; border-color: var(--warning); color: var(--warning);"><i>⭐</i> Leave Feedback</a>' :
                    ''
                }
            </td>
        `;
        tbody.appendChild(row);
    });
}

function getStatusBadge(status) {
    const statusMap = {
        'CONFIRMED': 'success',
        'PENDING': 'warning',
        'COMPLETED': 'neutral',
        'CANCELLED': 'danger'
    };
    return statusMap[status] || 'neutral';
}

// Load appointments when page loads
document.addEventListener('DOMContentLoaded', loadUserAppointments);
