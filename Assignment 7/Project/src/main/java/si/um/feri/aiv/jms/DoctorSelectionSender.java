package si.um.feri.aiv.jms;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Queue;

// Stateless bean responsible for sending JMS messages
@Stateless
public class DoctorSelectionSender {

    @Resource(lookup = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/jms/queue/DoctorQueue")
    private Queue doctorQueue;

    public void sendDoctorSelectionRequest(int patientId, int doctorId) {
        try (JMSContext context = connectionFactory.createContext()) {
            DoctorSelectionRequest request = new DoctorSelectionRequest(patientId, doctorId);
            ObjectMessage message = context.createObjectMessage(request);
            context.createProducer().send(doctorQueue, message);

            System.out.println("[Sender] Sent message: Patient ID " + patientId + ", Doctor ID " + doctorId);
        } catch (Exception e) {
            System.err.println("[Sender] Failed to send JMS message");
            e.printStackTrace();
        }
    }
}
