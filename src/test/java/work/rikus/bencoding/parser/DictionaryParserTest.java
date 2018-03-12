package work.rikus.bencoding.parser;

import work.rikus.bencoding.parser.DictionaryParser;
import work.rikus.bencoding.parser.Parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.domain.BenString;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 */
public class DictionaryParserTest {

    private String valid = "d3:cowi20ee";
    private String emptyInput = "de";

    private String invalid = "aaeeedddee";

    private Parser parser = new DictionaryParser();
    private InputStream input;

    @Test
    public void validInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(valid.getBytes());
        TreeMap<BenString, BenElement> dataMap = new TreeMap<>();
        String cow = "cow";
        List<Byte> cowBytes = new ArrayList<>();
        for (Byte b : cow.getBytes())
            cowBytes.add(b);
        BenString key = new BenString(cowBytes);
        dataMap.put(new BenString(cowBytes), new BenInteger(new BigInteger("20")));
        BenDictionary expected = new BenDictionary(dataMap);
        BenDictionary empty = new BenDictionary(new TreeMap<>());
        assertEquals(expected, parser.parse(input));
        input = new ByteArrayInputStream(emptyInput.getBytes());
        assertEquals(empty, parser.parse(input));
    }

    @Test(expected = InvalidFormatException.class)
    public void invalidInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(invalid.getBytes());
        parser.parse(input);
    }

    
}
