
package si.um.feri.aiv.vao;

import java.io.Serializable;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

//Represents the patient entity

public class Patient implements Serializable {
    private int id; //error messages when the variables required to create a patient do not respect the regex pattern
    @NotBlank(message = "Name is required.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s'-]{2,}$", message = "Name must have at least 2 letters and only valid characters.")
    private String name;

    @NotBlank(message = "Surname is required.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s'-]{2,}$", message = "Surname must have at least 2 letters and only valid characters.")
    private String surname;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email format is invalid.")
    private String email;

    @NotNull(message = "Date of birth is required.")
    private LocalDate dateOfBirth;

    private String details;

    private String doctor;

    public Patient() {
    }

    public Patient(int id, String name, String surname, String email, LocalDate dateOfBirth, String details, String doctor) { //function to assign each value to it's variable from the patient
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
        this.doctor = doctor;
    }


    //getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }

    //to string function, to write a patient
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + " " +
                ", surname='" + surname + " " +
                ", email='" + email + " " +
                ", dateOfBirth=" + dateOfBirth +
                ", details='" + details + " " +
                ", doctor='" + doctor + " " +
                '}';
    }
}
