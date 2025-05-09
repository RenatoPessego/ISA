package si.um.feri.aiv.interfaces;

import si.um.feri.aiv.vao.Patient;

import javax.naming.InitialContext;
import java.util.List;
import java.util.Properties;

public class Program {
//First get the properties needed to access remote EJS
    public static void main(String[] args) throws Exception {
        Properties props=new Properties();
        props.put("java.naming.factory.initial","org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put("java.naming.provider.url","http-remoting://127.0.0.1:8080");
        props.put("jboss.naming.client.ejb.context","true");
        props.put("java.naming.factory.url.pkgs","org.jboss.ejb.client.naming");
        //Connect
        InitialContext ctx=new InitialContext(props);
        RemotePatientQuery Query = (RemotePatientQuery) ctx.lookup("sampleProject/RemotePatientQueryBean!si.um.feri.aiv.interfaces.RemotePatientQuery");
        //After connection actually show the patients on the console
        List<Patient> Patients = Query.getPatientsWithoutDoctor();
        System.out.println("List of patients without doctor:");
        System.out.println("--------------------------------------------");
        for (Patient patient : Patients) {
            System.out.println("ID: " + patient.getId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Surname: " + patient.getSurname());
            System.out.println("Email: " + patient.getEmail());
            System.out.println("DOB: " + patient.getDateOfBirth());
            System.out.println("Details: " + patient.getDetails());
            System.out.println("--------------------------------------------");
        }





    }

}
