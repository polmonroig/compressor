package domain;

public class GlobalStats extends Stats {
    private float nFiles = 0;


    public GlobalStats(){
        reset();
    }

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
        setCompressedFileSize(updateValue(getCompressedFileSize(), compressedFileSize));
    }

    public void addCompressionDegree(float compressionDegree) {
        setCompressionDegree(updateValue(getCompressionDegree(), compressionDegree));
    }

    public void addCompressionSpeed(float compressionSpeed) {
        setCompressionSpeed(updateValue(getCompressionSpeed(), compressionSpeed));
    }

    public String getAllStats(){
        String data = "";
        data += getNumberFiles() + "\n";
        data += getOriginalFileSize() + "\n";
        data += getCompressionTime() + "\n";
        data += getCompressedFileSize() + "\n";
        data += getCompressionDegree() + "\n";
        data += getCompressionSpeed() + "\n";
        return data;
    }

    public void setFileStats(String data){
        int j = 0;
        int nStats = 5;
        for(int i = 0; i <= nStats; ++i){
            StringBuilder number = new StringBuilder();
            while(data.charAt(j) != '\n'){
                number.append(data.charAt(j));
                ++j;
            }
            ++j;
            if(i == 0)  setNumberFiles(Float.parseFloat(number.toString()));
            else if (i == 1)    setOriginalFileSize(Float.parseFloat(number.toString()));
            else if (i == 2)    setCompressionTime(Float.parseFloat(number.toString()));
            else if (i == 3)    setCompressedFileSize(Float.parseFloat(number.toString()));
            else if (i == 4)    setCompressionDegree(Float.parseFloat(number.toString()));
            else if (i == 5)    setCompressionSpeed(Float.parseFloat(number.toString()));
        }
    }


}
