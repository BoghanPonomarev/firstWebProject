package ua.nure.ponomarev.web.form;


import javax.servlet.http.HttpServletRequest;

/**
 * @author Bogdan_Ponamarev.
 */
public interface FormMaker {
    RegistrationForm createRegistrationForm(HttpServletRequest request);

    AuthorizationForm createAuthorizationForm(HttpServletRequest request);

    AccountForm createAccountForm(HttpServletRequest request);

    UserForm createUserForm(HttpServletRequest request);
}
