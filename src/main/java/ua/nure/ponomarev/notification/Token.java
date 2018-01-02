package ua.nure.ponomarev.notification;

/**
 * @author Bogdan_Ponamarev.
 */
public class Token {
    /**
     * variable that contain unique data , like email or phone number
     */
    private String identificationName;

    private int data;

    private long time;

    public Token(String identificationName,long timeMinuets,int data){
        this.identificationName =identificationName;
        this.time= System.currentTimeMillis()/60000 + timeMinuets;
        this.data = data;
    }

    public boolean isAlive(long currentTimeMinutes){
        return currentTimeMinutes < time;
    }

    public String getIdentificationName() {
        return identificationName;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return identificationName != null ? identificationName.equals(((Token)o).identificationName) : ((Token)o).identificationName == null;
    }

    @Override
    public int hashCode() {
        return identificationName != null ? identificationName.hashCode() : 0;
    }

    public int getData() {
        return data;
    }
}
