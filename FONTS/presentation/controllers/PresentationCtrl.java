
package presentation.controllers;

import data.controllers.DataCtrl;
import domain.controllers.DomainCtrl;
import presentation.MainView;

import java.io.File;
import java.io.IOException;


public class PresentationCtrl {
    private DomainCtrl domainCtrl;
    private MainView mainView;
    private File[] files;
    private File file;

    public PresentationCtrl(){
        domainCtrl = new DomainCtrl();
        mainView = new MainView("MasterCompressor", this);
    }

    public void setFiles(File[] f){
        files = f;
    }

    public void init() {

        mainView.init(); // setup and start gui
    }

    public void compressFiles() throws IOException {
        domainCtrl.compressFiles(files);
    }

    public void decompressFile() {
        domainCtrl.decompressFile(file);
    }

    public void setFile(File selectedFile) {
        file = selectedFile;
    }
}
