package work.rikus.bencoding.parser;

import work.rikus.bencoding.parser.Parser;
import work.rikus.bencoding.parser.IntegerParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 */
public class IntegerParserTest {

    String correct = "i123123e";
    String signedCorrect = "i-123123e";
    String zero = "i0e";

    String invalidZeroPrefix = "i000000000001e";
    String invalidNegativeZero = "i-0e";
    String completelyInvalid = "iasködfjhaslkjdfhasdlkfjhasljkdhflasjkdhflkjhasdf";
    String wayCompletelyInvalid = "asldkjfhasdljkfhasdlkfj";
    String empty = "ie";

    private Parser parser = new IntegerParser();
    private InputStream input;

    @Test
    public void validInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(correct.getBytes());
        BigInteger expected = new BigInteger("123123");
        assertEquals(expected, parser.parse(input).get(BigInteger.class));
    }

    @Test
    public void validSignedInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(signedCorrect.getBytes());
        BigInteger expected = new BigInteger("-123123");
        assertEquals(expected, parser.parse(input).get(BigInteger.class));
    }

    @Test
    public void zeroAsAnInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(zero.getBytes());
        BigInteger expected = BigInteger.ZERO;
        assertEquals(expected, parser.parse(input).get(BigInteger.class));
    }

    @Test(expected = InvalidFormatException.class)
    public void invalidPrefixedInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(invalidZeroPrefix.getBytes());
        parser.parse(input);
    }

    @Test(expected = InvalidFormatException.class)
    public void invalidNegativeZero() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(invalidNegativeZero.getBytes());
        parser.parse(input);
    }

    @Test(expected = InvalidFormatException.class)
    public void completelyInvalid() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(completelyInvalid.getBytes());
        parser.parse(input);
    }

    @Test(expected = InvalidFormatException.class)
    public void wayCompletelyInvalid() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(wayCompletelyInvalid.getBytes());
        parser.parse(input);
    }

    @Test(expected = InvalidFormatException.class)
    public void emptyInvalid() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(empty.getBytes());
        parser.parse(input);
    }
    
}
