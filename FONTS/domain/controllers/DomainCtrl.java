package domain.controllers;

import domain.*;
import data.controllers.DataCtrl;

import java.io.File;
import java.io.IOException;

public class DomainCtrl {
    private DataCtrl dataCtrl;
    private LZ78 lz78;
    private LZSS lzss;
    private LZW lzw;
    private JPEG jpeg;
    private GlobalStats globalStats;
    private String [] Algoritmes = {"LZ78", "LZSS", "LZW", "JPEG"};


    public DomainCtrl(){
        init();
    }

    private void init() {
        dataCtrl = new DataCtrl(this);
        globalStats = new GlobalStats();
        lz78 = new LZ78();
        lzss = new LZSS();
        lzw = new LZW();
        jpeg = new JPEG();
    }

    public String[] getAlgoritmes(){
        return Algoritmes;
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
        return globalStats.getNumberFiles();
    }

    public byte[] compress(String algorithm, byte[] file){
        globalStats.setNumberFiles(globalStats.getNumberFiles() + 1);

        switch (algorithm) {
            case "lz78": {
                return lz78.compress(file);
            }
            case "lzss": {
                return lzss.compress(file);
            }
            case "lzw": {
                return lzw.compress(file);
            }
            case "jpeg": {
                return jpeg.compress(file);
            }
        }
        return file;

    }

    public byte[] decompress(String algorithm, byte[] file){

        switch (algorithm) {
            case "lz78": {
                return lz78.decompress(file);
            }
            case "lzss": {
                return lzss.decompress(file);
            }
            case "lzw": {
                return lzw.decompress(file);
            }
            case "jpeg": {
                return jpeg.decompress(file);
            }
        }
        return file;
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

    public void compressFiles(File[] files) throws IOException {
        dataCtrl.compressFiles(files);
    }

    public void decompressFile(File file) {
        dataCtrl.decompressFile(file);
    }
}
