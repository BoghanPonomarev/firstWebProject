package ua.nure.ponomarev.web.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Bogdan_Ponamarev.
 */
@WebServlet(name = "PhoneConfirmServlet" ,urlPatterns = "/phone_confirm")
public class PhoneConfirmServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(PhoneConfirmServlet.class);
    private  void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("application/json");//Отправляем от сервера данные в JSON -формате
        response.setCharacterEncoding("utf-8");//Кодировка отправляемых данных
        try (PrintWriter out = response.getWriter()) {
            JSONObject jsonEnt = new JSONObject();
            if(request.getParameter("pin_code").equals("1111"))
            {
                jsonEnt.put("backgroundColor","#99CC66");
                jsonEnt.put("serverInfo", "Вы вошли!");
                System.out.println("GOOD");
            }else
            {
                jsonEnt.put("backgroundColor","#CC6666");
                jsonEnt.put("serverInfo", "Введен неправильный логин или пароль!");
                System.out.println("BAD");
            }
            out.print(jsonEnt.toString());
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            logger.error("Ajax request does not work",ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            logger.error("Ajax request does not work",ex);
        }
    }
}
