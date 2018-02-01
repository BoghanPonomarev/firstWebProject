package ua.nure.ponomarev.web.form;


import ua.nure.ponomarev.web.form.impl.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bogdan_Ponamarev.
 */
public interface FormMaker {
    RegistrationForm createRegistrationForm(HttpServletRequest request);

    AuthorizationForm createAuthorizationForm(HttpServletRequest request);

    AccountForm createAccountForm(HttpServletRequest request);

    UserForm createUserForm(HttpServletRequest request);

    PaymentForm createPaymentForm(HttpServletRequest request);

    ReplenishForm createReplenishForm (HttpServletRequest request);
}
