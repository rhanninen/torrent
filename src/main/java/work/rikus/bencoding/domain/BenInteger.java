package work.rikus.bencoding.domain;

import java.math.BigInteger;

/**
 *
 * @author riku
 */
public class BenInteger extends BenElement<BigInteger> {

    public BenInteger(BigInteger data) {
        super(data);
    }

    public BigInteger get() {
        return data;
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean equals(Object t) {
        if (t == null || !(t instanceof BenInteger)) {
            return false;
        }

        BenInteger tt = (BenInteger) t;
        return this.data.equals(tt.data);
    }

}
