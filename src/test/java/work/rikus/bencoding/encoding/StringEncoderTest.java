package work.rikus.bencoding.encoding;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import work.rikus.bencoding.domain.BenString;
import work.rikus.bencoding.encoder.Encoder;
import work.rikus.bencoding.encoder.StringEncoder;
import work.rikus.util.ByteUtil;

/**
 *
 * @author riku
 */
public class StringEncoderTest {

    private final Encoder<BenString> encoder = new StringEncoder();

    @Test
    public void validInputValidResult() {
        BenString bString = new BenString(ByteUtil.fromString("bencoding"));
        List<Byte> expected = ByteUtil.fromString("9:bencoding");
        assertEquals(expected, encoder.encode(bString));
    }
    
}
