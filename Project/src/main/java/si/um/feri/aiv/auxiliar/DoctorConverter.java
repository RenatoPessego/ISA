
package si.um.feri.aiv.auxiliar;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import si.um.feri.aiv.interfaces.DoctorManager;
import si.um.feri.aiv.vao.Doctor;

@FacesConverter(value = "doctorConverter", managed = true)
public class DoctorConverter implements Converter<Doctor> {

    @EJB
    private DoctorManager doctorManager;

    // Convert String (id) to Doctor object
    @Override
    public Doctor getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            int id = Integer.parseInt(value);
            return doctorManager.read(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Convert Doctor object to String (id)
    @Override
    public String getAsString(FacesContext context, UIComponent component, Doctor doctor) {
        if (doctor == null) return "";
        return String.valueOf(doctor.getId());
    }
}
