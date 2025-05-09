// package: si.um.feri.aiv.ejb

package si.um.feri.aiv.ejb;

import jakarta.ejb.Stateless;
import si.um.feri.aiv.vao.Patient;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import si.um.feri.aiv.interfaces.PatientManager;


@Stateless
public class PatientManagerBean implements PatientManager {
    //Create a map for patients
    private final Map<Integer, Patient> patients = new ConcurrentHashMap<>();
    private int nextId = 1; //inicialize id


    //Function to create a patient with the given data
    public void create(String name, String surname, String email, LocalDate dob, String details, String doctor) {
        Patient p = new Patient(nextId++, name, surname, email, dob, details, doctor);
        patients.put(p.getId(), p);
    }

    //Function  that updates a already existing user
    public void update(int id, String name, String surname, String email, LocalDate dob, String details, String doctor) {
        Patient p = patients.get(id); //get the patient by id
        if (p != null) { //If the patient exists then change their information
            p.setName(name);
            p.setSurname(surname);
            p.setEmail(email);
            p.setDateOfBirth(dob);
            p.setDetails(details);
            p.setDoctor(doctor);
        }
    }

    //Function to delete patient
    public void delete(int id) {
        patients.remove(id);
    }

    //Function that return patient by id
    public Patient read(int id) {
        return patients.get(id);
    }

    //Function to get a list of all patients with a doctor
    public List<Patient> getWithDoctor() {
        return patients.values().stream()
                .filter(p -> p.getDoctor() != null && !p.getDoctor().isBlank())
                .toList();
    }

    //Function to get a list of all patients
    public List<Patient> getAll() {
        return new ArrayList<>(patients.values());
    }

    @Override
    //Function to get a list of all patients without a doctor
    public List<Patient> getWithoutDoctor() {
        return patients.values().stream()
                .filter(p -> p.getDoctor() == null || p.getDoctor().isBlank())
                .toList();
    }

}
