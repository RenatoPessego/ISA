package si.um.feri.aiv.vao;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Surname is required.")
    private String surname;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email.")
    private String email;

    private int maxPatients;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonbTransient
    private List<Patient> patients = new ArrayList<>();


    public Doctor(int i, String name, String surname, String email, int maxPatients) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.maxPatients = maxPatients;
    }

    public Doctor() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getMaxPatients() { return maxPatients; }
    public void setMaxPatients(int maxPatients) { this.maxPatients = maxPatients; }

    public List<Patient> getPatients() { return patients; }
    public void setPatients(List<Patient> patients) { this.patients = patients; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

}
