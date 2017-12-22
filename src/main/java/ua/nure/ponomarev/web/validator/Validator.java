package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.Form;

import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public interface Validator <T extends Form>{
    Map<String,String> validate(T form);
}
