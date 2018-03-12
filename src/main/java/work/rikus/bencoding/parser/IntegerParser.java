/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package work.rikus.bencoding.parser;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 */
public class IntegerParser implements Parser<BenElement<BigInteger>> {

    @Override
    public BenElement<BigInteger> parse(InputStream is) throws IOException, InvalidFormatException {
        is.read(); // read the i.
        int b = is.read();

        boolean signed = false;
        StringBuilder dataString = new StringBuilder();

        if (b == '-') {
            signed = true;
        } else if (b >= '1' && b <= '9') {
            dataString.append((char) b - '0');
        } else if (b == '0' && (b = is.read()) == 'e') {
            return new BenInteger(BigInteger.ZERO);
        } else {
            throw new InvalidFormatException("Expected -, [0-9] or 0e but got " + ((char) b));
        }

        if (signed && (!((b = is.read()) >= '1') || !(b <= '9'))) {
            throw new InvalidFormatException("Input's not in a valid format. Expected 1-9 after a sign '-' but got " + ((char) b));
        } else if (signed) {
            dataString.append("-").append((char) b - '0');
        }        

        for (b = is.read(); b != -1 && b != 'e'; b = is.read()) {
            if (!(b >= '0') || !(b <= '9'))
                throw new InvalidFormatException("Input's not in a valid format. Expected 0-9 but got " + ((char) b));
            dataString.append((char) b - '0');
        }

        if (b != 'e')
            throw new InvalidFormatException("Input's not in a valid format. Expected e but got " + ((char) b));

        return new BenInteger(new BigInteger(dataString.toString()));
    }
    
}
