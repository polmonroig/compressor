package domain.controllers;

import domain.*;
import data.controllers.DataCtrl;
import presentation.Content;
import presentation.controllers.PresentationCtrl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class DomainCtrl {
    private DataCtrl dataCtrl;
    private PresentationCtrl presentationCtrl;
    private AutoCompressor autoCompressor;
    private GlobalStats globalStats;
    private int currentId;

    public DomainCtrl(PresentationCtrl controller){
        presentationCtrl = controller;
        init();
    }

    private void init() {
        currentId = AlgorithmSet.LZ78_ID;
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
        byte[]content = readFile(file);
        if(content != null){
            pFile.setContent(content); // read and save file
            pFile.selectAlgorithm(currentId); // set compression algorithm
            pFile.compress();
            setlocalStats(pFile.getLocalStats());
            System.out.println("FileName:" + pFile.getFileName());
            System.out.println("OutPath: " + pFile.getCompletePath() + "." + pFile.getIdName());
            writeFile(pFile.getCompletePath() + "." + pFile.getIdName(), pFile.getContent());
            System.out.println("File compressed");
        }

    }


    public void decompressFile(File file){
        PhysicalFile pFile = new PhysicalFile(file);
        byte[]content = readFile(file);
        if(content != null){
            pFile.setContent(content);
            if(pFile.isAuto()){
                ArrayList<PhysicalFile> physicalFiles = autoCompressor.decompressFile(pFile);
                for(PhysicalFile f : physicalFiles){
                    System.out.println("MultFile:" +pFile.getRelativePath() + "." + pFile.getFileExtension() );
                    writeFile(pFile.getRelativePath() + "." + pFile.getFileExtension(), f.getContent());
                }
            }
            else{
                pFile.calculateId();
                pFile.decompress();
                System.out.println("SingleFilePath:" + pFile.getCompletePath() + "." + pFile.getOriginalIdName());
                writeFile(pFile.getCompletePath() + "." + pFile.getOriginalIdName(), pFile.getContent());
            }
            System.out.println("Done");
        }

    }

    private void writeFile(String s, byte[] content) {
        try{
            dataCtrl.WriteFile(s, content);
        } catch (IOException e) {
            // MOSTRAR ERROR DE ESCRITURA
            e.printStackTrace();
        }
    }

    public void compressFiles(File file)  {

        byte[] fileBytes;

        ArrayList<PhysicalFile> physicalFiles = new ArrayList<>();
        recursiveCompressFiles(new File[]{file}, physicalFiles, new StringBuilder());
        autoCompressor.setAlgorithm(currentId);
        try{
            fileBytes = autoCompressor.compressFiles(physicalFiles);
            System.out.println("File out: " + file.getPath() + "." + PhysicalFile.AUTO_EXTENSION);
            dataCtrl.WriteFile(file.getPath() + "." + PhysicalFile.AUTO_EXTENSION, fileBytes);
            System.out.println("File compressed");
        }
        catch (IOException e) {
            if(AutoCompressor.getFlag() == AutoCompressor.UNSUPPORTED_FILE) {
                // MOSTRAR MENSAJE ERROR
                e.printStackTrace();
            }
        }


    }

    private void recursiveCompressFiles(File[] files, ArrayList<PhysicalFile> physicalFiles, StringBuilder prefix){

        for(File f : files){
            if(f.isDirectory()){
                prefix.append(f.getName()).append("/");
                recursiveCompressFiles(Objects.requireNonNull(f.listFiles()), physicalFiles, prefix);
            }
            else{
                PhysicalFile pf = new PhysicalFile(f);
                byte[] content = readFile(f);

                if(content != null) {
                    pf.setContent(content);
                    pf.setRelativeDir(prefix.toString());
                    physicalFiles.add(pf);
                }
            }
        }

    }

    private byte[] readFile(File f) {
        try{
            return dataCtrl.ReadFile(f);
        } catch (IOException e) {
            // MOSTARR MENSAJE Error por pantalla
            e.printStackTrace();
        }
        return null;
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

    public void setlocalStats(Stats localStats){
        presentationCtrl.setlocalStats(localStats.getCompressionTime(), localStats.getCompressedFileSize(), localStats.getCompressionDegree(), localStats.getCompressionSpeed(), localStats.getOriginalFileSize());
    }


    public void compress(File file)  {
        if(file.isDirectory()){
            compressFiles(file);
        }
        else{
            compressFile(file);
        }
    }

    public void resetValues() {
        AlgorithmSet.setQuality(0);
        selectAlgorithm(AlgorithmSet.LZ78_ID);
    }
}
