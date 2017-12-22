package ua.nure.ponomarev.web.form;


import javax.servlet.http.HttpServletRequest;

/**
 * @author Bogdan_Ponamarev.
 */
public abstract class AbstractFormMaker {
    public abstract RegistrationForm createRegistrationForm(HttpServletRequest request);

    //TODO
}
