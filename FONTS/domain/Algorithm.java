package domain;

public interface Algorithm {

    byte[] compress(byte[] binaryFile);
    byte[] decompress(byte[] binaryFile);

}
