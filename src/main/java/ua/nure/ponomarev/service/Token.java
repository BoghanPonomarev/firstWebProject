package ua.nure.ponomarev.service;

/**
 * @author Bogdan_Ponamarev.
 */
public class Token {
    /**
     * variable that contain unique data , like email or phone number
     */
    private String identificationData;

    private int data;

    private long time;

    public Token(String identificationName, long timeMinuets, int data) {
        this.identificationData = identificationName;
        this.time = System.currentTimeMillis() / 60000 + timeMinuets;
        this.data = data;
    }

    public boolean isAlive(long currentTimeMinutes) {
        return currentTimeMinutes < time;
    }

    public String getIdentificationData() {
        return identificationData;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return identificationData != null ? identificationData.equals(((Token) o).identificationData) : ((Token) o).identificationData == null;
    }

    @Override
    public int hashCode() {
        return identificationData != null ? identificationData.hashCode() : 0;
    }

    public int getData() {
        return data;
    }
}
