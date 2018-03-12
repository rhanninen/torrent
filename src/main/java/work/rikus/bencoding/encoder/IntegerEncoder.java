package work.rikus.bencoding.encoder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import work.rikus.bencoding.domain.BenInteger;

/**
 *
 * @author riku
 */
public class IntegerEncoder implements Encoder<BenInteger> {

    @Override
    public List<Byte> encode(BenInteger element) {
        if (element == null)
            throw new IllegalArgumentException("Encodable BenInteger can't be null.");
        List<Byte> results = new ArrayList<>();
        BigInteger integer = element.get();
        results.add((Byte) (byte) 'i');
        for (Byte b : integer.toString().getBytes()) {
            results.add(b);
        }
        results.add((Byte) (byte) 'e');
        return results;
    }
    
}
