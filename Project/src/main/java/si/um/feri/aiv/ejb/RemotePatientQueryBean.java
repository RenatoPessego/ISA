// package: si.um.feri.aiv.ejb

package si.um.feri.aiv.ejb;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import si.um.feri.aiv.interfaces.PatientManager;
import si.um.feri.aiv.interfaces.RemotePatientQuery;
import si.um.feri.aiv.vao.Patient;

import java.util.List;

//Stateless EJB implementing the RemotePatientQuery interface. Provides remote access to patients without doctor

@Stateless
public class RemotePatientQueryBean implements RemotePatientQuery {

    // Inject the local EJB that handles patient data
    @EJB
    private PatientManager patientManager;

    //Returns the patients without a doctor
    @Override
    public List<Patient> getPatientsWithoutDoctor() {
        return patientManager.getWithoutDoctor();
    }
}
