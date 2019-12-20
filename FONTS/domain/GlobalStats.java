package domain;

/**
 * These is the class to store the global stats of all the aplication
 **/


public class GlobalStats extends Stats {
    /**
     * In this variable we store the number of compressions
     **/
    private float nFiles = 0;

    /**
     * <p>Main creator function of the class</p>
     **/
    public GlobalStats() {
        reset();
    }

    /**
     * <p>setStats set all the necessary stats</p>
     *
     * @param stats A class of stats
     **/
    public void setStats(Stats stats) {
        setCompressionSpeed(stats.getCompressionSpeed());
        setCompressionDegree(stats.getCompressionDegree());
        setCompressedFileSize(stats.getCompressedFileSize());
        setCompressionTime(stats.getCompressionTime());
        setOriginalFileSize(stats.getOriginalFileSize());
    }

    /**
     * <p>getNumberFiles get the number of files compressed</p>
     *
     * @return float with the number of files
     **/

    public float getNumberFiles() {
        return nFiles;
    }

    /**
     * <p>setNumberFiles set the number of files compressed</p>
     *
     * @param nFiles float with the number of files
     **/
    public void setNumberFiles(float nFiles) {
        this.nFiles = nFiles;
    }

    /**
     * <p>updateValue updates the value of the stats</p>
     *
     * @param addedValue    float with the new value to add
     * @param originalValue float with the old value
     * @return float with the value updated
     **/
    private float updateValue(float originalValue, float addedValue) {
        return ((originalValue * (nFiles - 1)) + addedValue) / nFiles;
    }

    /**
     * <p>addOriginalSize adds the new original size file of a new compression</p>
     *
     * @param originalFileSize float with the original size of the file
     **/
    public void addOriginalSize(float originalFileSize) {
        setOriginalFileSize(updateValue(getOriginalFileSize(), originalFileSize));
    }

    /**
     * <p>addCompressionTime adds the new compression time of a new compression</p>
     *
     * @param compressionTime float with the compression time of the file
     **/
    public void addCompressionTime(float compressionTime) {
        setCompressionTime(updateValue(getCompressionTime(), compressionTime));
    }

    /**
     * <p>addCompressedSize adds the new compressed size file of a new compression</p>
     *
     * @param compressedFileSize float with the compressed size of the file
     **/
    public void addCompressedSize(float compressedFileSize) {
        setCompressedFileSize(updateValue(getCompressedFileSize(), compressedFileSize));
    }

    /**
     * <p>addCompressionDegree adds the new compression degree of a new compression</p>
     *
     * @param compressionDegree float with the compression degree of the file
     **/
    public void addCompressionDegree(float compressionDegree) {
        setCompressionDegree(updateValue(getCompressionDegree(), compressionDegree));
    }

    /**
     * <p>addCompressionSpeed adds the new compression speed of a new compression</p>
     *
     * @param compressionSpeed float with the compression speed of the file
     **/
    public void addCompressionSpeed(float compressionSpeed) {
        setCompressionSpeed(updateValue(getCompressionSpeed(), compressionSpeed));
    }

    /**
     * <p>getAllStats makes the string to write in the file of global stats</p>
     *
     * @return A string with the global stats
     **/
    public String getAllStats() {
        String data = "";
        data += getNumberFiles() + "\n";
        data += getOriginalFileSize() + "\n";
        data += getCompressionTime() + "\n";
        data += getCompressedFileSize() + "\n";
        data += getCompressionDegree() + "\n";
        data += getCompressionSpeed() + "\n";
        return data;
    }

    /**
     * <p>setAllStats reads all the stats from the data parameter</p>
     *
     * @param data A string with the global stats
     **/
    public void setFileStats(String data) {
        int j = 0;
        int nStats = 5;
        for (int i = 0; i <= nStats; ++i) {
            StringBuilder number = new StringBuilder();
            while (data.charAt(j) != '\n') {
                number.append(data.charAt(j));
                ++j;
            }
            ++j;
            if (i == 0) setNumberFiles(Float.parseFloat(number.toString()));
            else if (i == 1) setOriginalFileSize(Float.parseFloat(number.toString()));
            else if (i == 2) setCompressionTime(Float.parseFloat(number.toString()));
            else if (i == 3) setCompressedFileSize(Float.parseFloat(number.toString()));
            else if (i == 4) setCompressionDegree(Float.parseFloat(number.toString()));
            else if (i == 5) setCompressionSpeed(Float.parseFloat(number.toString()));
        }
    }

}
