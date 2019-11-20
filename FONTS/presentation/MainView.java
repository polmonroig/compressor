package presentation;

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainView extends JFrame {
    private JButton HOLa;
    private JPanel HOla;
    private JFileChooser jfc;

    public void setGUI(){
        ImageIcon img = new ImageIcon("/home/adrian/Escritorio/compressor/DOCS/compresion.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setContentPane(HOla);
        this.setIconImage(img.getImage());
        jfc = new JFileChooser();
        setVisible(true);

        HOLa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                jfc.setMultiSelectionEnabled(true);
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM or TXT", "ppm", "txt" );
                jfc.addChoosableFileFilter(filter);
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnValue = jfc.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    File[]files = jfc.getSelectedFiles();


                }
            }
        });
    }

    public static void main(String[] args) {
        MainView view = new MainView("Compressor");

        view.setGUI();
    }

    public MainView(String HOLA){
        setTitle(HOLA);
    }
}
