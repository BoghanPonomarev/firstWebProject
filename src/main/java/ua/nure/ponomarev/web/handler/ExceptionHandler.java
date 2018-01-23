package ua.nure.ponomarev.web.handler;

import ua.nure.ponomarev.exception.LogicException;
import ua.nure.ponomarev.exception.MailSenderException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
public class ExceptionHandler {
    private static final String serverExceptionPage = "";//ERROR PAGE

    private static final String userExceptionPage = "";//Similar THINk

    public static void handleException(LogicException ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = null;
        if (ex.getType().equals(MailSenderException.ExceptionType.SERVER_EXCEPTION)) {
            requestDispatcher = request.getRequestDispatcher(serverExceptionPage);
        } else if (ex.getType().equals(MailSenderException.ExceptionType.USER_EXCEPTION)) {
            requestDispatcher = request.getRequestDispatcher(userExceptionPage);
        }
        if (requestDispatcher != null) {
            requestDispatcher.forward(request, response);
        }
    }
}
