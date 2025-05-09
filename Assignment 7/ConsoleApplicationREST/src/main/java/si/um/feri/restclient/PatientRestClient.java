package si.um.feri.restclient;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

public class PatientRestClient {

    static final String BASE_URL = "http://localhost:8080/sampleProject/api";
    static final URI PATIENTS_URI = URI.create(BASE_URL + "/patients");
    static final URI DOCTORS_URI = URI.create(BASE_URL + "/doctors");

    HttpClient client = HttpClient.newHttpClient();
    Jsonb jsonb = JsonbBuilder.create();
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        PatientRestClient cli = new PatientRestClient();
        int op = -1;
        while (true) {
            try {
                System.out.println("\nChoose an action:");
                System.out.println("1 - List patients");
                System.out.println("2 - Add patient");
                System.out.println("3 - Edit patient");
                System.out.println("4 - List doctors");
                System.out.println("5 - Add doctor");
                System.out.println("6 - Edit doctor");
                System.out.println("7 - Associate Doctor");
                System.out.println("0 - Exit");
                System.out.print("Option: ");
                op = Integer.parseInt(cli.sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (op) {
                    case 1 -> cli.listPatients();
                    case 2 -> cli.addPatient();
                    case 3 -> cli.editPatient();
                    case 4 -> cli.listDoctors();
                    case 5 -> cli.addDoctor();
                    case 6 -> cli.editDoctor();
                    case 7 -> cli.assignDoctorToPatient();
                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }



    public void listPatients() throws Exception {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(PATIENTS_URI).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Patient[] patients = jsonb.fromJson(response.body(), Patient[].class);

        if (patients.length == 0) {
            System.out.println("No patients found.");
            return;
        }

        System.out.println("\nPatients:");
        for (int i = 0; i < patients.length; i++) {
            System.out.printf("%d - %s %s%n", i + 1, patients[i].getName(), patients[i].getSurname());
        }

        System.out.print("Select a patient to view details (or 0 to return): ");
        int sel = Integer.parseInt(sc.nextLine());
        if (sel > 0 && sel <= patients.length) {
            Patient p = patients[sel - 1];
            System.out.println("ID: " + p.getId());
            System.out.println("Name: " + p.getName());
            System.out.println("Surname: " + p.getSurname());
            System.out.println("Email: " + p.getEmail());
            System.out.println("DOB: " + p.getDateOfBirth());
            System.out.println("Details: " + p.getDetails());
            if (p.getDoctor() != null) {
                System.out.println("Doctor: " + p.getDoctor().getName() + " " + p.getDoctor().getSurname());
            }
        }
        else {
            System.out.println("Invalid selection.");
        }

    }

    public void listDoctors() throws Exception {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(DOCTORS_URI).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Doctor[] doctors = jsonb.fromJson(response.body(), Doctor[].class);

        if (doctors.length == 0) {
            System.out.println("No doctors found.");
            return;
        }

        System.out.println("\nDoctors:");
        for (int i = 0; i < doctors.length; i++) {
            System.out.printf("%d - %s %s%n", i + 1, doctors[i].getName(), doctors[i].getSurname());
        }

        System.out.print("Select a doctor to view details (or 0 to return): ");
        int sel = Integer.parseInt(sc.nextLine());
        if (sel > 0 && sel <= doctors.length) {
            Doctor d = doctors[sel - 1];
            System.out.println("ID: " + d.getId());
            System.out.println("Name: " + d.getName());
            System.out.println("Surname: " + d.getSurname());
            System.out.println("Email: " + d.getEmail());
            System.out.println("Max patients: " + d.getMaxPatients());
        }
        else{
            System.out.println("Invalid selection.");
        }
    }

    public void addPatient() throws Exception {
        Patient p = new Patient();
        System.out.print("Name: ");
        p.setName(sc.nextLine());
        System.out.print("Surname: ");
        p.setSurname(sc.nextLine());
        System.out.print("Email: ");
        p.setEmail(sc.nextLine());
        System.out.print("Date of Birth (yyyy-MM-dd): ");
        p.setDateOfBirth(LocalDate.parse(sc.nextLine()));
        System.out.print("Details: ");
        p.setDetails(sc.nextLine());

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonb.toJson(p)))
                .header("Content-Type", "application/json")
                .uri(PATIENTS_URI)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Patient added: " + response.statusCode());
    }

    public void editPatient() throws Exception {
        System.out.print("Patient ID to edit: ");
        int id = Integer.parseInt(sc.nextLine());

        Patient p = new Patient();
        p.setId(id);

        System.out.print("Edit name (leave blank to skip): ");
        String name = sc.nextLine();
        if (!name.isBlank()) p.setName(name);

        System.out.print("Edit surname: ");
        String surname = sc.nextLine();
        if (!surname.isBlank()) p.setSurname(surname);

        System.out.print("Edit email: ");
        String email = sc.nextLine();
        if (!email.isBlank()) p.setEmail(email);

        System.out.print("Edit DOB (yyyy-MM-dd): ");
        String dob = sc.nextLine();
        if (!dob.isBlank()) p.setDateOfBirth(LocalDate.parse(dob));

        System.out.print("Edit details: ");
        String details = sc.nextLine();
        if (!details.isBlank()) p.setDetails(details);

        System.out.print("Confirm update (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonb.toJson(p)))
                    .header("Content-Type", "application/json")
                    .uri(new URI(BASE_URL + "/patients/" + id))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Patient updated: " + response.statusCode());
        } else {
            System.out.println("Update canceled.");
        }
    }

    public void addDoctor() throws Exception {
        Doctor d = new Doctor();
        System.out.print("Name: ");
        d.setName(sc.nextLine());
        System.out.print("Surname: ");
        d.setSurname(sc.nextLine());
        System.out.print("Email: ");
        d.setEmail(sc.nextLine());
        System.out.print("Max Patients: ");
        d.setMaxPatients(Integer.parseInt(sc.nextLine()));

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonb.toJson(d)))
                .header("Content-Type", "application/json")
                .uri(DOCTORS_URI)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Doctor added: " + response.statusCode());
    }


    public void assignDoctorToPatient() throws Exception {

        listPatients();

        System.out.print("Enter the ID of the patient: ");
        int patientId = Integer.parseInt(sc.nextLine());

        listDoctors();

        System.out.print("Enter the ID of the doctor to assign: ");
        int doctorId = Integer.parseInt(sc.nextLine());

        System.out.print("Are you sure you want to assign this doctor to the patient? (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .uri(new URI(BASE_URL + "/patients/" + patientId + "/doctor/" + doctorId))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Assignment status: " + response.statusCode());
            if (!response.body().isBlank()) {
                System.out.println("Response body: " + response.body());
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    public void editDoctor() throws Exception {
        System.out.print("Doctor ID to edit: ");
        int id = Integer.parseInt(sc.nextLine());

        Doctor d = new Doctor();
        d.setId(id);

        System.out.print("Edit name: ");
        String name = sc.nextLine();
        if (!name.isBlank()) d.setName(name);

        System.out.print("Edit surname: ");
        String surname = sc.nextLine();
        if (!surname.isBlank()) d.setSurname(surname);

        System.out.print("Edit email: ");
        String email = sc.nextLine();
        if (!email.isBlank()) d.setEmail(email);

        System.out.print("Edit max patients: ");
        String max = sc.nextLine();
        if (!max.isBlank()) d.setMaxPatients(Integer.parseInt(max));

        System.out.print("Confirm update (y/n): ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            HttpRequest request = HttpRequest.newBuilder()
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonb.toJson(d)))
                    .header("Content-Type", "application/json")
                    .uri(new URI(BASE_URL + "/doctors/" + id))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Doctor updated: " + response.statusCode());
        } else {
            System.out.println("Update canceled.");
        }
    }

}
