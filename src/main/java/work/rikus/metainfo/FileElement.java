package work.rikus.metainfo;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author riku
 */
public class FileElement {
    private final BigInteger length;
    private final List<String> path;

    public FileElement(BigInteger length, List<String> path) {
        this.length = length;
        this.path = path;
    }

    public BigInteger getLength() {
        return length;
    }

    public List<String> getPath() {
        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("length: ").append(length).append(", path: ").append(path);
        return sb.toString();
    }
    
}
