package ua.nure.ponomarev.web.validator;

import ua.nure.ponomarev.web.form.Form;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface Validator<T extends Form> {
    List<String> validate(T form);
}
