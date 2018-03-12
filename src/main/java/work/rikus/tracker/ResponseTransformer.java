package work.rikus.tracker;

/**
 *
 * @author riku
 */
public interface ResponseTransformer<T> {
    T transform(TrackerResponse response);
}
