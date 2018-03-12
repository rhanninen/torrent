package work.rikus.bencoding.domain;

/**
 *
 * @author riku
 */
public abstract class BenElement<T> {
    protected final T data;

    public BenElement(T data) {
        if (data == null)
            throw new IllegalArgumentException("Data for BenElement can't be null.");
        this.data = data;
    }

    public T get(Class<T> clazz) {
        return clazz.cast(data);
    }

}
