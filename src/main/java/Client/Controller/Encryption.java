package Client.Controller;

import Client.Utils;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Encryption
{
    // This class encrypts byte arrays before they are sent and decrypts byte arrays received from the the Server.
    private Cipher encryptCipher, decryptCipher;

    public Encryption()
    {
        try {
            Key key = new SecretKeySpec(Utils.ENCRYPTION_KEY_BYTES,"AES");

            encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] bytes) throws BadPaddingException, IllegalBlockSizeException
    {
        return encryptCipher.doFinal(bytes);
    }

    public byte[] decrypt(byte[] bytes) throws BadPaddingException, IllegalBlockSizeException
    {
        return decryptCipher.doFinal(bytes);
    }

}
