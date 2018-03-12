package work.rikus.bencoding.encoding;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.domain.BenList;
import work.rikus.bencoding.domain.BenString;
import work.rikus.bencoding.encoder.DictionaryEncoder;
import work.rikus.bencoding.encoder.Encoder;
import work.rikus.util.ByteUtil;

/**
 *
 * @author riku
 */
public class DictionaryEncoderTest {

    private final Encoder<BenDictionary> encoder = new DictionaryEncoder();

    @Test
    public void validInput() {
        String expected = "d3:cowl4:testi123123eee";
        BenString key = new BenString(ByteUtil.fromString("cow"));

        List<BenElement> elements = new ArrayList<>();
        elements.add(new BenString(ByteUtil.fromString("test")));
        elements.add(new BenInteger(new BigInteger("123123")));
        BenList value = new BenList(elements);

        TreeMap<BenString, BenElement> data = new TreeMap<>();
        data.put(key, value);
        BenDictionary input = new BenDictionary(data);
        assertEquals(ByteUtil.fromString(expected), encoder.encode(input));
    }


    
}
