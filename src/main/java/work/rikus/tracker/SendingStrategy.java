package work.rikus.tracker;

/**
 *
 * @author riku
 */
public interface SendingStrategy {
    TrackerResponse send(TrackerRequest request);
}
