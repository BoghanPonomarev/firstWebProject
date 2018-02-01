package ua.nure.ponomarev.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.LocaleService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.wrapper.LocaleRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

/**
 * @author Bogdan_Ponamarev.
 */
public class LocalizationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LocalizationFilter.class.getName());
    private static List<Locale> supportedLocales;
    private static final Locale DEFAULT_LOCALE = new Locale("en");
    private UserService userService;
    private LocaleService localeService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userService = (UserService) filterConfig.getServletContext().getAttribute("user_service");
        localeService = (LocaleService) filterConfig.getServletContext().getAttribute("locale_service");
        try {
            supportedLocales = localeService.getSupportedLocales();
        } catch (DbException e) {
            logger.error("Could not get supported locales " + e);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setAttribute("locales",supportedLocales);
        LocaleRequestWrapper localeRequestWrapper = new LocaleRequestWrapper(request);
        if(request.getSession().getAttribute("locale")!=null
                &&request.getParameter("language")!=null
                &&!request.getSession().getAttribute("locale").equals(request.getParameter("locale"))){
            try {
                setLocale(request);
            } catch (DbException e) {
                logger.error("Could not set locale in db "+e);
            }
        }
        if (request.getSession().getAttribute("locale") == null) {
            Integer id =  (Integer)request.getSession().getAttribute("userId");
            if(id !=null){
                try {
                    Locale userLocale = userService.getLanguage(id);
                    request.getSession().setAttribute("locale",userLocale);
                    localeRequestWrapper.setLocale(userLocale);
                } catch (DbException e) {
                    ExceptionHandler.handleException(e,request,response);
                }
            }else{
                request.getSession().setAttribute("locale",DEFAULT_LOCALE);
                localeRequestWrapper.setLocale(DEFAULT_LOCALE);
            }
        }else{
            localeRequestWrapper.setLocale((Locale)request.getSession().getAttribute("locale"));
        }
        logger.debug("Create locale wrapper");
        chain.doFilter(localeRequestWrapper, resp);
    }
    private void setLocale(HttpServletRequest request) throws DbException {
       String lang =  request.getParameter("language");
        for (Locale locale : supportedLocales){
            if(locale.getLanguage().equals(lang)){
                Locale newLocale = new Locale(lang);
              request.getSession().setAttribute("locale",newLocale);
                userService.setLanguage((Integer)request.getSession().getAttribute("userId")
                ,new Locale(lang));
            }
        }
    }
}
