package domain;

public abstract class Algorithm {



    protected Stats localStats = new Stats();

    public float getOriginalSize(){
        return this.localStats.getOriginalFileSize();
    }

    public float getCompressedSize(){ return this.localStats.getCompressedFileSize(); }

    public float getCompressionRatio(){
        return this.localStats.getCompressionDegree();
    }

    public abstract byte[] compress(byte[] binaryFile);
    public abstract byte[] decompress(byte[] binaryFile);


    public float getCompressionTime(){
        return localStats.getCompressionTime();
    }

    public float getCompressionSpeed(){
        return localStats.getCompressionSpeed();
    }
}

