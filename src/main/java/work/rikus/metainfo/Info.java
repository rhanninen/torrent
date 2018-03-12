package work.rikus.metainfo;

import java.math.BigInteger;
import java.util.List;
import work.rikus.bencoding.domain.BenString;
import work.rikus.util.ByteUtil;

/**
 *
 * @author riku
 */
public class Info {
    private final String name; // either dictionary or file name
    private final BigInteger pieceLength;
    private final List<List<Byte>> pieces;
    private final BigInteger length; // might not be present
    private final List<FileElement> files;

    private Info(Builder builder) {
        this.name = builder.name;
        this.pieceLength = builder.pieceLength;
        this.pieces = builder.pieces;
        this.length = builder.length;
        this.files = builder.files;
    }

    public String getName() {
        return name;
    }

    public BigInteger getPieceLength() {
        return pieceLength;
    }

    public List<List<Byte>> getPieces() {
        return pieces;
    }

    public BigInteger getLength() {
        return length;
    }

    public List<FileElement> getFiles() {
        return files;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("name: ").append(name);
        string.append("\npieceLength: ").append(pieceLength);
        string.append("\npieces: ");
        pieces.forEach(i -> string.append("\n").append(ByteUtil.toHexString(i)));
        string.append("\nlength: ").append(length);
        string.append("\nfiles: ").append(files);
        return string.toString();
    }


    public static class Builder {
        private String name;
        private BigInteger pieceLength;
        private List<List<Byte>> pieces;
        private BigInteger length; 
        private List<FileElement> files;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder pieceLength(BigInteger pieceLength) {
            this.pieceLength = pieceLength;
            return this;
        }

        public Builder pieces(List<List<Byte>> pieces) {
            this.pieces = pieces;
            return this;
        }

        public Builder length(BigInteger length) {
            this.length = length;
            return this;
        }

        public Builder files(List<FileElement> files) {
            this.files = files;
            return this;
        }

        public Info build() {
            return new Info(this);
        }

    }

    
}
