package work.rikus.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 * @author riku
 */
public class Digester {

    public static byte[] getDigest(String algo, byte[] source) {
        try {
            MessageDigest md = MessageDigest.getInstance(algo);
            byte[] results = md.digest(source);
            return results;
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println(nsae);
        }
        return null;
    }
}
