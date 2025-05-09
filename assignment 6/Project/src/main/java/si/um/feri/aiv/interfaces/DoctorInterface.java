package si.um.feri.aiv.interfaces;

import si.um.feri.aiv.vao.Doctor;
import java.util.List;

// Interface for managing doctors in JSF
public interface DoctorInterface {
    Doctor getSelectedDoctor();
    void setSelectedDoctor(Doctor doctor);
    String saveDoctor();
    void deleteDoctor(int id);
    String editDoctor(Doctor doctor);
    boolean isEditing();
    void cancelEdit();
    List<Doctor> getAllDoctors();
}
