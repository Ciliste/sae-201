package appli2.ihm;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import appli2.Controleur;
import appli2.Reseau;
import metier.Cuve;


public class Panel extends JPanel {
    private Controleur ctrl;
    private Reseau reseau;

    public Panel(Controleur controleur, appli2.Reseau reseau2) {
        this.ctrl = controleur;
        this.reseau = reseau2;
    }

    public void paint(Graphics g) {
        super.paint(g);

        

        int x = 0;

        int cpt = 0;
        for (int i = 0; i < this.reseau.getEnsCuves().size(); i++)
        {
            Cuve cuve = this.reseau.getEnsCuves().get(i);

            int width  = (cuve.getCapacite() / 10) * (this.ctrl.getWidthFrame() / 400);
            int height = (cuve.getCapacite() / 10) * (this.ctrl.getWidthFrame() / 400);

            int y = cuve.getCapacite() / 20;
            //System.out.println(((this.ctrl.getWidthFrame() / 100) + 1));
            if (i+1 >= this.reseau.getEnsCuves().size())
            {
                x = 0;
                y += (cuve.getCapacite() / 10) + 10 + width/2;
            }
            else
            {
                x += (cuve.getCapacite() / 10) + 10 + width/2;
            }


            //System.out.println(cuve.getCapacite() + " : " + x);


            Color couleur = new Color(0, 0, 0);
            if ((int) cuve.getContenu() / 2 <= 255)
            {
                couleur = new Color((int) cuve.getContenu() / 2, 0, 0);
            }
            else
            {
                couleur = new Color(255, (int) ((cuve.getContenu()/2)-255), (int) ((cuve.getContenu()/2)-255));
            }
            

            g.setColor(couleur);
            g.fillOval(x, y, width, height);

            

            cpt ++;
        }

        
    }
}
