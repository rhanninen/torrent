package work.rikus.bencoding.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 * @param <T>
 */
public interface Parser<T extends BenElement> {
    T parse(InputStream is) throws IOException, InvalidFormatException;


}
