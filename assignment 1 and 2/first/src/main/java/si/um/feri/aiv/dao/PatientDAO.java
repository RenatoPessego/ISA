
package si.um.feri.aiv.dao;

import si.um.feri.aiv.vao.Patient;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

 //DAO class to simulate database access for patients.
public class PatientDAO {
    private final Map<Integer, Patient> patients = new HashMap<>(); // Map that stores patients
    private int nextId = 1; //actual new patient id

     //Fucntion to create the patient on the "database"
    public Patient create(String name, String surname, String email, LocalDate dateOfBirth, String details, String doctor) {
        Patient patient = new Patient(nextId++, name, surname, email, dateOfBirth, details, doctor);
        patients.put(patient.getId(), patient);
        return patient;
    }

    //function to read a patient from the database
    public Patient read(int id) {
        return patients.get(id);
    }

    //function to read all of the patients from the list
    public List<Patient> readAll() {
        return new ArrayList<>(patients.values());
    }


    //funciton that updates a patient, if selected
    public boolean update(int id, String name, String surname, String email, LocalDate dateOfBirth, String details, String doctor) {
        Patient patient = patients.get(id);
        if (patient != null) {
            patient.setName(name);
            patient.setSurname(surname);
            patient.setEmail(email);
            patient.setDateOfBirth(dateOfBirth);
            patient.setDetails(details);
            patient.setDoctor(doctor);
            return true;
        }
        return false;
    }

    public boolean delete(int id) { //function that deletes a patient by id
        return patients.remove(id) != null;
    }

    public List<Patient> getWithDoctor() { //function that return all patients with doctor, applying a filter to the list
        return patients.values().stream()
                .filter(p -> p.getDoctor() != null && !p.getDoctor().trim().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Patient> getWithoutDoctor() { //the same as the function above but for patients without doctor
        return patients.values().stream()
                .filter(p -> p.getDoctor() == null || p.getDoctor().trim().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Patient> getAll() { //function that return every patient from the list
        return new ArrayList<>(patients.values());
    }

}
