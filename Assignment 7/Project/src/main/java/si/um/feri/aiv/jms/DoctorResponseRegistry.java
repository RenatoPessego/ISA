package si.um.feri.aiv.jms;

import java.util.concurrent.ConcurrentHashMap;

// Simulated in-memory message store for responses from MDB
public class DoctorResponseRegistry {

    // Maps patient ID to response message
    private static final ConcurrentHashMap<Integer, String> responses = new ConcurrentHashMap<>();

    // Add a response for a given patient
    public static void addMessage(int patientId, String message) {
        responses.put(patientId, message);
    }

    // Get the response for a given patient
    public static String getMessage(int patientId) {
        return responses.get(patientId);
    }

    // Remove the response after it's read
    public static void removeMessage(int patientId) {
        responses.remove(patientId);
    }
}
