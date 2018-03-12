package work.rikus.metainfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import work.rikus.bencoding.Bencoder;
import work.rikus.bencoding.domain.BenDictionary;
import work.rikus.bencoding.domain.BenInteger;
import work.rikus.bencoding.domain.BenList;
import work.rikus.bencoding.domain.BenString;
import work.rikus.util.ByteUtil;
import work.rikus.util.Digester;

/**
 *
 * @author riku
 */
public class MetaInfo {

    private final String announce;
    private final Info info;
    private final boolean isSingleFile;
    private final byte[] infoHash;
    private List<String> announceList;

    public MetaInfo(String announce, Info info, boolean isSingleFile, byte[] infoHash) {
        this.announce = announce;
        this.info = info;
        this.isSingleFile = isSingleFile;
        this.infoHash = infoHash;
    }

    public MetaInfo(String announce, Info info, boolean isSingleFile, byte[] infoHash, List<String> announceList) {
        this(announce, info, isSingleFile, infoHash);
        this.announceList = announceList;
    }

    public String getAnnounce() {
        return announce;
    }

    public Info getInfo() {
        return info;
    }

    public boolean isSingleFile() {
        return isSingleFile;
    }

    public byte[] getInfoHash() {
        // TODO: return a copy instead.
        return infoHash;
    }

    public List<String> getAnnounceList() {
        return announceList;
    }

    @Override
    public String toString() {
        StringBuilder results = new StringBuilder();
        results.append("announce: ").append(announce);
        if (announceList != null) {
            results.append("\nannounce-list: ").append(announceList);
        }
        results.append("\ninfo hash: ").append(ByteUtil.toHexString(ByteUtil.fromArray(infoHash)));
        results.append("\n").append("info: ");
        results.append("\n").append(info);
        results.append("\n").append("isSingleFile ").append(isSingleFile);
        return results.toString();
    }

    public static MetaInfo fromBenDictionary(BenDictionary element) throws IOException {
        if (element == null) {
            throw new IllegalArgumentException("Can't create MetaInfo instance from BenElement that's null.");
        }

        BenString announce = element.get(BenString.class, "announce");
        if (announce == null) {
            throw new IllegalArgumentException("Can't create MetaInfo instance without announce URL.");
        }

        BenList announceListRaw = element.get(BenList.class, "announce-list");

        BenDictionary info = element.get(BenDictionary.class, "info");
        if (info == null) {
            throw new IllegalArgumentException("Can't create MetaInfo instance without info dictionary.");
        }

        byte[] infoHash = null;
        ByteArrayOutputStream barrayOut = new ByteArrayOutputStream();
        Bencoder.encode(barrayOut, info);
        infoHash = Digester.getDigest("SHA-1", barrayOut.toByteArray());
        barrayOut.close();

        if (infoHash == null) {
            throw new IllegalArgumentException("Couldn't create a hash of the info dictionary.");
        }

        BenString name = info.get(BenString.class, "name");
        BenInteger pieceLength = info.get(BenInteger.class, "piece length");
        BenString piecesString = info.get(BenString.class, "pieces");
        BenInteger length = info.get(BenInteger.class, "length");
        BenList files = info.get(BenList.class, "files");

        Info.Builder infoBuilder = new Info.Builder();
        
        infoBuilder.name(ByteUtil.toCharString(name.get()));

        boolean isSingleFile = true;
        if (length != null) {
            infoBuilder.length(length.get());
        } else if (length == null && files != null) {
            isSingleFile = false;
            List<FileElement> fileElements = parseFileElements(files.getCasted(BenDictionary.class));
            infoBuilder.files(fileElements);
        } else {
            throw new IllegalArgumentException("Couldn't define whether the source represents single file or multiple files.");
        }

        List<List<Byte>> pieces = ByteUtil.splitTo(piecesString.get(), 20);
        infoBuilder.pieces(pieces).pieceLength(pieceLength.get());

        if (announceListRaw != null) {
            List<BenList> announceListDecl = announceListRaw.getCasted(BenList.class);
            List<String> announceList = new ArrayList<>();
            for (BenList announceURL : announceListDecl) {
                List<BenString> announceStringList = announceURL.getCasted(BenString.class);
                for (BenString announceString : announceStringList) {
                    announceList.add(ByteUtil.toCharString(announceString.get()));
                }
            }
            return new MetaInfo(ByteUtil.toCharString(announce.get()), infoBuilder.build(), isSingleFile, infoHash, announceList);
        }
        return new MetaInfo(ByteUtil.toCharString(announce.get()), infoBuilder.build(), isSingleFile, infoHash);
    }

    private static List<FileElement> parseFileElements(List<BenDictionary> source) {
        if (source == null)
            throw new IllegalArgumentException("Can't parse individual file elements when source is null.");
        List<FileElement> results = new LinkedList<>();
        for (BenDictionary dict : source) {
            BenInteger length = dict.get(BenInteger.class, "length");
            BenList path = dict.get(BenList.class, "path");

            if (length == null || path == null) {
                throw new IllegalArgumentException("Length or path of the 'files' list was null. (" + length + ", " + path + ")");
            } else if (path.get().isEmpty()) {
                throw new IllegalArgumentException("Length of individual file's path can't be empty.");
            }

            List<String> pathList = new LinkedList<>();
            List<BenString> pathParts = path.getCasted(BenString.class);
            for (BenString pathPart : pathParts) {
                String asString = ByteUtil.toCharString(pathPart.get());
                pathList.add(asString);
            }

            FileElement fElement = new FileElement(length.get(), pathList);
            results.add(fElement);
        }
        return results;
    }

}
