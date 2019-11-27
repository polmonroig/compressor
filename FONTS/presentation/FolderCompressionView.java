package presentation;

import javax.swing.*;

public class FolderCompressionView extends JFrame{
    private JPanel panel1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton salirButton;
    private JButton retrocederButton;
    private JButton siguienteButton;

    FolderCompressionView(){
        ImageIcon img = new ImageIcon("/home/adrian/Escritorio/compressor/DOCS/compresion.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(375, 150);
        setResizable(false);
        setContentPane(panel1);
        siguienteButton.setEnabled(false);
        this.setIconImage(img.getImage());
        setVisible(false);
    }
}
