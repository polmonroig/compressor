package domain;

public class Stats {
    private float compressionTime;
    private float compressionDegree;
    private  float compressionSpeed;
    private float originalFileSize;
    private float compressedFileSize;

    public Stats(){
        reset();
    }


    public float getOriginalFileSize() {
        return originalFileSize;
    }

    public void setOriginalFileSize(float size) {
        this.originalFileSize = size;
    }

    public float getCompressedFileSize() {
        return compressedFileSize;
    }

    public void setCompressedFileSize(float size) {
        this.compressedFileSize = size;
    }


    public float getCompressionTime() {
        return compressionTime;
    }

    public void setCompressionTime(float compressionTime) {
        this.compressionTime = compressionTime;
    }

    public float getCompressionDegree() {
        return compressionDegree;
    }

    public void setCompressionDegree(float compressionDegree) {
        this.compressionDegree = compressionDegree;
    }

    public float getCompressionSpeed() {
        return compressionSpeed;
    }

    public void setCompressionSpeed(float compressionSpeed) {
        this.compressionSpeed = compressionSpeed;
    }

    protected void reset(){
        compressionDegree = 0;
        compressionTime = 0;
        compressionSpeed = 0;
        compressedFileSize = 0;
        originalFileSize = 0;
    }




}
