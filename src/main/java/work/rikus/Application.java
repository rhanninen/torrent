package work.rikus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import work.rikus.bencoding.Bencoder;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenElement;
import work.rikus.bencoding.exception.InvalidFormatException;
import work.rikus.metainfo.MetaInfo;
import work.rikus.tracker.HTTPSendingStrategy;
import work.rikus.tracker.SendingStrategy;
import work.rikus.tracker.TrackerRequest;
import work.rikus.tracker.TrackerResponse;

public class Application {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InvalidFormatException {
        File f = new File("/tmp/ubuntu.torrent");
        InputStream input = new BufferedInputStream(new FileInputStream(f));

        long t = System.currentTimeMillis();
        BenElement element = Bencoder.decode(input);
        System.out.println("Decoding took " + (System.currentTimeMillis() - t) + " ms.");

        t = System.currentTimeMillis();
        MetaInfo metaInfo = MetaInfo.fromBenDictionary((BenDictionary) element);
        System.out.println("From BenDictionary to MetaInfo took " + 
                (System.currentTimeMillis() - t) + " ms.");
        System.out.println("metaInfo:");
        System.out.println(metaInfo);
        System.out.println(metaInfo.getInfo().getPieces().size());

        input.close();

        byte[] peerId = {(byte) '-', (byte) 'U', (byte) 'T', 0x23, 0x34, 0x45};
        SendingStrategy sendingStrategy = new HTTPSendingStrategy(new URL(metaInfo.getAnnounce()));
        TrackerRequest request = new TrackerRequest.Builder().infoHash(metaInfo.getInfoHash())
                .port(6882).downloaded(0).ip(new byte[1]).uploaded(0).peerId(peerId).left(0).build();
        TrackerResponse response = sendingStrategy.send(request);
        System.out.println(response);


        
    }
}
