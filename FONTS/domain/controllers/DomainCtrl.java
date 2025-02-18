package domain.controllers;

import domain.*;
import data.controllers.DataCtrl;
import presentation.controllers.PresentationCtrl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * The type Domain ctrl.
 */
public class DomainCtrl {
    private DataCtrl dataCtrl;
    private PresentationCtrl presentationCtrl;
    private AutoAlgorithm autoAlgorithm;
    private GlobalStats globalStats;
    private int currentId;
    private String globalStatsFilePath;

    private static final int NO_ERROR = 0;
    private static final int ERROR = 1;

    /**
     * Instantiates a new Domain ctrl.
     *
     * @param controller the controller
     */
    public DomainCtrl(PresentationCtrl controller){
        presentationCtrl = controller;
    }

    /**
     * <p>Init.</p>
     */
    public void init() {
        currentId = AlgorithmSet.LZ78_ID;
        dataCtrl = new DataCtrl();
        globalStats = new GlobalStats();
        autoAlgorithm = new AutoAlgorithm();
        globalStatsFilePath = System.getProperty("user.dir") + File.separator +"GlobalStatesSave.txt";
        readGlobalStats();
    }

    private void readGlobalStats() {
        byte[] stats = readFile(new File(globalStatsFilePath));
        if(stats != null) {
            globalStats.setFileStats(new String(stats));
        }
        presentationCtrl.setGlobalStats(
                globalStats.getNumberFiles(),
                globalStats.getCompressionTime(), globalStats.getCompressedFileSize(),
                globalStats.getCompressionDegree(), globalStats.getCompressionSpeed(),
                globalStats.getOriginalFileSize()
        );
    }


    /**
     * <p>Select algorithm.</p>
     *
     * @param id the id
     */
    public void selectAlgorithm(int id){
        currentId = id;
    }


    /**
     * <p>Set quality.</p>
     *
     * @param quality the quality
     */
    public void setQuality(int quality){
        AlgorithmSet.setQuality(quality);
    }

    private void compressFile(File file){

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
            int errorInt = writeFile(pFile.getCompletePath() + "." + pFile.getIdName(), pFile.getContent());
            if(errorInt == NO_ERROR){
                globalStats.setNumberFiles(globalStats.getNumberFiles() + 1);
                setLocalStats(pFile.getLocalStats());
                presentationCtrl.displayMessage("Compresion completada", "El archivo a sido guardado en " + pFile.getCompletePath() + "." + pFile.getIdName());
            }

        }

    }


    /**
     * <p>Decompress file.</p>
     *
     * @param file the file
     */
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
            dataCtrl.write(s, content);
            return NO_ERROR;
        } catch (IOException e) {
            presentationCtrl.displayMessage("Error", "Error al escribir el archivo: " + s);
            return ERROR;
        }

    }

    private void compressFolder(File file)  {

        byte[] fileBytes;

        ArrayList<PhysicalFile> physicalFiles = new ArrayList<>();
        recursiveCompressFiles(new File[]{file}, physicalFiles,"");
        autoAlgorithm.setAlgorithm(currentId);
        try{
            fileBytes = autoAlgorithm.compressFiles(physicalFiles);

            int errorInt = writeFile(file.getPath() + "." + PhysicalFile.AUTO_EXTENSION, fileBytes);
            if(errorInt == NO_ERROR){
                globalStats.setNumberFiles(globalStats.getNumberFiles() + physicalFiles.size());
                setLocalStats(autoAlgorithm.getLocalStats());
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
                recursiveCompressFiles(Objects.requireNonNull(f.listFiles()), physicalFiles, prefix + f.getName() + File.separator);
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
            return dataCtrl.read(f);
        } catch (IOException e) {
            presentationCtrl.displayMessage("Error","Error al leer el archivo: " + f.getAbsolutePath());
        }
        return null;
    }


    private void setLocalStats(Stats localStats){
        globalStats.setStats(localStats);
        presentationCtrl.setGlobalStats(
                globalStats.getNumberFiles(),
                globalStats.getCompressionTime(), globalStats.getCompressedFileSize(),
                globalStats.getCompressionDegree(), globalStats.getCompressionSpeed(),
                globalStats.getOriginalFileSize()
        );
        presentationCtrl.setLocalStats(localStats.getCompressionTime(), localStats.getCompressedFileSize(),
                                       localStats.getCompressionDegree(), localStats.getCompressionSpeed(),
                                       localStats.getOriginalFileSize());
        String data = globalStats.getAllStats();
        writeFile(globalStatsFilePath, data.getBytes());
    }


    /**
     * <p>Compress.</p>
     *
     * @param file the file
     */
    public void compress(File file)  {
        if(file.isDirectory()){
            compressFolder(file);
        }
        else{
            compressFile(file);
        }
    }

    /**
     * <p>Reset values.</p>
     */
    public void resetValues() {
        AlgorithmSet.setQuality(0);
        selectAlgorithm(AlgorithmSet.LZ78_ID);
    }

    /**
     * <p>Gets ppm.</p>
     *
     * @param fileA the file a
     * @return the ppm
     */
    public BufferedImage getPPM(File fileA) {
            byte [] aux = readFile(fileA);
            if(aux != null) return JPEG.makePPM(aux);
            return null;
    }
}
