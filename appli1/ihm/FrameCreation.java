package appli1.ihm;

import javax.swing.*;
import appli1.Controleur;

import java.awt.BorderLayout;

public class FrameCreation extends JFrame {

    private Controleur ctrl;

    public FrameCreation(Controleur ctrl) {

        this.ctrl = ctrl;

        this.setTitle("Création d'un réseau");
        this.setSize(500, 500);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.add(new PanelAction(this), BorderLayout.NORTH);

        this.setVisible(true);

        this.pack();
    }

    public void nouveau() {

        this.add(new JScrollPane(new PanelCreation(ctrl, this, null)), BorderLayout.CENTER);
        this.pack();
    }

    public void ouvrir(String path) {

        
        this.pack();
    }
}