package work.rikus.tracker;

import java.util.ArrayList;
import java.util.List;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.domain.BenList;
import work.rikus.bencoding.domain.BenString;
import work.rikus.util.ByteUtil;

/**
 *
 * @author riku
 */
public class TrackerResponse {

    private String failure;
    private Integer interval;
    private List<Peer> peers;

    public TrackerResponse(String failure) {
        this.failure = failure;
    }

    public TrackerResponse(Integer interval, List<Peer> peers) {
        if (interval == null || interval < 0) {
            throw new IllegalArgumentException("Interval should be atleast 0.");
        }
        this.interval = interval;
        this.peers = peers;
    }

    public List<Peer> getPeers() {
        return new ArrayList<>(peers);
    }

    public Integer getInterval() {
        return interval;
    }

    public String getFailure() {
        if (!isFailure()) {
            throw new IllegalStateException("TrackerResponse object isn't a failure response.");
        }
        return failure;
    }
    
    public boolean isFailure() {
        return failure != null;
    }

    public static TrackerResponse fromBenDictionary(BenDictionary element) {
        if (element == null) {
            throw new IllegalArgumentException("Can't create TrackerResponse object from a dictionary that's null.");
        }

        BenString failure = element.get(BenString.class, "failure reason");

        if (failure != null) {
            return new TrackerResponse(ByteUtil.toCharString(failure.get()));
        }

        BenInteger interval = element.get(BenInteger.class, "interval");
        BenList peers = element.get(BenList.class, "peers");

        if (interval == null || peers == null) {
            throw new IllegalArgumentException("Response didn't contain interval value or a list of peers.");
        }

        List<Peer> peersList = new ArrayList<>();
        try {
            List<BenDictionary> benDictPeerList = peers.getCasted(BenDictionary.class);
            for (BenDictionary peerDict : benDictPeerList) {
                Peer next = Peer.fromBenDictionary(peerDict);
                peersList.add(next);
            }
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException("Peer list of the response is not in a correct format.");
        }

        return new TrackerResponse(interval.get().intValueExact(), peersList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isFailure()) {
            sb.append("failure: ").append(failure);
            return sb.toString();
        }
        sb.append("interval: ").append(interval).append(", peers: ").append(peers);
        return sb.toString();
    }

    
}
