
package presentation.controllers;

import domain.controllers.DomainCtrl;
import presentation.MainView;
import presentation.View;

import java.io.File;
import java.io.IOException;


public class PresentationCtrl {
    private DomainCtrl domainCtrl;
    private View mainView;
    private File[] files;
    private File file;

    public PresentationCtrl(){
        domainCtrl = new DomainCtrl();
        mainView = new View("MasterCompressor", this);
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


    public void compressFile(int index){
        domainCtrl.selectAlgorithm(index);
        domainCtrl.compressFile(file);
    }

    public void decompressFile() {
        domainCtrl.decompressFile(file);
    }

    public void setFile(File selectedFile) {
        file = selectedFile;
    }
}
