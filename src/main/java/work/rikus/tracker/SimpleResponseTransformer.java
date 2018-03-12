package work.rikus.tracker;

/**
 *
 * @author riku
 */
public class SimpleResponseTransformer implements ResponseTransformer<TrackerResponse> {

    @Override
    public TrackerResponse transform(TrackerResponse response) {
        return response;
    }
    
}
