package appli1.ihm;

import appli1.Controleur;

import javax.swing.*;

public class PanelAction extends JPanel {

    private Controleur ctrl;

    public PanelAction(Controleur ctrl) {
        
        this.ctrl = ctrl;

        this.add(new JLabel("Panel actions"));
    }
}