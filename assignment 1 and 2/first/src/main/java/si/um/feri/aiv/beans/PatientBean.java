package si.um.feri.aiv.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import si.um.feri.aiv.dao.PatientDAO;
import si.um.feri.aiv.interfaces.PatientInterface;
import si.um.feri.aiv.vao.Patient;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * JSF Managed Bean that implements PatientInterface.
 * Handles all logic related to patients.
 */
@Named("patientBean")
@SessionScoped
//Bean using interface
public class PatientBean implements PatientInterface, Serializable {

    private static final long serialVersionUID = 1L; //used to serialize if needed

    private PatientDAO patientDAO = new PatientDAO(); //inicialize patientDAO

    // The patient currently being created or edited
    private Patient selectedPatient = new Patient(); // inicialize patient

    @Override
    public boolean isEditing() {
        return selectedPatient != null && selectedPatient.getId() != 0;
    } //return if a patient is being edited

    @Override
    public String savePatient() {
        // loop through all existing patients to check for duplicates
        for (Patient p : patientDAO.getAll()) {
            //compare name, surname, email and date of birth (ignoring case for strings)
            if (p.getName().equalsIgnoreCase(selectedPatient.getName()) &&
                    p.getSurname().equalsIgnoreCase(selectedPatient.getSurname()) &&
                    p.getEmail().equalsIgnoreCase(selectedPatient.getEmail()) &&
                    p.getDateOfBirth().equals(selectedPatient.getDateOfBirth()) &&
                    p.getId() != selectedPatient.getId()) { // don't compare with the same patient (in case of update)

                // if found, show a message and stop saving
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "A patient with the same name, surname, email and date of birth already exists.", null));
                return null; // stay on the same page
            }
        }

        //if editing an existing patient id won't be  0
        if (selectedPatient.getId() != 0) {
            patientDAO.update( //update values
                    selectedPatient.getId(),
                    selectedPatient.getName(),
                    selectedPatient.getSurname(),
                    selectedPatient.getEmail(),
                    selectedPatient.getDateOfBirth(),
                    selectedPatient.getDetails(),
                    selectedPatient.getDoctor()
            );
        } else {
            //if adding a new patient
            patientDAO.create( //create the patient with given values
                    selectedPatient.getName(),
                    selectedPatient.getSurname(),
                    selectedPatient.getEmail(),
                    selectedPatient.getDateOfBirth(),
                    selectedPatient.getDetails(),
                    selectedPatient.getDoctor()
            );
        }

        //reset the form (create new blank patient)
        selectedPatient = new Patient(0, "", "", "", LocalDate.now(), "", "");

        // Redirect to refresh the page and show updated table
        return "patient?faces-redirect=true";
    }

    @Override //function that deletes patients
    public void deletePatient(int id) {
        patientDAO.delete(id);
    }

    @Override
    public String editPatient(Patient patient) { //function that edits patients
        selectedPatient = patient;
        return "patient?faces-redirect=true";
    }

    @Override //function that redirects to view details page of patients
    public String viewPatientDetails(int id) {
        selectedPatient = patientDAO.read(id);
        return "patientDetails?faces-redirect=true";
    }

    @Override
    public List<Patient> getPatientsWithDoctor() {
        return patientDAO.getWithDoctor();
    } //function that return patients without doctor

    @Override
    //function that return patients with doctor
    public List<Patient> getPatientsWithoutDoctor() {
        return patientDAO.getWithoutDoctor();
    }


    @Override
    public Patient getSelectedPatient() { //function that returns selected patient or a empty one if none is selected
        if (selectedPatient == null) {
            selectedPatient = new Patient(0, "", "", "", LocalDate.now(), "", "");
        }
        return selectedPatient;
    }


    @Override
    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    } //Set the selected patient

    @Override
    public String goToPatientList() {
        return "patient?faces-redirect=true";
    } //redirect to patient list (main page)

    @Override
    public String goToPatientWDList() {
        return "patientsWithoutDoctor?faces-redirect=true";
    } //redirect to patient without doctor page

    @Override
    public void cancelEdit() { //Function to cancel editing of a patient
        selectedPatient = new Patient(0, "", "", "", LocalDate.now(), "", "");
    }
}
