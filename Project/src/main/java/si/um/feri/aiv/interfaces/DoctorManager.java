// package: si.um.feri.aiv.interfaces

package si.um.feri.aiv.interfaces;

import jakarta.ejb.Local;
import si.um.feri.aiv.vao.Doctor;
import java.util.List;

@Local
public interface DoctorManager {

    void create(String name, String surname, String email, int maxPatients);

    void update(int id, String name, String surname, String email, int maxPatients);

    void delete(int id);

    Doctor read(int id);

    List<Doctor> getAll();

    List<Doctor> getAvailableDoctors();
}
