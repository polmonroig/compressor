package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CompressionView extends JFrame{
    private JPanel panel1;
    private JButton salirButton;
    private JButton retrocederButton;
    private JButton siguienteButton;
    private JButton seleccionarArchivosButton;
    private MainView parentView;
    private FolderCompressionView nextView;
    private File[] files;


    private JFileChooser jfc;

    CompressionView(String title, MainView view){
        parentView = view;
        nextView = new FolderCompressionView();
        setTitle(title);
    }

    public void init(){
        initComponents();
        initEventListeners();
    }


    private void initComponents(){
        ImageIcon img = new ImageIcon("/home/adrian/Escritorio/compressor/DOCS/compresion.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(375, 150);
        setResizable(false);
        setContentPane(panel1);
        siguienteButton.setEnabled(false);
        this.setIconImage(img.getImage());
        setVisible(true);
        jfc = new JFileChooser();
    }

    private void initEventListeners(){
        retrocederButton.addActionListener(actionEvent -> {
            setVisible(false);
            parentView.setVisible(true);
        });


        // select files button
        seleccionarArchivosButton.addActionListener(actionEvent -> {
            jfc.setMultiSelectionEnabled(true);
            jfc.setAcceptAllFileFilterUsed(false);

            FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM or TXT, ","ppm", "txt" );
            jfc.addChoosableFileFilter(filter);
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int returnValue = jfc.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                files = jfc.getSelectedFiles();
                siguienteButton.setEnabled(true);
            }
        });

        siguienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                nextView.setVisible(true);
            }
        });

        // exit app button
        salirButton.addActionListener(e -> System.exit(0));
    }


}
