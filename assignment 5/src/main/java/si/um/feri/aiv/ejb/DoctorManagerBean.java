// package: si.um.feri.aiv.ejb

package si.um.feri.aiv.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import si.um.feri.aiv.interfaces.DoctorManager;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;

import java.util.List;

@Stateless
public class DoctorManagerBean implements DoctorManager {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public void create(String name, String surname, String email, int maxPatients) {
        Doctor doctor = new Doctor(0, name, surname, email, maxPatients);
        em.persist(doctor);
    }

    public void update(int id, String name, String surname, String email, int maxPatients) {
        Doctor d = em.find(Doctor.class, id);
        if (d != null) {
            d.setName(name);
            d.setSurname(surname);
            d.setEmail(email);
            d.setMaxPatients(maxPatients);

            // Remove patients if they exceed the new maximum
            while (d.getPatients().size() > maxPatients) {
                // Remove the last patient (could be any logic - here, we remove from the end)
                Patient p = d.getPatients().get(d.getPatients().size() - 1);
                p.setDoctor(null);
                em.merge(p);
                d.getPatients().remove(p);
            }

            em.merge(d);
        }
    }

    @Override
    public void delete(int id) {
        Doctor d = em.find(Doctor.class, id);
        if (d != null) em.remove(d);
    }

    @Override
    public Doctor read(int id) {
        return em.find(Doctor.class, id);
    }

    @Override
    public List<Doctor> getAll() {
        return em.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
    }
}
