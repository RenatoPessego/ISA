package si.um.feri.aiv.jms;

import java.io.Serializable;

// Serializable payload for JMS message
public class DoctorSelectionRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private int patientId;
    private int doctorId;

    public DoctorSelectionRequest() {
        // Required by JavaBeans and JMS serialization
    }

    public DoctorSelectionRequest(int patientId, int doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
