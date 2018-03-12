/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding.parser;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class ListParser implements Parser<BenElement<List<? extends BenElement>>> {

    @Override
    public BenElement<List<? extends BenElement>> parse(InputStream is) throws IOException, InvalidFormatException {
        is.read(); //read the l
        List<BenElement> list = new LinkedList<>();

        is.mark(1);
        int r = is.read();
        for (; r != -1 && r != 'e'; r = is.read()) {
            if (!Arrays.asList('l','d','i','1','2','3','4','5','6','7','8','9').contains((char) r)) {
                throw new InvalidFormatException("Input's is not in a valid format, got " + r);
            }
            Class type = BenElementParser.TYPES.get((char) r);
            Parser<? extends BenElement> parser = BenElementParser.PARSERS.get(type);
            is.reset();
            BenElement<? extends BenElement> element = parser.parse(is);
            list.add(element);
            is.mark(1);
        }

        if (r != 'e')
            throw new InvalidFormatException("Input's is not in a valid format. Expected e but got " + r);

        return new BenList(list);
    }
    
}
