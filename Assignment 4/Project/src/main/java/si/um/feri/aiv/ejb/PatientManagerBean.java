// package: si.um.feri.aiv.ejb

package si.um.feri.aiv.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import si.um.feri.aiv.interfaces.PatientManager;
import si.um.feri.aiv.vao.Patient;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class PatientManagerBean implements PatientManager {

    // Inject the EntityManager from the persistence unit
    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public void create(String name, String surname, String email, LocalDate dob, String details, String doctor) {
        Patient p = new Patient(0, name, surname, email, dob, details, doctor);
        em.persist(p); // Saves to DB
    }

    @Override
    public void update(int id, String name, String surname, String email, LocalDate dob, String details, String doctor) {
        Patient existing = em.find(Patient.class, id); // Find by primary key
        if (existing != null) {
            existing.setName(name);
            existing.setSurname(surname);
            existing.setEmail(email);
            existing.setDateOfBirth(dob);
            existing.setDetails(details);
            existing.setDoctor(doctor);
            em.merge(existing); // Update in DB
        }
    }

    @Override
    public void delete(int id) {
        Patient p = em.find(Patient.class, id);
        if (p != null) {
            em.remove(p); // Remove from DB
        }
    }

    @Override
    public Patient read(int id) {
        return em.find(Patient.class, id);
    }

    @Override
    public List<Patient> getWithDoctor() {
        return em.createQuery("SELECT p FROM Patient p WHERE p.doctor IS NOT NULL AND p.doctor <> ''", Patient.class)
                .getResultList();
    }

    @Override
    public List<Patient> getAll() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }

    @Override
    public List<Patient> getWithoutDoctor() {
        return em.createQuery("SELECT p FROM Patient p WHERE p.doctor IS NULL OR p.doctor = ''", Patient.class)
                .getResultList();
    }
}
