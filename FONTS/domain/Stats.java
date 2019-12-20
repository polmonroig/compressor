package domain;

/**
 * Class Stats with all the local stats of each compression
 **/

public class Stats {
    /**
     * Variable for the time of compression
     **/
    private float compressionTime;
    /**
     * Variable for the degree of compression
     **/
    private float compressionDegree;
    /**
     * Variable for the speed of compression
     **/
    private float compressionSpeed;
    /**
     * Variable for the original file size
     **/
    private float originalFileSize;
    /**
     * Variable for the compressed file size
     **/
    private float compressedFileSize;

    /**
     * <p>Main creator function of the class</p>
     **/
    public Stats() {
        reset();
    }

    /**
     * <p>getOriginalFileSize get the size of the file</p>
     *
     * @return It has the size of the file
     */
    public float getOriginalFileSize() {
        return originalFileSize;
    }

    /**
     * <p>setOriginalFileSize set the size of the file</p>
     *
     * @param size It has the size of the file
     */
    public void setOriginalFileSize(float size) {
        this.originalFileSize = size;
    }

    /**
     * <p>getCompressedFileSize get the size of the compressed file</p>
     *
     * @return It has the size of the compressed file
     */
    public float getCompressedFileSize() {
        return compressedFileSize;
    }

    /**
     * <p>setCompressedFileSize set the size of the compressed file</p>
     *
     * @param size It has the size of the compressed file
     */
    public void setCompressedFileSize(float size) {
        this.compressedFileSize = size;
    }

    /**
     * <p>getCompressionTime get the time of the compression</p>
     *
     * @return It has the time of the compression
     */
    public float getCompressionTime() {
        return compressionTime;
    }

    /**
     * <p>setCompressionTime set the time of the compression</p>
     *
     * @param compressionTime It has the time of the compression
     */
    public void setCompressionTime(float compressionTime) {
        this.compressionTime = compressionTime;
    }

    /**
     * <p>getCompressionDegree get the degree of the compression</p>
     *
     * @return It has the degree of the compression
     */
    public float getCompressionDegree() {
        return compressionDegree;
    }

    /**
     * <p>setCompressionDegree set the degree of the compression</p>
     *
     * @param compressionDegree It has the degree of the compression
     */
    public void setCompressionDegree(float compressionDegree) {
        this.compressionDegree = compressionDegree;
    }

    /**
     * <p>getCompressionSpeed get the speed of the compression</p>
     *
     * @return It has the speed of the compression
     */
    public float getCompressionSpeed() {
        return compressionSpeed;
    }

    /**
     * <p>setCompressionSpeed set the speed of the compression</p>
     *
     * @param compressionSpeed It has the speed of the compression
     */
    public void setCompressionSpeed(float compressionSpeed) {
        this.compressionSpeed = compressionSpeed;
    }

    /**
     * <p>reset restart the variables to 0</p>
     */
    protected void reset() {
        compressionDegree = 0;
        compressionTime = 0;
        compressionSpeed = 0;
        compressedFileSize = 0;
        originalFileSize = 0;
    }


}
