package si.um.feri.aiv.jms;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import si.um.feri.aiv.auxiliar.EmailService;
import si.um.feri.aiv.interfaces.PatientManager;
import si.um.feri.aiv.vao.Doctor;
import si.um.feri.aiv.vao.Patient;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/DoctorQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class DoctorSelectionMDB implements MessageListener {
    @EJB
    private PatientManager patientManager;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage objMsg) {
                Object obj = objMsg.getObject();

                if (obj instanceof DoctorSelectionRequest request) {
                    System.out.println("[MDB] Received request: Patient ID " + request.getPatientId()
                            + ", Doctor ID " + request.getDoctorId());

                    boolean success = patientManager.assignDoctorIfPossible(
                            request.getPatientId(), request.getDoctorId());

                    if (success) {

                        // Simulated emails
                        Patient patient = patientManager.read(request.getPatientId());
                        Doctor doctor = patient.getDoctor();
                        EmailService.sendEmail(doctor.getEmail(), "New Patient Assigned",
                                "You have a new patient: " + patient.getName() + " " + patient.getSurname());

                        EmailService.sendEmail(patient.getEmail(), "Doctor Assigned",
                                "You have been successfully assigned to Dr. " + doctor.getName() + " " + doctor.getSurname());

                        String msg = "[EMAIL] Doctor assigned. Notification sent to doctor and patient.";
                        System.out.println(msg);
                        DoctorResponseRegistry.addMessage(request.getPatientId(), msg);
                    } else {
                        Patient patient = patientManager.read(request.getPatientId());

                        EmailService.sendEmail(patient.getEmail(), "Doctor Assignment Failed",
                                "Unfortunately, the requested doctor is not available at the moment.");

                        String msg = "[EMAIL] Doctor is full. Notified patient about unavailability.";
                        System.out.println(msg);
                        DoctorResponseRegistry.addMessage(request.getPatientId(), msg);
                    }
                } else {
                    System.out.println("[MDB] Unsupported payload in ObjectMessage.");
                }
            } else {
                System.out.println("[MDB] Received non-ObjectMessage: " + message.getClass());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}


