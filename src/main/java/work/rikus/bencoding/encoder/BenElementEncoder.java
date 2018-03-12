/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding.encoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.domain.BenList;
import work.rikus.bencoding.domain.BenString;

/**
 *
 * @author riku
 */
public class BenElementEncoder implements Encoder<BenElement> {

    protected static final Map<Class<? extends BenElement>, Encoder<? extends BenElement>> ENCODERS = new HashMap<>();
    static {
        ENCODERS.put(BenString.class, new StringEncoder());
        ENCODERS.put(BenInteger.class, new IntegerEncoder());
        ENCODERS.put(BenList.class, new ListEncoder());
        ENCODERS.put(BenDictionary.class, new DictionaryEncoder());
    }

    @Override
    public List<Byte> encode(BenElement element) {
        if (element == null)
            throw new IllegalArgumentException("Encodable BenElement can't be null.");
        Encoder encoder = ENCODERS.get(element.getClass());
        return encoder.encode(element);
    }
    
}
