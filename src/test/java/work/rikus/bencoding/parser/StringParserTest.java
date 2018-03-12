/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding.parser;

import work.rikus.bencoding.parser.StringParser;
import work.rikus.bencoding.parser.Parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import work.rikus.bencoding.domain.BenString;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 */
public class StringParserTest {

    String valid = "7:torrent";
    String invalid = "8:torrent";

    String lengthOnly = "9:";
    String withoutLengthInvalid = ":torrent";
    String completelyInvalid = "laksjhdfadshf";

    private Parser parser = new StringParser();
    private InputStream input;

    @Test
    public void validInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(valid.getBytes());
        List<Byte> expected = new ArrayList<>();
        for (int i = 2; i < valid.length(); i++) {
            expected.add(valid.getBytes()[i]);
        }
        assertEquals(expected, parser.parse(input).get(List.class));
    }

    @Test(expected = InvalidFormatException.class)
    public void invalidInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(invalid.getBytes());
        parser.parse(input);
    }

    @Test(expected = InvalidFormatException.class)
    public void lengthOnlyInvalidInput() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(lengthOnly.getBytes());
        parser.parse(input);
    }

    @Test(expected = InvalidFormatException.class)
    public void withLengthInvalid() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(withoutLengthInvalid.getBytes());
        parser.parse(input);
    }

    @Test(expected = InvalidFormatException.class)
    public void completelyInvalid() throws IOException, InvalidFormatException {
        input = new ByteArrayInputStream(completelyInvalid.getBytes());
        parser.parse(input);
    }


    
}
