package domain;

/**
 * Interface of of the algorithms JPEG, LZ78, LZSS, LZW
 **/

public interface Algorithm {
    /**
     * <p>The compression method makes a compression of a given text/image</>
     *
     * @param binaryFile the text/image to compress
     * @return the compressed text/image
     */
    byte[] compress(byte[] binaryFile);

    /**
     * <p>The decompression method takes a compressed file and returns the original text/image</>
     *
     * @param binaryFile the text/image to decompress
     * @return the decompressed text/image
     */
    byte[] decompress(byte[] binaryFile);

}
