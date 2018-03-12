package work.rikus.bencoding.parser;

import work.rikus.bencoding.parser.ListParser;
import work.rikus.bencoding.parser.Parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import static org.junit.Assert.*;
import org.junit.Test;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.domain.BenList;
import work.rikus.bencoding.domain.BenString;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 */
public class ListParserTest {

    private String valid = "li20ei30ee";
    private String validTwoLists = "lli20ei30eee";
    private String validDictionary = "ld3:cow3:mooee";

    private String invalidList = "laskdjfdöljkfase";
    

    private Parser parser = new ListParser();
    private InputStream input;

    @Test
    public void simpleValidInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(valid.getBytes());
        List<BenElement> expList = new ArrayList<>();
        expList.add(new BenInteger(new BigInteger("20")));
        expList.add(new BenInteger(new BigInteger("30")));
        BenList expected = new BenList(expList);
        assertEquals(expected, parser.parse(input));
    }

    @Test(expected = InvalidFormatException.class)
    public void invalidListAsAnInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(invalidList.getBytes());
        parser.parse(input);
    }

    @Test
    public void validTwoListsAsAnInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(validTwoLists.getBytes());
        List<BenElement> expOuterList = new ArrayList<>();
        List<BenElement> expList = new ArrayList<>();
        expList.add(new BenInteger(new BigInteger("20")));
        expList.add(new BenInteger(new BigInteger("30")));
        BenList expected = new BenList(expList);
        expOuterList.add(expected);
        BenList expectedOuter = new BenList(expOuterList);
        assertEquals(expectedOuter, parser.parse(input));
    }

    @Test
    public void validDictionaryAsAnInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(validDictionary.getBytes());
        List<BenElement> expList = new ArrayList<>();
        BenList expected = new BenList(expList);
        TreeMap<BenString, BenElement> dictMap = new TreeMap<>();
        BenDictionary expDict = new BenDictionary(dictMap);

        String cow = "cow";
        List<Byte> cowBytes = new ArrayList<>();
        for (Byte b : cow.getBytes())
            cowBytes.add(b);
        BenString key = new BenString(cowBytes);
        String moo = "moo";
        List<Byte> mooBytes = new ArrayList<>();
        for (Byte b : moo.getBytes())
            mooBytes.add(b);
        BenString value = new BenString(mooBytes);
        dictMap.put(key, value);
        expList.add(expDict);
        assertEquals(expected, parser.parse(input));
        
    }
    
}
