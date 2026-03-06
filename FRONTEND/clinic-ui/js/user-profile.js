// Load user profile from user-service
async function loadUserProfile() {
    const userId = localStorage.getItem('userId');
    
    if (!userId) {
        console.error('No user ID found');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8081/users/${userId}`);
        
        if (!response.ok) {
            throw new Error('Failed to fetch user profile');
        }

        const user = await response.json();
        displayUserProfile(user);
    } catch (error) {
        console.error('Error loading profile:', error);
    }
}

function displayUserProfile(user) {
    // Update header
    document.getElementById('display-header-name').textContent = user.name || 'N/A';
    document.getElementById('display-name').textContent = user.name || 'N/A';
    
    // Update avatar initials
    if (user.name) {
        const parts = user.name.trim().split(' ');
        let initials = parts.length > 1 ? parts[0][0] + parts[parts.length - 1][0] : user.name.substring(0, 2);
        document.getElementById('display-avatar').textContent = initials.toUpperCase();
    }
    
    // Update profile fields
    document.getElementById('display-age').textContent = (user.age || 'N/A') + ' Years';
    document.getElementById('display-phone').textContent = user.phone || 'N/A';
    document.getElementById('display-address').textContent = user.address || 'N/A';
    
    // Update edit form
    document.getElementById('edit-name').value = user.name || '';
    document.getElementById('edit-phone').value = user.phone || '';
    document.getElementById('edit-age').value = user.age || '';
    document.getElementById('edit-address').value = user.address || '';
}

// Save profile changes to user-service
async function saveProfileChanges() {
    const userId = localStorage.getItem('userId');
    
    const updatedUser = {
        name: document.getElementById('edit-name').value,
        phone: document.getElementById('edit-phone').value,
        age: parseInt(document.getElementById('edit-age').value),
        address: document.getElementById('edit-address').value
    };

    try {
        const response = await fetch(`http://localhost:8081/users/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedUser)
        });

        if (!response.ok) {
            throw new Error('Failed to update profile');
        }

        const user = await response.json();
        displayUserProfile(user);
        document.getElementById('edit-profile-card').style.display = 'none';
        alert('Profile updated successfully!');
    } catch (error) {
        console.error('Error updating profile:', error);
        alert('Failed to update profile');
    }
}

// Load profile when page loads
document.addEventListener('DOMContentLoaded', loadUserProfile);
