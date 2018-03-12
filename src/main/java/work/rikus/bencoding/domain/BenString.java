package work.rikus.bencoding.domain;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author riku
 */
public class BenString extends BenElement<List<Byte>> implements Comparable<BenString> {

    public BenString(List<Byte> data) {
        super(data);
    }

    public List<Byte> get() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Byte b : data) {
            sb.append((char) b.byteValue());
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.data);
        return hash;
    }
    

    @Override
    public boolean equals(Object t) {
        if (t == null || !(t instanceof BenString)) {
            return false;
        }

        BenString tt = (BenString) t;
        return this.data.equals(tt.data);
    }

    @Override
    public int compareTo(BenString o) {
        int shorter = this.data.size() < o.data.size() ? this.data.size() : 
                o.data.size();
        for (int i = 0; i < shorter; i++) {
            if (this.data.get(i) < o.data.get(i)) {
                return -1;
            } else if (this.data.get(i) > o.data.get(i)) {
                return 1;
            }
        }
        if (this.data.size() != o.data.size()) {
            return shorter == this.data.size() ? -1 : 1;
        }
        return 0;
    }


    
}
