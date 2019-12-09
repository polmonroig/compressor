package domain.controllers;

import domain.*;
import data.controllers.DataCtrl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class DomainCtrl {
    private DataCtrl dataCtrl;
    private AutoCompressor autoCompressor;
    private GlobalStats globalStats;
    private int currentId;

    public DomainCtrl(){
        init();
    }

    private void init() {
        currentId = 0;
        dataCtrl = new DataCtrl();
        globalStats = new GlobalStats();
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
        currentId = id;
    }


    public void setQuality(int quality){
        AlgorithmSet.setQuality(quality);
    }

    public void compressFile(File file){
        globalStats.setNumberFiles(globalStats.getNumberFiles() + 1);
        PhysicalFile pFile = new PhysicalFile(file);
        pFile.setContent(dataCtrl.ReadFile(file)); // read and save file
        pFile.selectAlgorithm(currentId); // set compression algorithm
        pFile.compress();
        dataCtrl.WriteFile(pFile.getCompletePath() + "." + pFile.getIdName(), pFile.getContent());
    }


    public void decompressFile(File file){
        PhysicalFile pFile = new PhysicalFile(file);
        pFile.setContent(dataCtrl.ReadFile(file));
        if(pFile.isAuto()){
            ArrayList<PhysicalFile> physicalFiles = autoCompressor.decompressFile(pFile);
            for(PhysicalFile f : physicalFiles){
                dataCtrl.WriteFile(pFile.getRelativePath() + "." + pFile.getFileExtension(), f.getContent());
            }
        }
        else{
            pFile.decompress();
            dataCtrl.WriteFile(pFile.getCompletePath() + "." + pFile.getIdName(), pFile.getContent());
        }
    }

    public void compressFiles(File[] files) throws IOException {

        ArrayList<PhysicalFile> physicalFiles = new ArrayList<>();
        recursiveCompressFiles(files, physicalFiles, new StringBuilder());
        autoCompressor.setAlgorithm(currentId);
        byte[] fileBytes = autoCompressor.compressFiles(physicalFiles);
        dataCtrl.WriteFile(physicalFiles.get(0).getFileDir() + "compression." + PhysicalFile.AUTO_EXTENSION, fileBytes);

    }

    public void recursiveCompressFiles(File[] files, ArrayList<PhysicalFile> physicalFiles, StringBuilder prefix){

        for(File f : files){
            if(f.isDirectory()){
                prefix.append(f.getName()).append("/");
                recursiveCompressFiles(f.listFiles(), physicalFiles, prefix);
            }
            else{
                PhysicalFile pf = new PhysicalFile(f);
                pf.setContent(dataCtrl.ReadFile(f));
                pf.setRelativeDir(prefix.toString());
                physicalFiles.add(pf);
            }
        }

    }

/*
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
    }*/

    public void setStats(float originalFileSize, float compressedFileSize, float compressionTime, float compressionDegree, float compressionSpeed) {
        globalStats.addOriginalSize(originalFileSize);
        globalStats.addCompressedSize(compressedFileSize);
        globalStats.addCompressionTime(compressionTime);
        globalStats.addCompressionDegree(compressionDegree);
        globalStats.addCompressionSpeed(compressionSpeed);
    }


}
