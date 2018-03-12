package work.rikus.bencoding.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 *
 * @author riku
 */
public class BenDictionary extends BenElement<TreeMap<BenString, ? extends BenElement>> {

    public BenDictionary(TreeMap<BenString, ? extends BenElement> data) {
        super(data);
    }

    public <T> T get(Class<T> clazz, String key) {
        List<Byte> byteKey = new ArrayList<>();
        for (Byte b : key.getBytes())
            byteKey.add(b);
        BenString keyBenString = new BenString(byteKey);
        if (data.containsKey(keyBenString) && data.get(keyBenString).getClass().equals(clazz))
            return clazz.cast(data.get(keyBenString));
        return null;
    }

    public NavigableMap<BenString, ? extends BenElement> getMap() {
        return new TreeMap<>(data);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object t) {
        if (t == null || !(t instanceof BenDictionary)) {
            return false;
        }

        BenDictionary tt = (BenDictionary) t;
        return this.data.equals(tt.data);
        
    }

}
