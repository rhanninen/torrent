package work.rikus.tracker;

import java.io.IOException;

/**
 *
 * @author riku
 */
public abstract class TrackerCommunicator<T> {
    private final SendingStrategy sendingStrategy;
    private final ResponseTransformer<T> responseTransformer;

    public TrackerCommunicator(SendingStrategy sendingStrategy, 
            ResponseTransformer<T> responseTransformer) {
        this.sendingStrategy = sendingStrategy;
        this.responseTransformer = responseTransformer;
    }

    public T send(TrackerRequest request) {
        TrackerResponse response = sendingStrategy.send(request);
        // statusHandler.handle(response);
        return responseTransformer.transform(response);
    }

}
