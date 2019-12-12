package domain;

public class GlobalStats extends Stats {
    private float nFiles = 0;

    public void setStats(Stats stats){
        setCompressionSpeed(stats.getCompressionSpeed());
        setCompressionDegree(stats.getCompressionDegree());
        setCompressedFileSize(stats.getOriginalFileSize());
        setCompressionTime(stats.getCompressionTime());
        setOriginalFileSize(stats.getOriginalFileSize());
    }

    public float getNumberFiles() {
        return nFiles;
    }

 
    public void setNumberFiles(float nFiles) {
        this.nFiles = nFiles;
    }

    private float updateValue(float originalValue, float addedValue){
        return ((originalValue * (nFiles - 1)) + addedValue) / nFiles;
    }

    public void addOriginalSize(float originalFileSize) {
        setOriginalFileSize(updateValue(getOriginalFileSize(), originalFileSize));
    }



    public void addCompressionTime(float compressionTime) {
        setCompressionTime(updateValue(getCompressionTime(), compressionTime));
    }

    public void addCompressedSize(float compressedFileSize) {
        setCompressedFileSize(updateValue(getOriginalFileSize(), compressedFileSize));
    }

    public void addCompressionDegree(float compressionDegree) {
        setCompressionDegree(updateValue(getCompressionDegree(), compressionDegree));
    }

    public void addCompressionSpeed(float compressionSpeed) {
        setCompressionSpeed(updateValue(getCompressionSpeed(), compressionSpeed));
    }
}
