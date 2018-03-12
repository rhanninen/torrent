package work.rikus.tracker;

import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.domain.BenString;
import work.rikus.util.ByteUtil;

/**
 *
 * @author riku
 */
public class Peer {

    private final byte[] id;
    private final String ip;
    private final Integer port;

    public Peer(byte[] id, String ip, Integer port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public byte[] getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public static Peer fromBenDictionary(BenDictionary element) {
        if (element == null) {
            throw new IllegalArgumentException("Can't create Peer object from an element that's null.");
        }
        
        BenString id = element.get(BenString.class, "peer id");
        BenString ip = element.get(BenString.class, "ip");
        BenInteger port = element.get(BenInteger.class, "port");

        if (id == null || ip == null || port == null) {
            throw new IllegalArgumentException("Can't create Peer object from an element where id, ip or port is null.");
        }

        // TODO: validate ip as it does have its own format.
        // TODO: validate port that its under 2^16-1 

        return new Peer(ByteUtil.asArray(id.get()), ByteUtil.toCharString(ip.get()), port.get().intValueExact());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(ByteUtil.toCharString(id));
        sb.append("ip: ").append(ip);
        sb.append("port: ").append(port);
        return sb.toString();
    }

}
