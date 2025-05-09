package si.um.feri.aiv.interfaces;

import si.um.feri.aiv.vao.Patient;

import java.util.List;


//Interface for the backend
public interface PatientInterface {
    Patient getSelectedPatient();
    void setSelectedPatient(Patient patient);
    String savePatient();
    void deletePatient(int id);
    String editPatient(Patient patient);
    String viewPatientDetails(int id);
    List<Patient> getPatientsWithDoctor();
    List<Patient> getPatientsWithoutDoctor();
    boolean isEditing();
    void cancelEdit();
    String goToPatientList();
    String goToPatientWDList();
}

