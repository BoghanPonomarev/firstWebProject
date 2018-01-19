package ua.nure.ponomarev.web.command.authorization;

import ua.nure.ponomarev.web.command.FrontCommand;
import ua.nure.ponomarev.web.page.Mapping;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Bogdan_Ponamarev.
 */
public class GetAuthorizationFormCommand extends FrontCommand {
    @Override
    public void execute() throws ServletException, IOException {
        forward(Mapping.getPagePath(Mapping.Page.AUTHORIZATION_PAGE));
    }
}
