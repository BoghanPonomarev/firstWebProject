package ua.nure.ponomarev.web.form.impl;

import lombok.*;
import ua.nure.ponomarev.web.form.Form;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForm implements Form {
    private int id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private String password;
    private String phoneNumber;
    private String email;
}
