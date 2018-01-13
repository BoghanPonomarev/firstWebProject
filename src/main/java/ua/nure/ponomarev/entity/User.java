package ua.nure.ponomarev.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/**
 * @author Bogdan_Ponamarev.
 */
public class User {
    private int id;
    private String login;
    private String password;
    private String phoneNumber;
    private String email;
    private boolean isActiveEmail;
}
