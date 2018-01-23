package ua.nure.ponomarev.criteria;

import ua.nure.ponomarev.entity.Account;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */

public class AccountCriteria {
    private Map<String, String> criteria;

    public AccountCriteria(Account account, boolean isBanImportant, boolean isUnbanRequstedImportant) {
        criteria = new HashMap<>();
        if (account.getId() > 0) {
            addToCriteria("id", String.valueOf(account.getId()));
        }
        addToCriteria("name", account.getName());
        if (isBanImportant) {
            addToCriteria("banned", (account.isBanned()) ? "1" : "0");
        }
        if (isUnbanRequstedImportant) {
            addToCriteria("is_requested_for_unban", (account.isRequestedForUnban()) ? "1" : "0");
        }
        addToCriteria("card_number", account.getCard().getCardNumber());
        addToCriteria("currency_id", account.getCard().getCurrency());
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
