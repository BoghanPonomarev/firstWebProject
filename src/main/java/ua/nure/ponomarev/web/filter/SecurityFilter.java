package ua.nure.ponomarev.web.filter;

import ua.nure.ponomarev.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Bogdan_Ponamarev.
 */
@WebFilter(filterName = "SecurityFilter")
public class SecurityFilter implements Filter {
    private Set<String> allowWithoutAuthorizationCommands = new TreeSet<>();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        allowWithoutAuthorizationCommands.add("authorization");
        allowWithoutAuthorizationCommands.add("authorization/check");
        allowWithoutAuthorizationCommands.add("registration");
        allowWithoutAuthorizationCommands.add("registration/check");
        allowWithoutAuthorizationCommands.add("registration/code/send");
        allowWithoutAuthorizationCommands.add(" registration/code/check");
        allowWithoutAuthorizationCommands.add("registration/phone/check");

    }
    private void checkAuthorization( HttpServletRequest request , HttpServletResponse response) throws InstantiationException, IllegalAccessException, IOException {
        Integer id =(Integer) request.getSession().getAttribute("userId");
        User.Role role = (User.Role) request.getSession().getAttribute("userRole");
        if((id==null||role==null)&&!allowWithoutAuthorizationCommands.contains(makeUrl(request))){
       redirect(request.getContextPath() + "/authorization",response);
        }
    }
    private String makeUrl(HttpServletRequest request) throws IllegalAccessException, InstantiationException {
        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        return url.substring(url.indexOf(contextPath) + contextPath.length() + 1);
    }
    public void redirect(String path, HttpServletResponse response) throws IOException {
        response.sendRedirect(path);
    }

}
