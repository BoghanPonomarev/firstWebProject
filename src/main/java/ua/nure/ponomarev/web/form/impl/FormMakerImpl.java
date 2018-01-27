package ua.nure.ponomarev.web.form.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.web.form.FormMaker;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bogdan_Ponamarev.
 */
public class FormMakerImpl implements FormMaker {
    private static final Logger logger = LogManager.getLogger(FormMakerImpl.class);

    @Override
    public RegistrationForm createRegistrationForm(HttpServletRequest request) {
        RegistrationForm result = new RegistrationForm();
        result.setPhoneNumber(request.getParameter("phone_number"));
        result.setFirstPassword(request.getParameter("password"));
        result.setSecondPassword(request.getParameter("password_repeat"));
        logger.debug("Form was felt");
        return result;
    }

    @Override
    public AuthorizationForm createAuthorizationForm(HttpServletRequest request) {
        AuthorizationForm result = new AuthorizationForm();
        result.setPassword(request.getParameter("password"));
        result.setPhoneNumber(request.getParameter("phone_number"));
        return result;
    }

    @Override
    public AccountForm createAccountForm(HttpServletRequest request) {
        AccountForm accountForm = new AccountForm();
        accountForm.setAmount(request.getParameter("amount"));
        accountForm.setCardNumber(request.getParameter("card_number"));
        accountForm.setCVV(request.getParameter("CVV"));
        accountForm.setValidTrue(request.getParameter("year") + "-" + request.getParameter("month"));
        accountForm.setCurrency(request.getParameter("currency"));
        accountForm.setName(request.getParameter("account_name"));
        return accountForm;
    }

    @Override
    public UserForm createUserForm(HttpServletRequest request) {
        UserForm userForm = new UserForm();
        userForm.setEmail(request.getParameter("email"));
        userForm.setFirstName(request.getParameter("first_name"));
        userForm.setSecondName(request.getParameter("second_name"));
        userForm.setThirdName(request.getParameter("third_name"));
        userForm.setPhoneNumber(request.getParameter("phone_number"));
        userForm.setPassword(request.getParameter("password"));
        String id = request.getParameter("id");
        if (id != null && id.chars().allMatch(Character::isDigit)) {
            userForm.setId(Integer.parseInt(id));
        }
        return userForm;
    }

    @Override
    public PaymentForm createPaymentForm(HttpServletRequest request) {
        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setAccountId(Integer.parseInt(request.getParameter("account_id")));
        paymentForm.setAmount(request.getParameter("amount"));
        paymentForm.setPassword(request.getParameter("password"));
        paymentForm.setRecipientAccountIdentity(request.getParameter("recipient_identity"));
        paymentForm.setCurrency(request.getParameter("currency"));
        return paymentForm;
    }
}
