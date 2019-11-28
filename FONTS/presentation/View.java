package presentation;

import presentation.controllers.PresentationCtrl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JFrame {

    // set reference to the controller calling it
    private PresentationCtrl presentationCtrl;

    // a view is made of a buttons panel and a content panel
    private JSplitPane separator;
    private ButtonsPanel buttonsPanel;
    private ContentPanel contentPanel;

    // define the layout
    private GridLayout layout;
    private static final int N_COLS = 1;
    private static final int N_ROWS = 1;
    private static final String[] buttonNames = {"About", "Comprimir archivo de texto", "Comprimir imagen",
                                                 "Comprimir carpeta", "Descomprimir",
                                                 "Informacion de uso", "Estadisticas Globales"};




    public View(String title, PresentationCtrl controller){
        setTitle(title);

        presentationCtrl = controller;
        layout = new GridLayout(N_ROWS, N_COLS);
        buttonsPanel = new ButtonsPanel(buttonNames, this);


        ArrayList<Content> contents = new ArrayList<>();
        contents.add(new AboutContent("Bienvenidos a Master Compressor", "Contenido del compressor", 0));
        contents.add(new CompressTextContent("Comprimir archivo de texto", "Aqui puedes comprimir tus archivos de texto", 1));
        contentPanel = new ContentPanel(contents);
        // init separator

        separator = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buttonsPanel, contentPanel);
        separator.setDividerLocation(350);
        separator.setEnabled(false);
        separator.setDividerSize(0);
        separator.setFocusable(false);
        // remove undesirable border
        separator.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }


    public void init(){
        initComponents();
    }

    private void initComponents() {
        // setup layout variables
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 500);
        setLayout(layout);

        // init components
        buttonsPanel.init();
        contentPanel.init();
        add(separator);



        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        // finally set view to visible
        setVisible(true);
    }

    public void selectView(int id) {
        contentPanel.selectView(id);
    }
}
