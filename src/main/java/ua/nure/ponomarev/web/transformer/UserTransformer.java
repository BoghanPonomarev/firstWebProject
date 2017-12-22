package ua.nure.ponomarev.web.transformer;

import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.web.form.RegistrationForm;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserTransformer {

    public static User transform(RegistrationForm registrationForm){
        User user=new User();
        user.setEmail(registrationForm.getEmail());
        user.setLogin(registrationForm.getLogin());
        user.setPassword(registrationForm.getFirstPassword());
        user.setPhoneNumber(registrationForm.getPhoneNumber());
        return user;
    }
}
