package ua.nure.ponomarev.encoder.impl;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.encoder.Encoder;

public class AESEncoder implements Encoder {
    private static Logger logger = LogManager.getLogger(AESEncoder.class);

    public String encrypt( String plaintext ) {
        return encrypt( generateIV(), plaintext );
    }

    private String encrypt( byte [] iv, String plaintext ){

        byte [] decrypted = plaintext.getBytes();
        byte [] encrypted = new byte[0];
        try {
            encrypted = encrypt( iv, decrypted );
        } catch (Exception e) {
           logger.error("Encryption was not done" + e);
        }

        StringBuilder cipherText = new StringBuilder();

        cipherText.append( Base64.encodeBase64String( iv ) );
        cipherText.append( ":" );
        cipherText.append( Base64.encodeBase64String( encrypted ) );

        return cipherText.toString();

    }

    public String decrypt( String ciphertext ){
        String [] parts = ciphertext.split( ":" );
        byte [] iv = Base64.decodeBase64( parts[0] );
        byte [] encrypted = Base64.decodeBase64( parts[1] );
        byte [] decrypted = new byte[0];
        try {
            decrypted = decrypt( iv, encrypted );
        } catch (Exception e) {
            logger.error("Decryption was not done "+e);
        }
        return new String( decrypted );
    }

    private Key key;

    public AESEncoder( Key key ) {
        this.key = key;
    }

    public AESEncoder() throws Exception {
        this( generateSymmetricKey() );
    }

    public Key getKey() {
        return key;
    }

    public void setKey( Key key ) {
        this.key = key;
    }

    private static byte [] generateIV() {
        SecureRandom random = new SecureRandom();
        byte [] iv = new byte [16];
        random.nextBytes( iv );
        return iv;
    }

    private static Key generateSymmetricKey() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance( "AES" );
        return generator.generateKey();
    }

    private byte [] encrypt( byte [] iv, byte [] plaintext ) throws Exception {
        Cipher cipher = Cipher.getInstance( key.getAlgorithm() + "/CBC/PKCS5Padding" );
        cipher.init( Cipher.ENCRYPT_MODE, key, new IvParameterSpec( iv ) );
        return cipher.doFinal( plaintext );
    }

    private byte [] decrypt( byte [] iv, byte [] ciphertext ) throws Exception {
        Cipher cipher = Cipher.getInstance( key.getAlgorithm() + "/CBC/PKCS5Padding" );
        cipher.init( Cipher.DECRYPT_MODE, key, new IvParameterSpec( iv ) );
        return cipher.doFinal( ciphertext );
    }

}