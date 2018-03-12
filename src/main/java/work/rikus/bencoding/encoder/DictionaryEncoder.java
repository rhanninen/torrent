package work.rikus.bencoding.encoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenString;

/**
 *
 * @author riku
 */
public class DictionaryEncoder implements Encoder<BenDictionary> {

    @Override
    public List<Byte> encode(BenDictionary element) {
        if (element == null) 
            throw new IllegalArgumentException("Encodable BenDictionary can't be null.");
        List<Byte> results = new ArrayList<>();
        results.add((Byte) (byte) 'd');
        NavigableMap<BenString, ? extends BenElement> map = element.getMap();
        Entry<BenString, ? extends BenElement> next = map.pollFirstEntry();
        Encoder<BenString> keyEncoder = new StringEncoder();
        while (next != null) {
            BenString key = next.getKey();
            results.addAll(keyEncoder.encode(key));
            BenElement value = next.getValue();
            Encoder valueEncoder = BenElementEncoder.ENCODERS.get(value.getClass());
            if (valueEncoder != null) {
                results.addAll(valueEncoder.encode(value));
            } 

            next = map.pollFirstEntry();
        }
        results.add((Byte) (byte) 'e');
        return results;
    }

}
