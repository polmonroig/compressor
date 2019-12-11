package presentation;

import javax.swing.*;
import java.awt.*;


public class LocalStats extends JFrame {
    private AboutContent contentpanel;

    public LocalStats(){
        this.contentpanel = new AboutContent("Estadisticas Locales", "Estadisticas locales de la compression", 0);
        setSize(new Dimension(700, 300));
    }

    private JLabel compressionTime;
    private JLabel compressionDegree;
    private JLabel compressionSpeed;
    private JLabel originalFileSize;
    private JLabel compressedFileSize;


    public void setLocalStats(float compressedFileSize, float compressionDegree, float compressionSpeed, float compressionTime, float originalFileSize) {
        this.compressedFileSize = new JLabel(Float.toString(compressedFileSize));
        this.compressionDegree = new JLabel(Float.toString(compressionDegree));
        this.compressionSpeed = new JLabel(Float.toString(compressionSpeed));
        this.compressionTime = new JLabel(Float.toString(compressionTime));
        this.originalFileSize = new JLabel(Float.toString(originalFileSize));
        contentpanel.add(this.compressedFileSize);
        contentpanel.add(this.compressionDegree);
        contentpanel.add(this.compressionSpeed);
        contentpanel.add(this.compressionTime);
        contentpanel.add(this.originalFileSize);
    }
}
