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

    public String getAllStats(){
        String data = "";
        data += Float.toString(getNumberFiles()) + "\n";
        data += Float.toString(getOriginalFileSize()) + "\n";
        data += Float.toString(getCompressionTime()) + "\n";
        data += Float.toString(getCompressedFileSize()) + "\n";
        data += Float.toString(getCompressionDegree()) + "\n";
        data += Float.toString(getCompressionSpeed()) + "\n";
        return data;
    }

    public void setAllStats(String data){
        boolean finish = false;
        int j = 0;
        for(int i = 0; !finish; ++i){
            String number = "";
            for(; data.charAt(j) != '\n'; ++j){
                number += data.charAt(j);
            }
            if(i == 0)  setNumberFiles(Float.parseFloat(number));
            else if (i == 1)    setOriginalFileSize(Float.parseFloat(number));
            else if (i == 2)    setCompressionTime(Float.parseFloat(number));
            else if (i == 3)    setCompressedFileSize(Float.parseFloat(number));
            else if (i == 4)    setCompressionDegree(Float.parseFloat(number));
            else if (i == 5)    setCompressionSpeed(Float.parseFloat(number));
            else finish = true;
        }
    }

    public void resetStats(){
        setNumberFiles(0);
        setCompressionSpeed(0);
        setCompressionDegree(0);
        setCompressedFileSize(0);
        setCompressionTime(0);
        setOriginalFileSize(0);
    }
}
