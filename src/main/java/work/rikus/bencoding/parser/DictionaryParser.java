/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
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
public class DictionaryParser implements Parser<BenElement<TreeMap<BenString, ? extends BenElement>>> {

    @Override
    public BenElement<TreeMap<BenString, ? extends BenElement>> parse(InputStream is) throws IOException, InvalidFormatException {
        is.read(); // read the d
        Parser<BenString> parser = (Parser<BenString>) BenElementParser.PARSERS.get(BenString.class);
        TreeMap<BenString, BenElement> tree = new TreeMap<>();
        
        is.mark(1); 
        int b = is.read();
        BenString lastKey = null;
        for (; b != -1 && b != 'e'; b = is.read()) {
            if (!(b >= '1') || !(b <= '9')) {
                throw new InvalidFormatException("Input's not in a valid format. Expected a number indicating the next key's length. But got " + b);
            }
            is.reset(); 
            BenString key = parser.parse(is);
            if (lastKey == null)
                lastKey = key;

            if (lastKey.compareTo(key) > 0)
                throw new InvalidFormatException("Input's not in a valid format. Dictionary keys should be binary sorted.");

            is.mark(1);
            b = is.read();
            Class<? extends BenElement> typeToken = BenElementParser.TYPES.get((char) b);
            Parser<? extends BenElement> valueParser = BenElementParser.PARSERS.get(typeToken);
            if (valueParser == null) {
                throw new InvalidFormatException("Input's not in a valid format. Expected one of l,[1-9],i or d, but got " + b);
            }
            is.reset();
            BenElement value = valueParser.parse(is);
            tree.put(key, value);
            is.mark(1);
        }
        

        if (b != 'e') {
            throw new InvalidFormatException("Input's not in a valid format. Expected e but got " + b);
        }

        return new BenDictionary(tree);
    }

    
}
