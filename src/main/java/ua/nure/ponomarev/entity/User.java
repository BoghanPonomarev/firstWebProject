package ua.nure.ponomarev.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bogdan_Ponamarev.
 */
@Setter
@Getter
public class User {
    private int id;
    private String password;
    private String phoneNumber;
    private String email;
    private Role role;
    private boolean isBanned;

    public enum Role {
        USER,
        ADMIN
    }
}

