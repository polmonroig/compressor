package domain.controllers;

import domain.*;
import data.controllers.DataCtrl;

import java.io.File;
import java.io.IOException;

public class DomainCtrl {
    private DataCtrl dataCtrl;
    private Algorithm algorithm;
    private GlobalStats globalStats;
    private int compressionQuality;

    public DomainCtrl(){
        init();
    }

    private void init() {
        dataCtrl = new DataCtrl(this);
        globalStats = new GlobalStats();
        algorithm = new LZ78();
        compressionQuality = 0;
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


    public void selectLZ78(){
        algorithm = new LZ78();
    }

    public void selectLZW(){
        algorithm = new LZW();
    }

    public void selectLZSS() {
        algorithm = new LZSS();
    }

    public void selectJPEG(){
        algorithm = new JPEG();
        ((JPEG) algorithm).setQuality(compressionQuality);
    }

    public void setQuality(int quality){
        compressionQuality = quality;
    }

    public byte[] compress(byte[] file){
        globalStats.setNumberFiles(globalStats.getNumberFiles() + 1);
        return algorithm.compress(file);

    }

    public byte[] decompress(byte[] file){

        return algorithm.decompress(file);
    }


    public float getOriginalFileSize() {
        return algorithm.getOriginalSize();
    }

    public float getCompressedSize() {
        return algorithm.getCompressedSize();
    }

    public float getCompressionRatio() {
        return algorithm.getCompressionRatio();
    }

    public float getCompressionTime() {
        return algorithm.getCompressionTime();
    }

    public float getCompressionSpeed() {
        return algorithm.getCompressionSpeed();
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
