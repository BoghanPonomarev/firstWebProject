package ua.nure.ponomarev.web.form;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bogdan_Ponamarev.
 */
public class FormMakerImpl implements FormMaker {
    private static final Logger logger = LogManager.getLogger(FormMakerImpl.class);
    @Override
    public RegistrationForm createRegistrationForm(HttpServletRequest request) {
        RegistrationForm result= new RegistrationForm();
           result.setEmail(request.getParameter("email"));
            result.setLogin(request.getParameter("login"));
            result.setPhoneNumber(request.getParameter("phone_number"));
            result.setFirstPassword(request.getParameter("password"));
            result.setSecondPassword(request.getParameter("password_repeat"));
        logger.debug("Form was felt");
        return result;
    }

    @Override
    public AuthorizationForm createAuthorizationForm(HttpServletRequest request) {
        AuthorizationForm result=new AuthorizationForm();
        result.setPassword(request.getParameter("password"));
        result.setPhoneNumber(request.getParameter("phone_number"));
        return result;
    }

}
