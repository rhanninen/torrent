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
public class BenElementParser implements Parser<BenElement> {

    protected static final Map<Character, Class<? extends BenElement>> TYPES = new HashMap<>();
    protected static final Map<Class<? extends BenElement>, Parser<? extends BenElement>> PARSERS = new HashMap<>();

    static {
        TYPES.put('i', BenInteger.class);
        TYPES.put('l', BenList.class);
        TYPES.put('d', BenDictionary.class);
        TYPES.put('1', BenString.class);
        TYPES.put('2', BenString.class);
        TYPES.put('3', BenString.class);
        TYPES.put('4', BenString.class);
        TYPES.put('5', BenString.class);
        TYPES.put('6', BenString.class);
        TYPES.put('7', BenString.class);
        TYPES.put('8', BenString.class);
        TYPES.put('9', BenString.class);

        PARSERS.put(BenInteger.class, new IntegerParser());
        PARSERS.put(BenList.class, new ListParser());
        PARSERS.put(BenDictionary.class, new DictionaryParser());
        PARSERS.put(BenString.class, new StringParser());
    }

    @Override
    public BenElement parse(InputStream is) throws IOException, InvalidFormatException {
        is.mark(1);
        int b = is.read();
        Class<? extends BenElement> typeToken = TYPES.get((char) b);
        Parser<? extends BenElement> parser = PARSERS.get(typeToken);
        if (parser == null) {
            throw new InvalidFormatException("Input's not in valid format. Expected one of [1-9],i,l or d, but got " + b);
        }
        is.reset();
        BenElement result = parser.parse(is);
        return result;
    }

}
