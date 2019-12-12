
package presentation.controllers;

import domain.controllers.DomainCtrl;
import presentation.View;

import java.io.File;



public class PresentationCtrl {
    private DomainCtrl domainCtrl;
    private View mainView;

    public PresentationCtrl(){
        domainCtrl = new DomainCtrl(this);
        mainView = new View("MasterCompressor", this);
    }


    public void init() {

        mainView.init(); // setup and start gui
    }

    public void setQuality(int quality) {
        domainCtrl.setQuality(quality);
    }

    public void setAlgorithm(int index) {
        domainCtrl.selectAlgorithm(index);
    }



    public void setlocalStats(float compressionTime, float compressedFileSize, float compressionDegree, float compressionSpeed, float originalFileSize) {
        mainView.setLocalStats(compressedFileSize, compressionDegree, compressionSpeed, compressionTime, originalFileSize);
    }

    public void compress(File file) {
        domainCtrl.compress(file);
    }

    public void decompress(File file) {
        domainCtrl.decompressFile(file);
    }

    public void resetValues() {
        domainCtrl.resetValues();
    }

    public void displayError(String s) {
        mainView.displayError(s);
    }
}
