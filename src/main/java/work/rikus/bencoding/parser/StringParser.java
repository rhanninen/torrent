package work.rikus.bencoding.parser;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenString;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 */
public class StringParser implements Parser<BenElement<List<Byte>>> {

    @Override
    public BenElement<List<Byte>> parse(InputStream is) throws IOException, InvalidFormatException {
        StringBuilder sb = new StringBuilder();
        int b;
        for (b = is.read(); b != -1 && b != ':'; b = is.read()) {
            if (!(b >= '0') || !(b <= '9')) {
                throw new InvalidFormatException("Input's not in a valid format.");
            }
            sb.append((char) b - '0');
        }
        if (b != ':' || sb.length() == 0)  {
            throw new InvalidFormatException("Input's not in a valid format.");
        }

        BigInteger benStrLen = new BigInteger(sb.toString());
        List<Byte> data = new ArrayList<>();
        
        for (BigInteger i = BigInteger.ZERO; i.compareTo(benStrLen) == -1; i = i.add(BigInteger.ONE)) {
            int read = is.read();
            if (read == -1)
                throw new InvalidFormatException("Reached EOF before could read whole string.");
            data.add((byte) read);
        }

        return new BenString(data);
    }

}
