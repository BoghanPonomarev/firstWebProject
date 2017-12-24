package ua.nure.ponomarev.notification.mail_notification;

/**
 * @author Bogdan_Ponamarev.
 */
public class Token {

    private String email;

    private int id;

    private long time;

    public Token(String email,long timeMinuets,int id){
        this.email=email;
        this.time= timeMinuets;
        this.id = id;
    }

    public boolean isAlive(long currentTimeMinets){
        return currentTimeMinets < time;
    }

    public String getEmail() {
        return email;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return email != null ? email.equals(((Token)o).email) : ((Token)o).email == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

    public int getId() {
        return id;
    }
}
