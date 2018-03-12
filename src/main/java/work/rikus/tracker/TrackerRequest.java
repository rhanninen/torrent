package work.rikus.tracker;

/**
 *
 * @author riku
 */
public class TrackerRequest {

    private final byte[] infoHash;
    private final byte[] peerId;
    private final byte[] ip;
    private final Integer port;
    private final Integer uploaded;
    private final Integer downloaded;
    private final Integer left;

    private TrackerRequest(Builder builder) {
        this.infoHash = builder.infoHash;
        this.peerId = builder.peerId;
        this.ip = builder.ip;
        this.port = builder.port;
        this.uploaded = builder.uploaded;
        this.downloaded = builder.downloaded;
        this.left = builder.left;
    }


    public byte[] getInfoHash() {
        return infoHash;
    }

    public byte[] getPeerId() {
        return peerId;
    }

    public byte[] getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getUploaded() {
        return uploaded;
    }

    public Integer getDownloaded() {
        return downloaded;
    }

    public Integer getLeft() {
        return left;
    }

    public static class Builder {

        private byte[] infoHash;
        private byte[] peerId;
        private byte[] ip;
        private Integer port;
        private Integer uploaded;
        private Integer downloaded;
        private Integer left;

        public Builder() {
        }

        public TrackerRequest build() {
            return new TrackerRequest(this);
        }

        public Builder infoHash(byte[] infoHash) {
            this.infoHash = infoHash;
            return this;
        }

        public Builder peerId(byte[] peerId) {
            this.peerId = peerId;
            return this;
        }

        public Builder ip(byte[] ip) {
            this.ip = ip;
            return this;
        }

        public Builder port(Integer port) {
            this.port = port;
            return this;
        }

        public Builder uploaded(Integer uploaded) {
            this.uploaded = uploaded;
            return this;
        }

        public Builder downloaded(Integer downloaded) {
            this.downloaded = downloaded;
            return this;
        }

        public Builder left(Integer left) {
            this.left = left;
            return this;
        }

    }
    
    
}
