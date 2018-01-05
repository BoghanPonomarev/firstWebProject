package ua.nure.ponomarev.encoder;

/**
 * @author Bogdan_Ponamarev.
 */
public interface Encoder {
    String encrypt(String text);

    String decrypt(String text);
}
