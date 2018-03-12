/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding;

import work.rikus.bencoding.encoder.Encoder;
import work.rikus.bencoding.encoder.BenElementEncoder;
import work.rikus.bencoding.parser.Parser;
import work.rikus.bencoding.parser.BenElementParser;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 */
public class Bencoder {

    private static final Parser<BenElement> PARSER = new BenElementParser();
    private static final Encoder<BenElement> ENCODER = new BenElementEncoder();


    public static BenElement decode(InputStream is) throws IOException, InvalidFormatException {
        if (is == null) {
            throw new IllegalArgumentException("Bencoding can not be decoded from a nonexistent InputStream.");
        }
        return PARSER.parse(is);
    }

    public static void encode(OutputStream out, BenElement element) throws IOException {
        List<Byte> results = ENCODER.encode(element);
        if (!(out instanceof BufferedOutputStream)) {
            out = new BufferedOutputStream(out);
        }

        for (byte b : results) {
            out.write(b);
        }

        out.flush();
    }

}
