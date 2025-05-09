package si.um.feri.aiv.auxiliar;

public class EmailService {

    public static void sendEmail(String to, String subject, String body) {
        // Simulate sending email
        System.out.println("=== EMAIL SENT ===");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("==================");
    }
}
