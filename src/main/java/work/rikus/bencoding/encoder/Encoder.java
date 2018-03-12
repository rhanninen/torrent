/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding.encoder;

import java.util.List;
import work.rikus.bencoding.domain.BenElement;

/**
 *
 * @author riku
 */
public interface Encoder<T extends BenElement> {
    List<Byte> encode(T element);
}
