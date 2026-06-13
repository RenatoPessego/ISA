package si.um.feri.aiv.ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import si.um.feri.aiv.interfaces.DoctorInterface;
import si.um.feri.aiv.interfaces.DoctorManager;
import si.um.feri.aiv.vao.Doctor;

import java.io.Serializable;
import java.util.List;

@Named("doctorBean")
@SessionScoped
public class DoctorBean implements DoctorInterface, Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private DoctorManager doctorManager;

    private Doctor selectedDoctor = new Doctor();

    @Override
    public boolean isEditing() {
        return selectedDoctor != null && selectedDoctor.getId() != 0;
    }

    @Override
    public String saveDoctor() {
        // check for duplicates based on email
        for (Doctor d : doctorManager.getAll()) {
            if (d.getEmail().equalsIgnoreCase(selectedDoctor.getEmail())
                    && d.getId() != selectedDoctor.getId()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "A doctor with the same email already exists.", null));
                return null;
            }
        }

        if (selectedDoctor.getId() != 0) {
            doctorManager.update(
                    selectedDoctor.getId(),
                    selectedDoctor.getName(),
                    selectedDoctor.getSurname(),
                    selectedDoctor.getEmail(),
                    selectedDoctor.getMaxPatients()
            );
        } else {
            doctorManager.create(
                    selectedDoctor.getName(),
                    selectedDoctor.getSurname(),
                    selectedDoctor.getEmail(),
                    selectedDoctor.getMaxPatients()
            );
        }

        selectedDoctor = new Doctor(0, "", "", "", 0);
        return "doctors?faces-redirect=true";
    }

    @Override
    public void deleteDoctor(int id) {
        doctorManager.delete(id);
    }

    @Override
    public String editDoctor(Doctor doctor) {
        selectedDoctor = doctor;
        return "doctors?faces-redirect=true";
    }

    @Override
    public Doctor getSelectedDoctor() {
        if (selectedDoctor == null) {
            selectedDoctor = new Doctor(0, "", "", "", 0);
        }
        return selectedDoctor;
    }

    @Override
    public void setSelectedDoctor(Doctor doctor) {
        this.selectedDoctor = doctor;
    }

    @Override
    public void cancelEdit() {
        selectedDoctor = new Doctor(0, "", "", "", 0);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorManager.getAll();
    }
}
