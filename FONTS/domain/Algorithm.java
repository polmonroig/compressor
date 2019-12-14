package domain;

public abstract class Algorithm {

    public abstract byte[] compress(byte[] binaryFile);
    public abstract byte[] decompress(byte[] binaryFile);

}
