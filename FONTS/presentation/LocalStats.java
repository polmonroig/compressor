package presentation;

import javax.swing.*;
import java.awt.*;


public class LocalStats extends JFrame {
    private JPanel contentpanel = new JPanel();

    public LocalStats(){
        setSize(new Dimension(500, 300));
    }

    private JLabel compressionTime;
    private JLabel compressionDegree;
    private JLabel compressionSpeed;
    private JLabel originalFileSize;
    private JLabel compressedFileSize;
    private JLabel mainText;

    private GridLayout layout;

    public void setLocalStats(float compressedFileSize, float compressionDegree, float compressionSpeed, float compressionTime, float originalFileSize) {
        this.compressedFileSize = new JLabel("<html><u>Tamaño del archivo comprimido:</u><html>" + " " + Float.toString(compressedFileSize) + " Unidad");
        this.compressionDegree = new JLabel("<html><u>Grado de compression:</u><html>" + " " + Float.toString(compressionDegree) + " Unidad");
        this.compressionSpeed = new JLabel("<html><u>Velocidad de compression:</u><html>" + " " + Float.toString(compressionSpeed) + " Unidad");
        this.compressionTime = new JLabel("<html><u>Tiempo de compression:</u><html>" + " " + Float.toString(compressionTime) + " Unidad");
        this.originalFileSize = new JLabel("<html><u>Tamaño del archivo:</u><html>" + " " + Float.toString(originalFileSize) + " Unidad");
        init();
    }
    private void init(){
        mainText = new JLabel("Estadisticas Locales");
        mainText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));

        layout = new GridLayout(7, 1);
        setLayout(layout);

        setTitle("MasterCompressor");
        add(mainText);
        add(new JLabel("Visualizacion de las estadisticas de la compresion realizada"));

        add(this.originalFileSize);

        add(this.compressedFileSize);

        add(this.compressionDegree);

        add(this.compressionSpeed);

        add(this.compressionTime);
    }
}
