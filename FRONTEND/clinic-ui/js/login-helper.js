const AUTH_API_URL = "http://localhost:8080"; // User Service URL

async function handleLogin(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    if (!email || !password) {
        showLoginError("Please enter email and password.");
        return;
    }

    try {
        // ⚠️ ඔයාගේ User Service login endpoint එකට match කරන්න
        const response = await fetch(`${AUTH_API_URL}/users/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const user = await response.json();

            // ✅ userId සහ user info localStorage save කරන්න
            localStorage.setItem('userId', user.id);
            localStorage.setItem('userName', user.name);
            localStorage.setItem('userRole', user.role || 'PATIENT');

            // Role based redirect
            if (user.role === 'ADMIN') {
                window.location.href = "admin-dashboard.html";
            } else {
                window.location.href = "patient-dashboard.html";
            }

        } else {
            showLoginError("Invalid email or password.");
        }

    } catch (error) {
        console.error('Login error:', error);
        showLoginError("Cannot connect to server. Make sure backend is running.");
    }
}

function handleLogout() {
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
    localStorage.removeItem('userRole');
    window.location.href = "login.html";
}

function showLoginError(message) {
    const errDiv = document.getElementById('login-error');
    if (errDiv) {
        errDiv.textContent = message;
        errDiv.style.display = 'block';
    } else {
        alert(message);
    }
}