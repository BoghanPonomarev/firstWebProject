package ua.nure.ponomarev.criteria;

import ua.nure.ponomarev.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public class UserCriteria {
    private Map<String,String> criteria;
    public UserCriteria(User user){
            criteria = new HashMap<>();
            if(user.getId()>0) {
                addToCriteria("id", String.valueOf(user.getId()));
            }
            addToCriteria("email",user.getEmail());
        addToCriteria("password",user.getPassword());
        addToCriteria("phone_number",user.getPhoneNumber());
    }
    public UserCriteria(User user,boolean isEmailActivityImportant){
        criteria = new HashMap<>();
        if(user.getId()>0) {
            addToCriteria("id", String.valueOf(user.getId()));
        }
        addToCriteria("email",user.getEmail());
        addToCriteria("password",user.getPassword());
        addToCriteria("phone_number",user.getPhoneNumber());
        if(isEmailActivityImportant){
            addToCriteria("is_activated_email", String.valueOf(user.isActiveEmail()));
        }
    }
    public Map<String,String> getCriteria(){
        return criteria;
    }
    private void addToCriteria(String name,String value){
        if(value!=null){
            criteria.put(name,value);
        }
    }
}
