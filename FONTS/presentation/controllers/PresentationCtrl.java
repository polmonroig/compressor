
package presentation.controllers;

import domain.controllers.DomainCtrl;
import presentation.View;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * The type Presentation ctrl.
 */
public class PresentationCtrl {
    private DomainCtrl domainCtrl;
    private View mainView;

    /**
     * Instantiates a new Presentation ctrl.
     */
    public PresentationCtrl(){
        domainCtrl = new DomainCtrl(this);
        mainView = new View("MasterCompressor", this);
    }


    /**
     * <p>Init.</p>
     */
    public void init() {

        mainView.init(); // setup and start gui
        domainCtrl.init();
    }

    /**
     * <p>Sets quality.</p>
     *
     * @param quality the quality
     */
    public void setQuality(int quality) {
        domainCtrl.setQuality(quality);
    }

    /**
     * <p>Sets algorithm.</p>
     *
     * @param index the index
     */
    public void setAlgorithm(int index) {
        domainCtrl.selectAlgorithm(index);
    }


    /**
     * <p>Sets local stats.</p>
     *
     * @param compressionTime    the compression time
     * @param compressedFileSize the compressed file size
     * @param compressionDegree  the compression degree
     * @param compressionSpeed   the compression speed
     * @param originalFileSize   the original file size
     */
    public void setLocalStats(float compressionTime, float compressedFileSize, float compressionDegree, float compressionSpeed, float originalFileSize) {
        mainView.setLocalStats(compressedFileSize, compressionDegree, compressionSpeed, compressionTime, originalFileSize);
    }

    /**
     * <p>Compress.</p>
     *
     * @param file the file
     */
    public void compress(File file) {
        domainCtrl.compress(file);
    }

    /**
     * <p>Decompress.</p>
     *
     * @param file the file
     */
    public void decompress(File file) {
        domainCtrl.decompressFile(file);
    }

    /**
     * <p>Reset values.</p>
     */
    public void resetValues() {
        domainCtrl.resetValues();
    }

    /**
     * <p>Display message.</p>
     *
     * @param title   the title
     * @param message the message
     */
    public void displayMessage(String title, String message) {
        mainView.displayMessage(title, message);
    }


    /**
     * <p>Gets ppm</p>
     *
     * @param fileA the file a
     * @return the ppm
     */
    public BufferedImage getPPM(File fileA)  {
        return domainCtrl.getPPM(fileA);
    }

    /**
     * <p>Sets global stats.</p>
     *
     * @param nFiles             the n files
     * @param compressionTime    the compression time
     * @param compressedFileSize the compressed file size
     * @param compressionDegree  the compression degree
     * @param compressionSpeed   the compression speed
     * @param originalFileSize   the original file size
     */
    public void setGlobalStats(float nFiles, float compressionTime, float compressedFileSize, float compressionDegree, float compressionSpeed, float originalFileSize) {
        mainView.setGlobalStats(nFiles, compressionTime, compressedFileSize, compressionDegree,
                                compressionSpeed, originalFileSize);
    }
}
