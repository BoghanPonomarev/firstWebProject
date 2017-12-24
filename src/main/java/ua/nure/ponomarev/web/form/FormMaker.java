package ua.nure.ponomarev.web.form;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bogdan_Ponamarev.
 */
public class FormMaker extends AbstractFormMaker {
    private static final Logger logger = LogManager.getLogger(FormMaker.class);
    @Override
    public RegistrationForm createRegistrationForm(HttpServletRequest request) {
        RegistrationForm result= new RegistrationForm();
        result.setEmail(request.getParameter("email"));
        result.setLogin(request.getParameter("login"));
        result.setPhoneNumber(request.getParameter("phone_notification"));
        result.setFirstPassword(request.getParameter("password"));
        result.setSecondPassword(request.getParameter("password_repeat"));
        return result;
        //TODO
    }
}
