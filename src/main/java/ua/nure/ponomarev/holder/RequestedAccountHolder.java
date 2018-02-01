package ua.nure.ponomarev.holder;

/**
 * @author Bogdan_Ponamarev.
 */
public interface RequestedAccountHolder {
    void addToRequested(int accountId);

    boolean isAlreadyRequested(int accountId);

    void remove(int accountId);
}
