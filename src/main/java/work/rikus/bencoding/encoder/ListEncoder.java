/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding.encoder;

import java.util.ArrayList;
import java.util.List;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenList;

/**
 *
 * @author riku
 */
public class ListEncoder implements Encoder<BenList> {

    @Override
    public List<Byte> encode(BenList element) {
        if (element == null)
            throw new IllegalArgumentException("Encodable BenList can't be null.");
        List<Byte> results = new ArrayList<>();
        results.add((Byte) (byte) 'l');
        List<? extends BenElement> elements = element.get();
        for (BenElement e : elements) {
            Encoder encoder = BenElementEncoder.ENCODERS.get(e.getClass());
            if (encoder != null) {
                results.addAll(encoder.encode(e));
            }
        }
        results.add((Byte) (byte) 'e');
        return results;
    }
}
