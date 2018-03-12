package work.rikus.tracker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import work.rikus.bencoding.Bencoder;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.exception.InvalidFormatException;

/**
 *
 * @author riku
 */
public class HTTPSendingStrategy implements SendingStrategy {

    private final URL destination;

    public HTTPSendingStrategy(URL destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination can't be null.");
        } else if (!destination.getProtocol().startsWith("http")) {
            throw new IllegalArgumentException("HTTPSendingStrategy can't process URLs other than http or https.");
        }
        this.destination = destination;
    }

    @Override
    public TrackerResponse send(TrackerRequest request) {
        if (request == null)
            throw new IllegalArgumentException("Can't send request having nothing as an argument.");

        System.out.println("info_hash: " + request.getInfoHash());
        System.out.println("peer_id: " + request.getPeerId());
        System.out.println("port: " + request.getPort());
        System.out.println("uploaded: " + request.getUploaded());
        System.out.println("downloaded: " + request.getDownloaded());
        System.out.println("left: " + request.getLeft());

        String queryString = asQueryString(request);
                
        
        try {
            URL destinationWithQueryString = new URL(destination + queryString);
            System.out.println("url: " + destinationWithQueryString);
            HttpURLConnection connection = (HttpURLConnection) destinationWithQueryString.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BenElement rawResponse = Bencoder.decode(connection.getInputStream());
            if (!(rawResponse instanceof BenDictionary)) {
                throw new InvalidFormatException("Tracker's response is not in a valid format.");
            }
            TrackerResponse response = TrackerResponse.fromBenDictionary((BenDictionary) rawResponse);
            return response;
        } catch (IOException | InvalidFormatException ioe) {
            System.out.println(ioe);
        }
        return null;
    }

    private String asQueryString(TrackerRequest request) {
        StringBuilder results = new StringBuilder("?");
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder();
        queryStringBuilder.append("info_hash", request.getInfoHash())
                .append("peer_id", request.getPeerId())
                .append("ip", request.getIp())
                .append("port", request.getPort().toString())
                .append("uploaded", request.getUploaded().toString())
                .append("downloaded", request.getDownloaded().toString())
                .append("left", request.getLeft().toString());
        results.append(queryStringBuilder.toString());
        return results.toString();
    }

    private static class QueryStringBuilder {

        private final StringBuilder queryString;

        public QueryStringBuilder() {
            queryString = new StringBuilder();
        }

        public QueryStringBuilder append(String key, byte[] data) {
            return append(key, new String(data));
        }

        public QueryStringBuilder append(String key, String value) {
            try {
                String encoded = URLEncoder.encode(value, "UTF-8");
                queryString.append(key).append("=")
                        .append(encoded).append("&");
            } catch (UnsupportedEncodingException uee) {
                System.out.println(uee);
            }
            
            return this;
        }

        @Override
        public String toString() {
            return queryString.substring(0, queryString.length()-1);
        }

    }
    
}
