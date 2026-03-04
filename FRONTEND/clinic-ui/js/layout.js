document.addEventListener("DOMContentLoaded", async () => {
    // Determine which layout to load based on current page
    const currentPath = window.location.pathname;
    const currentPage = currentPath.substring(currentPath.lastIndexOf("/") + 1).replace(".html", "");
    const isAdmin = currentPage.startsWith("admin") || currentPage.startsWith("manage");
    const isPatient = currentPage.startsWith("patient") || currentPage.startsWith("view") || currentPage.startsWith("book") || currentPage.startsWith("my-") || currentPage === "feedback" || currentPage === "patient-profile";

    if (isAdmin || isPatient) {
        // Load Navbar
        const navbarContainer = document.getElementById("navbar");
        if (navbarContainer) {
            try {
                const response = await fetch("../components/navbar.html");
                navbarContainer.innerHTML = await response.text();
            } catch (error) {
                console.error("Error loading navbar:", error);
            }
        }

        // Load Sidebar
        const sidebarContainer = document.getElementById("sidebar");
        if (sidebarContainer) {
            try {
                const sidebarFile = isAdmin ? "sidebar-admin.html" : "sidebar-patient.html";
                const response = await fetch(`../components/${sidebarFile}`);
                sidebarContainer.innerHTML = await response.text();

                // Set active menu item
                const activeLink = document.querySelector(`.sidebar-menu a[data-page="${currentPage}"]`);
                if (activeLink) {
                    activeLink.classList.add("active");
                }
            } catch (error) {
                console.error("Error loading sidebar:", error);
            }
        }

        // Load Footer
        const footerContainer = document.getElementById("footer");
        if (footerContainer) {
            try {
                const response = await fetch("../components/footer.html");
                footerContainer.innerHTML = await response.text();
            } catch (error) {
                console.error("Error loading footer:", error);
            }
        }
    }

    // Mobile Sidebar Toggle (delegated since navbar is loaded dynamically)
    document.body.addEventListener("click", (e) => {
        if (e.target.id === "mobile-menu-toggle") {
            const sidebar = document.querySelector(".sidebar:not(#sidebar > .sidebar) ") || document.querySelector("#sidebar .sidebar"); // Depending on how it mounts
            if (sidebar) {
                sidebar.classList.toggle("open");
            }
        }
    });
});
