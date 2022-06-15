package appli1.ihm;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;

public class PanelAction extends JPanel implements ActionListener {

    private FrameCreation frame;

    private JButton btnNouveau, btnOuvrir, btnSauvegarder, btnSauvegarderSous;

    public PanelAction(FrameCreation frame) {
        
        this.frame = frame;

        this.setLayout(new GridLayout(1, 10));

        System.out.println(new File("").getAbsolutePath());

        this.btnOuvrir = new JButton("Ouvrir");
        this.btnNouveau = new JButton("Nouveau");
        this.btnSauvegarder = new JButton("Sauvegarder");
        this.btnSauvegarderSous = new JButton("Sauvegarder sous");
        
        this.add(this.btnOuvrir);
        this.add(this.btnNouveau);
        for (int cpt = 0; cpt < 6; cpt++) {

            this.add(new JLabel());
        }
        this.add(this.btnSauvegarder);
        this.add(this.btnSauvegarderSous);

        this.btnOuvrir.addActionListener(this);
        this.btnNouveau.addActionListener(this);
        this.btnSauvegarder.addActionListener(this);
        this.btnSauvegarderSous.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == this.btnNouveau) {

            this.frame.nouveau();
        }

        if (event.getSource() == this.btnOuvrir) {

            JFileChooser choose = new JFileChooser(
                FileSystemView
                .getFileSystemView()
                .getHomeDirectory()
            );
            // Ouvrez le fichier
            int res = choose.showOpenDialog(null);
            // Enregistrez le fichier
            // int res = choose.showSaveDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = choose.getSelectedFile();
                this.frame.ouvrir(file.getAbsolutePath());
            }
        }
        // else if (event.getSource() == this.btnOuvrir) {

        //     this.frame.ouvrir();
        // }
        // else if (event.getSource() == this.btnSauvegarder) {

        //     this.frame.sauvegarder();
        // }
        // else if (event.getSource() == this.btnSauvegarderSous) {

        //     this.frame.sauvegarderSous();
        // }
    }
}