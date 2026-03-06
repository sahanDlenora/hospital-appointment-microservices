async function loadDoctors() {

    try {

        const response = await fetch("http://localhost:8082/api/doctors");

        const doctors = await response.json();

        console.log(doctors);

        const tableBody = document.getElementById("doctorTableBody");

        tableBody.innerHTML = "";

        doctors.forEach(doc => {

            const initials = doc.name
                .split(" ")
                .map(word => word[0])
                .join("");

            const departmentName =
                doc.department ? doc.department.departmentName : "N/A";

            const row = `
                <tr>

                    <td>
                        <div class="flex items-center gap-3">

                            <div class="avatar"
                            style="width:40px;height:40px;background:var(--primary-light);">
                                ${initials}
                            </div>

                            <div>
                                <strong>${doc.name}</strong>
                            </div>

                        </div>
                    </td>

                    <td>${doc.specialization}</td>

                    <td>${departmentName}</td>

                    <td style="text-align:right">

                        <a href="view-schedule.html?doc=${doc.id}"
                        class="btn btn-outline">
                        View Schedule
                        </a>

                        <a href="book-appointment.html?doc=${doc.id}"
                        class="btn btn-primary">
                        Book Now
                        </a>

                    </td>

                </tr>
            `;

            tableBody.innerHTML += row;

        });

    } catch (error) {

        console.error("Error loading doctors:", error);

    }

}

window.onload = loadDoctors;