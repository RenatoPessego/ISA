// package: si.um.feri.aiv.servlets

package si.um.feri.aiv.servlets;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import si.um.feri.aiv.interfaces.RemotePatientQuery;
import si.um.feri.aiv.vao.Patient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//Servlet that remotely accesses the EJB to list patients without a doctor
@WebServlet("/remotePatients")
public class RemotePatientsServlet extends HttpServlet {

    @EJB
    private RemotePatientQuery remotePatientQuery;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Patient> patients = remotePatientQuery.getPatientsWithoutDoctor();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // HTML output
        out.println("<html>");
        out.println("<head><title>Remote Patients Without Doctor</title>");
        out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css'/>");
        out.println("</head><body class='container mt-4'>");

        out.println("<h1>Patients Without Doctor (Remote EJB)</h1>");
        if (patients.isEmpty()) {
            out.println("<p>No patients found.</p>");
        } else {
            out.println("<table class='table table-striped'>");
            out.println("<thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Date of Birth</th></tr></thead>");
            out.println("<tbody>");
            for (Patient p : patients) {
                out.printf("<tr><td>%d</td><td>%s %s</td><td>%s</td><td>%s</td></tr>",
                        p.getId(),
                        p.getName(),
                        p.getSurname(),
                        p.getEmail(),
                        p.getDateOfBirth());
            }
            out.println("</tbody></table>");
        }

        out.println("<a href='faces/views/patient.xhtml' class='btn btn-secondary mt-3'>Back to Main</a>");
        out.println("</body></html>");
    }
}
