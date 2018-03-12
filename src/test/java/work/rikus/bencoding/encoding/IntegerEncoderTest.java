package work.rikus.bencoding.encoding;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.encoder.Encoder;
import work.rikus.bencoding.encoder.IntegerEncoder;
import work.rikus.util.ByteUtil;

/**
 *
 * @author riku
 */
public class IntegerEncoderTest {

    private final Encoder<BenInteger> encoder = new IntegerEncoder();

    @Test
    public void validInput() {
        String expected = "i123123123e";
        BenInteger input = new BenInteger(new BigInteger("123123123"));
        assertEquals(ByteUtil.fromString(expected), encoder.encode(input));
    }
    
}
