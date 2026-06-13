package si.um.feri.restclient;

public class Doctor {
    private int id;
    private String name;
    private String surname;
    private String email;
    private int maxPatients;

    public Doctor() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }

    @Override
    public String toString() {
        return id + " - " + name + " " + surname + " (" + email + ")";
    }
}
