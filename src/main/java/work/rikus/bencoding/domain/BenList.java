package work.rikus.bencoding.domain;

import java.util.List;

/**
 *
 * @author riku
 */
public class BenList extends BenElement<List<? extends BenElement>> {

    public BenList(List<? extends BenElement> data) {
        super(data);
    }

    public List<? extends BenElement> get() {
        return data;
    }

    public <T> List<T> getCasted(Class<T> clazz) {
        return (List<T>) data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object t) {
        if (t == null || !(t instanceof BenList)) {
            return false;
        }
        
        BenList tt = (BenList) t;
        return this.data.equals(tt.data);
    }

}
