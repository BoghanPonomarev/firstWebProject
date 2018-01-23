package ua.nure.ponomarev.hash;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Bogdan_Ponamarev.
 */
public class ShaHashGeneratorImpl implements HashGenerator {
    private static final String PASSWORD = "1234567891234567";
    private static final String ALGORITHM = "SHA-512";
    private static final String ENCODING = "UTF-8";
    private static Logger logger = LogManager.getLogger(ShaHashGeneratorImpl.class);

    public String generateHash(String line) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(line.getBytes(ENCODING));
            byte[] bytes = md.digest(PASSWORD.getBytes(ENCODING));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
            if (generatedPassword.length() > 50) {
                generatedPassword = generatedPassword.substring(0, 50);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.fatal("HashGenerator ca`nt find write algorithm " + e);
        } catch (UnsupportedEncodingException e) {
            logger.fatal("No such encoding " + e);
        }
        return generatedPassword;
    }
}
