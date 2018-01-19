package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.Form;

import java.util.List;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public interface Validator <T extends Form>{
    List<String> validate(T form);
}
