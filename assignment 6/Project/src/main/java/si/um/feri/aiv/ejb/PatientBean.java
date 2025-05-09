// package: si.um.feri.aiv.ejb

package si.um.feri.aiv.ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import si.um.feri.aiv.interfaces.PatientInterface;
import si.um.feri.aiv.interfaces.PatientManager;
import si.um.feri.aiv.interfaces.DoctorManager;
import si.um.feri.aiv.vao.Patient;
import si.um.feri.aiv.vao.Doctor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named("patientBean")
@SessionScoped
public class PatientBean implements PatientInterface, Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PatientManager patientManager;

    @EJB
    private DoctorManager doctorManager;

    private Patient selectedPatient = new Patient();

    // Check if we're editing an existing patient
    @Override
    public boolean isEditing() {
        return selectedPatient != null && selectedPatient.getId() != 0;
    }

    // Save or update a patient
    @Override
    public String savePatient() {
        // Check for duplicate patient by name, surname, email and date
        for (Patient p : patientManager.getAll()) {
            if (p.getName().equalsIgnoreCase(selectedPatient.getName()) &&
                    p.getSurname().equalsIgnoreCase(selectedPatient.getSurname()) &&
                    p.getEmail().equalsIgnoreCase(selectedPatient.getEmail()) &&
                    p.getDateOfBirth().equals(selectedPatient.getDateOfBirth()) &&
                    p.getId() != selectedPatient.getId()) {

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "A patient with the same name, surname, email and date of birth already exists.", null));
                return null;
            }
        }

        Doctor assignedDoctor = selectedPatient.getDoctor();

        if (selectedPatient.getId() != 0) {
            // Update existing patient
            patientManager.update(
                    selectedPatient.getId(),
                    selectedPatient.getName(),
                    selectedPatient.getSurname(),
                    selectedPatient.getEmail(),
                    selectedPatient.getDateOfBirth(),
                    selectedPatient.getDetails(),
                    assignedDoctor
            );
        } else {
            // Create new patient
            patientManager.create(
                    selectedPatient.getName(),
                    selectedPatient.getSurname(),
                    selectedPatient.getEmail(),
                    selectedPatient.getDateOfBirth(),
                    selectedPatient.getDetails(),
                    assignedDoctor
            );
        }

        // Reset patient after save
        selectedPatient = new Patient(0, "", "", "", LocalDate.now(), "", null);
        return "patient?faces-redirect=true";
    }

    // Delete a patient
    @Override
    public void deletePatient(int id) {
        patientManager.delete(id);
    }

    // Load a patient for editing
    @Override
    public String editPatient(Patient patient) {
        selectedPatient = patient;
        return "patient?faces-redirect=true";
    }

    // View full details of a patient
    @Override
    public String viewPatientDetails(int id) {
        selectedPatient = patientManager.read(id);
        return "patientDetails?faces-redirect=true";
    }

    // Get list of patients with a doctor
    @Override
    public List<Patient> getPatientsWithDoctor() {
        return patientManager.getWithDoctor();
    }

    // Get list of patients without a doctor
    @Override
    public List<Patient> getPatientsWithoutDoctor() {
        return patientManager.getWithoutDoctor();
    }

    // Get currently selected patient
    @Override
    public Patient getSelectedPatient() {
        if (selectedPatient == null) {
            selectedPatient = new Patient(0, "", "", "", LocalDate.now(), "", null);
        }
        return selectedPatient;
    }

    // Set the selected patient
    @Override
    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    // Go to patient list
    @Override
    public String goToPatientList() {
        return "patient?faces-redirect=true";
    }

    // Go to patients without doctor
    @Override
    public String goToPatientWDList() {
        return "patientsWithoutDoctor?faces-redirect=true";
    }

    // Cancel editing
    @Override
    public void cancelEdit() {
        selectedPatient = new Patient(0, "", "", "", LocalDate.now(), "", null);
    }

    // Get list of all available doctors for dropdown
    public List<Doctor> getAllDoctors() {
        return doctorManager.getAvailableDoctors();
    }
}
