package work.rikus.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author riku
 */
public class ByteUtil {

    public static String toHexString(List<Byte> bytes) {
        StringBuilder results = new StringBuilder();
        for (byte b : bytes) {
            results.append(String.format("%02x", b));
        }
        return results.toString();
    }

    public static List<Byte> fromString(String source) {
        if (source == null)
            throw new IllegalArgumentException("Source string can't be null.");
        List<Byte> results = new ArrayList<>();
        for (Byte b : source.getBytes()) {
            results.add(b);
        }
        return results;
    }

    public static String toCharString(List<Byte> source) {
        StringBuilder sb = new StringBuilder();
        for (byte b : source) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    public static String toCharString(byte[] source) {
        return toCharString(ByteUtil.fromArray(source));
    }

    public static List<List<Byte>> splitTo(List<Byte> source, int resultSize) {
        if (source == null)
            throw new IllegalArgumentException("Can't split anything when source array is null.");
        List<List<Byte>> results = new ArrayList<>();
        List<Byte> current = new ArrayList<>(resultSize);
        for (int i = 0; i < source.size(); i++) {
            current.add(source.get(i));
            if ((i+1) % resultSize == 0) {
                results.add(current);
                current = new ArrayList<>();
            }
        }
        return results;
    }

    public static byte[] asArray(List<Byte> bytes) {
        byte[] results = new byte[bytes.size()];
        for (int i = 0; i < results.length; i++)
            results[i] = bytes.get(i);
        return results;
    }

    public static List<Byte> fromArray(byte[] bytes) {
        List<Byte> results = new ArrayList<>();
        for (Byte b : bytes)
            results.add(b);
        return results;
    }
    
}
