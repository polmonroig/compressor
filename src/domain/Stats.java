package domain;

public class Stats {
    private float compressionTime;
    private float compressionDegree;
    private  float compressionSpeed;
    private int originalFileSize;
    private int compressedFileSize;

    public void reset(){
        originalFileSize = 0;
        compressedFileSize = 0;
        compressionSpeed = 0;
        compressionTime = 0;
        compressionDegree = 0;
    };

    public int getOriginalFileSize() {
        return originalFileSize;
    }

    public void setOriginalFileSize(int size) {
        this.originalFileSize = size;
    }

    public int getCompressedFileSize() {
        return compressedFileSize;
    }

    public void setCompressedFileSize(int size) {
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




}
