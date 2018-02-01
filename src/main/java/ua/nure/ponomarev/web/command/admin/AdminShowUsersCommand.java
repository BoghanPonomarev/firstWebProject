package ua.nure.ponomarev.web.command.admin;

import ua.nure.ponomarev.entity.Account;
import ua.nure.ponomarev.entity.User;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.service.AccountService;
import ua.nure.ponomarev.service.UserService;
import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.handler.ExceptionHandler;
import ua.nure.ponomarev.web.page.Mapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Bogdan_Ponamarev.
 */
public class AdminShowUsersCommand extends FrontCommand {
    private UserService userService;
    private AccountService accountService;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response
            , ServletContext servletContext, ServletConfig config) {
        super.init(request, response, servletContext, config);
        userService = (UserService) config.getServletContext().getAttribute("user_service");
        accountService = (AccountService) config.getServletContext().getAttribute("account_service");
    }

    @Override
    public void execute() throws ServletException, IOException {
        putUsersToRequest(request, response);
        forward(Mapping.getPagePath(Mapping.Page.ADMIN_SHOW_USERS));
    }

    private void putUsersToRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<User, List<Account>> users = new TreeMap<>((User e1, User e2) -> {
            return Integer.compare(e1.getId(), e2.getId());
        });
        String page;
        int numberOfPage = 1;
        if ((page = request.getParameter("page")) != null && page.chars().allMatch(Character::isDigit)) {
            numberOfPage = Integer.parseInt((String) request.getParameter("page"));
        }
        try {
            for (User user : userService.getAll((User.Role) request.getSession()
                            .getAttribute("userRole")
                    , numberOfPage)) {
                users.put(user, accountService.getAccounts(user.getId(), AccountService.SortStrategy.ID));
            }
            paginationValidation(numberOfPage);
        } catch (DbException e) {
            ExceptionHandler.handleException(e, request, response);
        }
        request.setAttribute("users", users);
        request.setAttribute("page", numberOfPage);
    }

    private void paginationValidation(int numberOfPage) throws DbException {
        if (!userService.getAll((User.Role) request.getSession()
                        .getAttribute("userRole")
                , numberOfPage + 1).isEmpty()) {
            request.setAttribute("nextPage", servletContext.getContextPath()
                    + "/admin/users?page=" + (numberOfPage + 1));
            if (!userService.getAll(((User.Role) request.getSession()
                            .getAttribute("userRole"))
                    , numberOfPage + 2).isEmpty()) {
                request.setAttribute("doubleNextPage", servletContext.getContextPath()
                        + "/admin/users?page=" + (numberOfPage + 2));
            }
        }
        if (numberOfPage > 1) {
            request.setAttribute("previousPage", servletContext.getContextPath()
                    + "/admin/users?page=" + (numberOfPage - 1));
            if (numberOfPage>2&&!userService.getAll(((User.Role) request.getSession()
                            .getAttribute("userRole"))
                    , numberOfPage - 2).isEmpty()) {
                request.setAttribute("doublePreviousPage", servletContext.getContextPath()
                        + "/admin/users?page=" + (numberOfPage - 2));
            }
        }
    }
}
