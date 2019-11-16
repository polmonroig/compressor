package domain.controllers;

import domain.*;
import data.controllers.DataCtrl;

public class DomainCtrl {
    private DataCtrl dataCtrl;
    private LZ78 lz78;
    private LZSS lzss;
    private LZW lzw;
    private JPEG jpeg;
    private GlobalStats globalStats;


    public DomainCtrl(){
        init();
    }

    private void init() {
        dataCtrl = new DataCtrl();
        globalStats = new GlobalStats();
        lz78 = new LZ78();
        lzss = new LZSS();
        lzw = new LZW();
        jpeg = new JPEG();
    }

    public float getMeanOriginalFileSize(){
        return globalStats.getOriginalFileSize();
    }

    public float getMeanCompressedFileSize(){
        return globalStats.getCompressedFileSize();
    }

    public float getMeanCompressionDegree(){
        return globalStats.getCompressionDegree();
    }

    public float getMeanCompressionSpeed(){
        return globalStats.getCompressionSpeed();
    }

    public float getMeanCompressionTime(){
        return globalStats.getCompressionTime();
    }

    public float getMeanFiles(){
        return globalStats.getNumberArxius();
    }

    public void compress(String algorithm, String fileName){
        globalStats.setNumberArxius(globalStats.getNumberArxius() + 1);
        byte[] file = DataCtrl.ReadFileAsBytes(fileName);
        int lastPeriodPos = fileName.lastIndexOf('.');
        fileName = fileName.substring(0,lastPeriodPos);
        switch (algorithm) {
            case "lz78": {
                byte[] decompressed = lz78.compress(file);
                DataCtrl.WriteBytesToFile(fileName + ".lz78", decompressed);
                break;
            }
            case "lzss": {
                byte[] compressed = lzss.compress(file);
                DataCtrl.WriteBytesToFile(fileName + ".lzss", compressed);
                break;
            }
            case "lzw": {
                byte[] compressed = lzw.compress(file);
                DataCtrl.WriteBytesToFile(fileName + ".lzw", compressed);
                break;
            }
            case "jpeg": {
                byte[] compressed = jpeg.compress(file);
                DataCtrl.WriteBytesToFile(fileName + ".jpeg", compressed);
                break;
            }
        }
    }

    public void decompress(String algorithm, String fileName){
        byte[] file = DataCtrl.ReadFileAsBytes(fileName);
        int lastPeriodPos = fileName.lastIndexOf('.');
        fileName = fileName.substring(0,lastPeriodPos);
        switch (algorithm) {
            case "lz78": {
                byte[] decompressed = lz78.decompress(file);
                DataCtrl.WriteBytesToFile(fileName + ".txt", decompressed);
                break;
            }
            case "lzss": {
                byte[] decompressed = lzss.decompress(file);
                DataCtrl.WriteBytesToFile(fileName + ".txt", decompressed);
                break;
            }
            case "lzw": {
                byte[] decompressed = lzw.decompress(file);
                DataCtrl.WriteBytesToFile(fileName + ".txt", decompressed);
                break;
            }
            case "jpeg": {
                byte[] decompressed = jpeg.decompress(file);
                DataCtrl.WriteBytesToFile(fileName + ".ppm", decompressed);
                break;
            }
        }
    }


    public void setQuality(int parseInt) {
        jpeg.setQuality(parseInt);
    }

    public float getOriginalFileSize(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getOriginalSize();
            }
            case "lzss": {
                return lzss.getOriginalSize();
            }
            case "lzw": {
                return lzw.getOriginalSize();
            }
            case "jpeg": {
                return jpeg.getOriginalSize();
            }
        }
        return 0;
    }

    public float getMidaArxiuFinal(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getCompressedSize();
            }
            case "lzss": {
                return lzss.getCompressedSize();
            }
            case "lzw": {
                return lzw.getCompressedSize();
            }
            case "jpeg": {
                return jpeg.getCompressedSize();
            }
        }
        return 0;
    }

    public float getCompressionDegree(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getCompressionRatio();
            }
            case "lzss": {
                return lzss.getCompressionRatio();
            }
            case "lzw": {
                return lzw.getCompressionRatio();
            }
            case "jpeg": {
                return jpeg.getCompressionRatio();
            }
        }
        return 0;
    }

    public float getCompressionTime(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getCompressionTime();
            }
            case "lzss": {
                return lzss.getCompressionTime();
            }
            case "lzw": {
                return lzw.getCompressionTime();
            }
            case "jpeg": {
                return jpeg.getCompressionTime();
            }
        }
        return 0;
    }

    public float getCompressionSpeed(String algorithm) {
        switch (algorithm) {
            case "lz78": {
                return lz78.getCompressionSpeed();
            }
            case "lzss": {
                return lzss.getCompressionSpeed();
            }
            case "lzw": {
                return lzw.getCompressionSpeed();
            }
            case "jpeg": {
                return jpeg.getCompressionSpeed();
            }
        }
        return 0;
    }

    public void setStats(float originalFileSize, float compressedFileSize, float compressionTime, float compressionDegree, float compressionSpeed) {
        globalStats.addOriginalSize(originalFileSize);
        globalStats.addCompressedSize(compressedFileSize);
        globalStats.addCompressionTime(compressionTime);
        globalStats.addCompressionDegree(compressionDegree);
        globalStats.addCompressionSpeed(compressionSpeed);
    }
}
