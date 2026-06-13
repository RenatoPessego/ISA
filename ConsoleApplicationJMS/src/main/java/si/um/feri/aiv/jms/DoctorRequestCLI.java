package si.um.feri.aiv.jms;

import javax.naming.NamingException;
import java.util.Scanner;

public class DoctorRequestCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JMSDoctorClient jmsClient = new JMSDoctorClient();

        System.out.print("Enter patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter doctor ID: ");
        int doctorId = Integer.parseInt(scanner.nextLine());

        try {
            jmsClient.sendDoctorRequestLegacy(patientId, doctorId);
            System.out.println("✅ Request sent successfully.");
        } catch (Exception e) {
            System.out.println("❌ Error: Unable to send JMS request.");
            e.printStackTrace();
        }
    }
}
