// package: si.um.feri.aiv.interfaces

package si.um.feri.aiv.interfaces;

import jakarta.ejb.Remote;
import si.um.feri.aiv.vao.Patient;

import java.util.List;

// Remote interface for querying patients without doctor.

@Remote
public interface RemotePatientQuery {

//Returns a list of patients who do not have a doctor assigned.

    List<Patient> getPatientsWithoutDoctor();
}
