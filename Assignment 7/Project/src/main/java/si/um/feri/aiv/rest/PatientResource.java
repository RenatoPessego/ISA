// package: si.um.feri.aiv.rest

package si.um.feri.aiv.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import jakarta.ws.rs.core.Response;
import si.um.feri.aiv.interfaces.PatientManager;
import si.um.feri.aiv.vao.Patient;

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @EJB
    private PatientManager patientManager;

    // Endpoint: GET /api/patients
    @GET
    public List<Patient> getAllPatients() {
        return patientManager.getAll();
    }
    // POST /api/patients - Add new patient from JSON
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient p) {
        try {
            patientManager.create(
                    p.getName(),
                    p.getSurname(),
                    p.getEmail(),
                    p.getDateOfBirth(),
                    p.getDetails(),
                    p.getDoctor() != null ? p.getDoctor() : null
            );
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error creating patient: " + e.getMessage()).build();
        }
    }
    // POST /api/patients/{id}/doctor/{doctorId}
    @POST
    @Path("/{id}/doctor/{doctorId}")
    public Response assignDoctorToPatient(@PathParam("id") int patientId, @PathParam("doctorId") int doctorId) {
        try {
            patientManager.assignDoctor(patientId, doctorId);
            return Response.ok().build(); // HTTP 200 OK
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error assigning doctor: " + e.getMessage()).build();
        }
    }

    // PUT /api/patients/{id} - Update existing patient
    @PUT
    @Path("/{id}")
    public Response updatePatient(@PathParam("id") int id, Patient p) {
        try {
            patientManager.update(
                    id,
                    p.getName(),
                    p.getSurname(),
                    p.getEmail(),
                    p.getDateOfBirth(),
                    p.getDetails(),
                    p.getDoctor()
            );
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error updating patient: " + e.getMessage())
                    .build();
        }
    }
}
