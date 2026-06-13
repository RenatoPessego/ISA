package si.um.feri.aiv.jms;

import jakarta.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class JMSDoctorClient {
    private static final long serialVersionUID = 1L;
    public void sendDoctorRequestLegacy(int patientId, int doctorId) throws Exception {
        // Setup JNDI context
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        env.put(Context.SECURITY_PRINCIPAL, "test");
        env.put(Context.SECURITY_CREDENTIALS, "test");

        Context ctx = new InitialContext(env);

        // Lookup resources
        Queue queue = (Queue) ctx.lookup("jms/queue/DoctorQueue");
        QueueConnectionFactory factory = (QueueConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");

        // Create connection/session/sender
        QueueConnection connection = factory.createQueueConnection("test", "test");
        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        QueueSender sender = session.createSender(queue);

        // Send the ObjectMessage
        ObjectMessage message = session.createObjectMessage(new DoctorSelectionRequest(patientId, doctorId));
        sender.send(message);

        System.out.println("✅ JMS ObjectMessage sent to DoctorQueue!");

        // Clean up
        session.close();
        connection.close();
    }
}
