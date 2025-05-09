package si.um.feri.aiv.auxiliar;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Function to convert String dates to LocalDate
@FacesConverter(value = "localDateConverter", managed = true)
public class LocalDateConverter implements Converter<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //Convert to european date scheme

    @Override
    public LocalDate getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) return null;

        try { //Try to convert
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            // If error, the format is not valid
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid date format. Please use dd-MM-yyyy.", "Invalid date format. Please use dd-MM-yyyy. Example: 25-12-2023");
            throw new ConverterException(msg);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocalDate value) {
        if (value == null) return ""; //if no date return empty
        return value.format(formatter); //else return date formatted
    }
}

