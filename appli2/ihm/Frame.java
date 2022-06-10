package appli2.ihm;

import appli1.Controleur;
import appli1.Reseau;
import javax.swing.JFrame;

public class Frame extends JFrame {
    private Panel panel;

    public Frame(Controleur ctrl, Reseau reseau) {
        this.setTitle("ton indentation de merde");
        this.setSize(500, 500);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.panel = new Panel(ctrl, reseau);
        this.add(this.panel);

        this.setVisible(true);
    }
}