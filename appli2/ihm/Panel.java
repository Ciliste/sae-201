package appli1.ihm;

import appli1.Controleur;
import appli1.Reseau;
import metier.Cuve;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

public class Panel extends JPanel {
    private Controleur ctrl;
    private Reseau reseau;

    public Panel(Controleur ctrl, Reseau reseau) {
        this.ctrl = ctrl;
        this.reseau = reseau;
    }

    public void paint(Graphics g) {
        super.paint(g);

        // List<Cuve> ensCuve = new ArrayList<Cuve>();

        for (Cuve c : this.reseau.getEnsCuves()) {
            g.drawOval((int) (this.ctrl.getWidth() * 0.10), (int) (this.ctrl.getHeight() * 0.10),
                    c.getCapacite() / 10, c.getCapacite() / 10);
        }
    }
}