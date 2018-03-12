package work.rikus.bencoding.encoding;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.domain.BenList;
import work.rikus.bencoding.domain.BenString;
import work.rikus.bencoding.encoder.Encoder;
import work.rikus.bencoding.encoder.ListEncoder;
import work.rikus.util.ByteUtil;

/**
 *
 * @author riku
 */
public class ListEncoderTest {

    private final Encoder<BenList> encoder = new ListEncoder();

    @Test
    public void validInput() {
        List<BenElement> elements = new ArrayList<>();
        elements.add(new BenString(ByteUtil.fromString("test")));
        elements.add(new BenInteger(new BigInteger("123123")));
        BenList input = new BenList(elements);
        String expected = "l4:testi123123ee";
        assertEquals(ByteUtil.fromString(expected), encoder.encode(input));
    }
    
}
