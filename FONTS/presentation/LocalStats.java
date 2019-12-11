package presentation;

import javax.swing.*;
import java.awt.*;


public class LocalStats extends JFrame {
    private JPanel contentpanel = new JPanel();

    public LocalStats(){
        setSize(new Dimension(500, 300));
        mainText = new JLabel("Estadisticas Locales");
        mainText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));

        layout = new GridLayout(7, 1);
        setLayout(layout);

        setTitle("MasterCompressor");
        add(mainText);
        add(new JLabel("Visualizacion de las estadisticas de la compresion realizada"));
    }

    private JLabel compressionTime = new JLabel();
    private JLabel compressionDegree = new JLabel();
    private JLabel compressionSpeed = new JLabel();
    private JLabel originalFileSize = new JLabel();
    private JLabel compressedFileSize = new JLabel();

    private JLabel mainText;

    private GridLayout layout;

    public void setLocalStats(float compressedFileSize, float compressionDegree, float compressionSpeed, float compressionTime, float originalFileSize) {
        this.compressedFileSize.setText("<html><u>Tamaño del archivo comprimido:</u><html>" + " " + Float.toString(compressedFileSize) + " Unidad");
        this.compressionDegree.setText("<html><u>Grado de compression:</u><html>" + " " + Float.toString(compressionDegree) + " Unidad");
        this.compressionSpeed.setText("<html><u>Velocidad de compression:</u><html>" + " " + Float.toString(compressionSpeed) + " Unidad");
        this.compressionTime.setText("<html><u>Tiempo de compression:</u><html>" + " " + Float.toString(compressionTime) + " Unidad");
        this.originalFileSize.setText("<html><u>Tamaño del archivo:</u><html>" + " " + Float.toString(originalFileSize) + " Unidad");
    }

    public void init(){
        add(this.originalFileSize);

        add(this.compressedFileSize);

        add(this.compressionDegree);

        add(this.compressionSpeed);

        add(this.compressionTime);
    }
}
