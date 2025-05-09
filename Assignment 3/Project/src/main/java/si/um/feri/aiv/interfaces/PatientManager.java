package si.um.feri.aiv.interfaces;

import si.um.feri.aiv.vao.Patient;
import java.time.LocalDate;
import java.util.List;

import jakarta.ejb.Local;

//Local interface for managing patients inside the same application

@Local
public interface PatientManager {
    void create(String name, String surname, String email, LocalDate dateOfBirth, String details, String doctor);
    void update(int id, String name, String surname, String email, LocalDate dateOfBirth, String details, String doctor);
    void delete(int id);
    Patient read(int id);
    List<Patient> getAll();
    List<Patient> getWithDoctor();
    List<Patient> getWithoutDoctor();
}
