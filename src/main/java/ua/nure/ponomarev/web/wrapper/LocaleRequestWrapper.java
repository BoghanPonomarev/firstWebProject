package ua.nure.ponomarev.web.wrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Locale;
import java.util.NoSuchElementException;

/**
 * @author Viacheslav Babanin
 */
public class LocaleRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger LOG = LogManager.getLogger(LocaleRequestWrapper.class.getName());
    private Locale locale;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public LocaleRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Locale getLocale() {
        if (locale == null) {
            return super.getRequest().getLocale();
        }
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }


    @Override
    public Enumeration<Locale> getLocales() {
        if (locale == null) {
            return super.getLocales();
        }
        return new Enumeration<Locale>() {
            boolean next = true;

            @Override
            public boolean hasMoreElements() {
                return next;
            }

            @Override
            public Locale nextElement() {
                if (!next) {
                    throw new NoSuchElementException();
                }
                next = false;
                return locale;
            }
        };
    }
}
