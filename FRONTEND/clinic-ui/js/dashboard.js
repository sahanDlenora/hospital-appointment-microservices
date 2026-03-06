document.addEventListener("DOMContentLoaded", () => {
    // Feedback star rating
    const stars = document.querySelectorAll(".rating-stars .star");
    if (stars.length > 0) {
        stars.forEach((star, index) => {
            star.style.cursor = "pointer";
            star.addEventListener("click", () => {
                stars.forEach((s, i) => {
                    if (i <= index) {
                        s.innerHTML = "★";
                        s.style.color = "#facc15";
                    } else {
                        s.innerHTML = "☆";
                        s.style.color = "#ccc";
                    }
                });
            });

            // Initial styling for outline stars
            if (star.innerHTML.trim() === "☆") {
                star.style.color = "#ccc";
            }
        });
    }

    // ── Forms that are managed by their own JS files ──────────
    // dashboard.js must NOT intercept these — they handle their
    // own submit events and call e.preventDefault() themselves.
    const managedForms = [
        "addDoctorForm",
        "addPatientForm",
        "addScheduleForm",
        "addDepartmentForm",
        "addAppointmentForm",
    ];

    const forms = document.querySelectorAll("form");
    forms.forEach(form => {
        // Skip any form that is managed by its own page JS
        if (managedForms.includes(form.id)) return;

        form.addEventListener("submit", (e) => {
            const isAuthForm = form.closest(".auth-card") !== null;
            if (isAuthForm) {
                return;
            }

            // Appointment Booking Logic
            if (form.action.includes("my-appointments.html") && document.getElementById("doctor")) {
                e.preventDefault();

                const doctorSelect = document.getElementById("doctor");
                const dateInput    = document.getElementById("date");
                const timeSelect   = document.getElementById("time");

                if (doctorSelect && dateInput && timeSelect) {
                    const doctorId = doctorSelect.value;
                    const date     = dateInput.value;
                    const time     = timeSelect.value;

                    if (doctorId && date && time) {
                        const bookingKey = `booked_${doctorId}_${date}_${time}`;
                        localStorage.setItem(bookingKey, "true");
                        alert("Appointment Successfully Booked!\nThis time slot will now be unavailable for others.");
                        window.location.href = form.action;
                        return;
                    }
                }
            }

            e.preventDefault();
            form.reset();
        });
    });

    // Handle Book Appointment Page - Disable booked slots
    const bookAppointmentForm = document.querySelector('form[action="my-appointments.html"]');
    if (bookAppointmentForm && document.getElementById("doctor")) {
        const doctorSelect = document.getElementById("doctor");
        const dateInput    = document.getElementById("date");
        const timeSelect   = document.getElementById("time");

        function updateAvailableTimeSlots() {
            const doctorId = doctorSelect.value;
            const date     = dateInput.value;

            if (!doctorId || !date) return;

            Array.from(timeSelect.options).forEach(option => {
                if (option.value === "") return;

                const bookingKey = `booked_${doctorId}_${date}_${option.value}`;
                const isBooked   = localStorage.getItem(bookingKey) === "true";

                if (isBooked) {
                    option.disabled = true;
                    if (!option.text.includes("(Booked)")) {
                        option.text = option.text + " (Booked)";
                    }
                    option.style.color = "#ef4444";
                } else {
                    option.disabled  = false;
                    option.text      = option.text.replace(" (Booked)", "");
                    option.style.color = "";
                }
            });

            if (timeSelect.options[timeSelect.selectedIndex].disabled) {
                timeSelect.value = "";
            }
        }

        doctorSelect.addEventListener("change", updateAvailableTimeSlots);
        dateInput.addEventListener("change", updateAvailableTimeSlots);
        updateAvailableTimeSlots();
    }
});