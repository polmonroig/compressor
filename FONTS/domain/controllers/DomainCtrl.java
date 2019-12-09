package domain.controllers;

import domain.*;
import data.controllers.DataCtrl;

import java.io.File;
import java.io.IOException;


public class DomainCtrl {
    private DataCtrl dataCtrl;
    private Algorithm algorithm;
    private AutoCompressor autoCompressor;
    private GlobalStats globalStats;
    private int currentID;

    public DomainCtrl(){
        init();
    }

    private void init() {
        currentID = 0;
        dataCtrl = new DataCtrl();
        globalStats = new GlobalStats();
        algorithm = AlgorithmSet.getAlgorithm(0);
        autoCompressor = new AutoCompressor(this);
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


    public void selectAlgorithm(int id){
        currentID = id;
        algorithm = AlgorithmSet.getAlgorithm(id);
    }
    public void setAutoID(int id){
        autoCompressor.setAlgorithm(id);
    }

    public void setQuality(int quality){
        AlgorithmSet.setQuality(quality);
    }

    public void compressFile(File file){
        globalStats.setNumberFiles(globalStats.getNumberFiles() + 1);
        byte[] byteFile = dataCtrl.ReadFile(file);
        byteFile = algorithm.compress(byteFile);
        dataCtrl.WriteFile(autoCompressor.getFileDir(file) + "compression." + currentID, byteFile);
    }

    public void decompressFile(File file){
        byte[] byteFile = dataCtrl.ReadFile(file);
        int size = file.getPath().length();
        int id = (int) file.getPath().charAt(size - 1);
        if(id != AlgorithmSet.AUTO_ID){
            selectAlgorithm(id);
            byteFile = algorithm.decompress(byteFile);
            String extension = ".txt";
            if(currentID == AlgorithmSet.JPEG_ID)extension = ".ppm";
            dataCtrl.WriteFile(autoCompressor.getFileDir(file) + autoCompressor.getFileName(file) + "." + extension,
                    byteFile);
        }
        else {
            autoCompressor.decompressFile(file);
        }

    }

    public void compressFiles(File[] files) throws IOException {
        byte[] byteFile = autoCompressor.compressFiles(files);
        dataCtrl.WriteFile(autoCompressor.getFileDir(files[0]) + "compression.mc" , byteFile);
    }



    public byte[] readFile(String file){
        return dataCtrl.ReadFile(new File(file));
    }

    public void WriteFile(String fileName, byte[] decompressedFile) {
        dataCtrl.WriteFile(fileName, decompressedFile);
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


}
