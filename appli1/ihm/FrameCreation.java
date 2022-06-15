package appli1.ihm;

import javax.swing.*;
import appli1.Controleur;

import java.awt.BorderLayout;

public class FrameCreation extends JFrame {
    
    private static final int RIEN    = 0;
    private static final int NOUVEAU = 1;
    private static final int OUVERT  = 2;

    private static final int AUCUN   = 0;
    private static final int TRAVAIL = 1;
    
    private Controleur ctrl;

    private int statutFichier;
    private int statutTravail;

    public FrameCreation(Controleur ctrl) {

        this.ctrl = ctrl;

        this.statutFichier = FrameCreation.RIEN;
        this.statutTravail = FrameCreation.AUCUN;

        this.setTitle("Création d'un réseau");
        this.setSize(500, 500);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.add(new PanelAction(this), BorderLayout.NORTH);

        this.setVisible(true);

        this.pack();
    }

    public void nouveau() {

        if (this.statutTravail == FrameCreation.AUCUN) {

            this.statutFichier = FrameCreation.NOUVEAU;
            this.statutTravail = FrameCreation.TRAVAIL;

            this.add(new JScrollPane(new PanelCreation(ctrl, this, null)), BorderLayout.CENTER);
        }
        else {

            int result = JOptionPane.showConfirmDialog(this, "Press any button to close the dialog.");
        }
        
        this.pack();
    }

    public void ouvrir(String path) {

        
        this.pack();
    }
}