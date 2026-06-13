// package: si.um.feri.aiv.rest

package si.um.feri.aiv.rest;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.um.feri.aiv.interfaces.DoctorManager;
import si.um.feri.aiv.vao.Doctor;

import java.util.List;

@Path("/doctors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

    @EJB
    private DoctorManager doctorManager;

    // GET /api/doctors - List all doctors
    @GET
    public List<Doctor> getAllDoctors() {
        return doctorManager.getAll();
    }

    // POST /api/doctors - Add new doctor
    @POST
    public Response addDoctor(Doctor doctor) {
        try {
            doctorManager.create(doctor.getName(), doctor.getSurname(), doctor.getEmail(), doctor.getMaxPatients());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error adding doctor: " + e.getMessage()).build();
        }
    }

    // PUT /api/doctors/{id} - Update doctor
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("id") int id, Doctor doctor) {
        try {
            doctorManager.update(
                    id,
                    doctor.getName(),
                    doctor.getSurname(),
                    doctor.getEmail(),
                    doctor.getMaxPatients()
            );
            return Response.ok().build(); // HTTP 200 OK
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error updating doctor: " + e.getMessage())
                    .build();
        }
    }

}
