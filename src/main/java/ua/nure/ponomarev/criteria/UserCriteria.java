package ua.nure.ponomarev.criteria;

import ua.nure.ponomarev.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserCriteria {
    private Map<String, String> criteria;

    public UserCriteria(User user) {
        criteria = new HashMap<>();
        if (user.getId() > 0) {
            addToCriteria("id", String.valueOf(user.getId()));
        }
        addToCriteria("first_name", user.getFirstName());
        addToCriteria("second_name", user.getSecondName());
        addToCriteria("third_name", user.getThirdName());
        addToCriteria("email", user.getEmail());
        addToCriteria("password", user.getPassword());
        addToCriteria("phone_number", user.getPhoneNumber());
        addToCriteria("role", String.valueOf(user.getRole()));
    }

    public UserCriteria(User user, boolean isBanImportant) {
        criteria = new HashMap<>();
        if (user.getId() > 0) {
            addToCriteria("id", String.valueOf(user.getId()));
        }
        addToCriteria("first_name", user.getFirstName());
        addToCriteria("second_name", user.getSecondName());
        addToCriteria("third_name", user.getThirdName());
        addToCriteria("email", user.getEmail());
        addToCriteria("password", user.getPassword());
        addToCriteria("phone_number", user.getPhoneNumber());
        addToCriteria("role", String.valueOf(user.getRole()));
        if (isBanImportant) {
            boolean res = user.isBanned();
            addToCriteria("banned", (res) ? "1" : "0");
        }
    }

    public Map<String, String> getCriteria() {
        return criteria;
    }

    private void addToCriteria(String name, String value) {
        if (value != null && !value.equals("null")) {
            criteria.put(name, value);
        }
    }
}
