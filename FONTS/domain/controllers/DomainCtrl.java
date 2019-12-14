package domain.controllers;

import domain.*;
import data.controllers.DataCtrl;
import presentation.controllers.PresentationCtrl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
 

public class DomainCtrl {
    private DataCtrl dataCtrl;
    private PresentationCtrl presentationCtrl;
    private AutoAlgorithm autoAlgorithm;
    private GlobalStats globalStats;
    private int currentId;

    private static final int NO_ERROR = 0;
    private static final int ERROR = 1;

    public DomainCtrl(PresentationCtrl controller){
        presentationCtrl = controller;
        init();
    }

    private void init() {
        currentId = AlgorithmSet.LZ78_ID;
        dataCtrl = new DataCtrl();
        globalStats = new GlobalStats();
        autoAlgorithm = new AutoAlgorithm();
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
            if(pFile.isImage()){
                pFile.selectAlgorithm(AlgorithmSet.JPEG_ID);
            }else{
                pFile.selectAlgorithm(currentId); // set compression algorithm
            }

            pFile.compress();
            setLocalStats(pFile.getLocalStats());
            int errorInt = writeFile(pFile.getCompletePath() + "." + pFile.getIdName(), pFile.getContent());
            if(errorInt == NO_ERROR){
                presentationCtrl.displayMessage("Compresion completada", "El archivo a sido guardado en " + pFile.getCompletePath() + "." + pFile.getIdName());
            }
        }

    }


    public void decompressFile(File file){
        PhysicalFile pFile = new PhysicalFile(file);
        byte[]content = readFile(file);
        if(content != null){
            pFile.setContent(content);
            if(pFile.isAuto()){
                ArrayList<PhysicalFile> physicalFiles = autoAlgorithm.decompressFile(pFile);
                for(PhysicalFile f : physicalFiles){
                    writeFile(pFile.getFileDir() + f.getCompletePath() + "." + f.getFileExtension(), f.getContent());
                }
                presentationCtrl.displayMessage("Descompresion completada", "");
            }
            else{
                pFile.calculateId();
                pFile.decompress();
                int errorInt = writeFile(pFile.getCompletePath() + "." + pFile.getOriginalIdName(), pFile.getContent());
                if(errorInt == NO_ERROR){
                    presentationCtrl.displayMessage("Descompresion completada", "El archivo a sido guardado en " + pFile.getCompletePath() + "." + pFile.getOriginalIdName());
                }
            }
        }

    }

    private int writeFile(String s, byte[] content) {
        try{
            dataCtrl.WriteFile(s, content);
            return NO_ERROR;
        } catch (IOException e) {
            presentationCtrl.displayMessage("Error", "Error al escribir el archivo: " + s);
            return ERROR;
        }

    }

    public void compressFolder(File file)  {

        byte[] fileBytes;

        ArrayList<PhysicalFile> physicalFiles = new ArrayList<>();
        recursiveCompressFiles(new File[]{file}, physicalFiles,"");
        autoAlgorithm.setAlgorithm(currentId);
        try{
            fileBytes = autoAlgorithm.compressFiles(physicalFiles);
            setLocalStats(autoAlgorithm.getLocalStats());
            int errorInt = writeFile(file.getPath() + "." + PhysicalFile.AUTO_EXTENSION, fileBytes);
            if(errorInt == NO_ERROR){
                presentationCtrl.displayMessage("Compresion completada", "El archivo a sido guardado en " + file.getPath() + "." + PhysicalFile.AUTO_EXTENSION);
            }
        }
        catch (IOException e) {
            presentationCtrl.displayMessage("Error", "Archivo no soportado: " + AutoAlgorithm.getUnsupportedFile());
        }


    }

    private void recursiveCompressFiles(File[] files, ArrayList<PhysicalFile> physicalFiles, String prefix){

        for(File f : files){
            if(f.isDirectory()){
                recursiveCompressFiles(Objects.requireNonNull(f.listFiles()), physicalFiles, prefix + f.getName() + "/");
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
            presentationCtrl.displayMessage("Error","Error al leer el archivo: " + f.getAbsolutePath());
        }
        return null;
    }



    public void setStats(float originalFileSize, float compressedFileSize,
                         float compressionTime, float compressionDegree, float compressionSpeed) {
        globalStats.addOriginalSize(originalFileSize);
        globalStats.addCompressedSize(compressedFileSize);
        globalStats.addCompressionTime(compressionTime);
        globalStats.addCompressionDegree(compressionDegree);
        globalStats.addCompressionSpeed(compressionSpeed);
    }

    public void setLocalStats(Stats localStats){
        presentationCtrl.setLocalStats(localStats.getCompressionTime(), localStats.getCompressedFileSize(),
                                       localStats.getCompressionDegree(), localStats.getCompressionSpeed(),
                                       localStats.getOriginalFileSize());
    }


    public void compress(File file)  {
        if(file.isDirectory()){
            compressFolder(file);
        }
        else{
            compressFile(file);
        }
    }

    public void resetValues() {
        AlgorithmSet.setQuality(0);
        selectAlgorithm(AlgorithmSet.LZ78_ID);
    }

    public BufferedImage getPPM(File fileA) {
            byte [] aux = readFile(fileA);
            if(aux != null) return JPEG.makePPM(aux);
            return null;
    }
}
