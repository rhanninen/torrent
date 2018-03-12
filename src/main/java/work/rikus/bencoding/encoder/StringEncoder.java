/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding.encoder;

import java.util.ArrayList;
import java.util.List;
import work.rikus.bencoding.domain.BenString;

/**
 *
 * @author riku
 */
public class StringEncoder implements Encoder<BenString> {

    @Override
    public List<Byte> encode(BenString element) {
        if (element == null)
            throw new IllegalArgumentException("Encodable BenString can't be null.");
        List<Byte> results = new ArrayList<>();
        List<Byte> source = element.get();
        String length = "" + source.size();
        byte[] lengthArray = length.getBytes();
        for (Byte b : lengthArray) {
            results.add(b);
        }
        results.add((Byte) (byte) ':');
        results.addAll(source);
        return results;
    }
    
}
