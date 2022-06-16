package appli2.ihm;

import static java.lang.Math.round;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.*;

import appli2.Controleur;
import metier.reseau.Reseau;
import metier.Cuve;


public class PanelRendu extends JPanel implements MouseMotionListener
{
    private Controleur ctrl;
    private Reseau reseau;

    public PanelRendu(Controleur ctrl, Reseau reseau) {
        this.ctrl = ctrl;
        this.reseau = reseau;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Color couleurInitiale = g.getColor();

        /* Variable */
        int x, y, width, height;


        for (Cuve cuve : this.reseau.getEnsCuves())
        {
            // Détermination de la position de la cuve
            x = cuve.getPosition().x() - (cuve.getPosition().x() / 2);
            y = cuve.getPosition().y() - (cuve.getPosition().x() / 2);

            // Détermination de la taille des cuves sur l'ihm
            width  = (cuve.getCapacite() / 10) * (this.ctrl.getWidthFrame() / 400);
            height = (cuve.getCapacite() / 10) * (this.ctrl.getWidthFrame() / 400);

            // Détermination de la couleur de la cuve
            Color couleur = new Color(255, 255, 255);
            int temp = (int)(round(cuve.getContenu() / 2));
            if (temp <= 255)
            {
                // du blanc vers le rouge
                couleur = new Color(255, 255-temp, 255-temp);
            }
            else
            {
                // du noir vers le rouge
                couleur = new Color(500-temp, 0, 0);
            }
  


            g.setColor(couleur);
            g.fillOval(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, width, height);

        }

        
        g.setColor(couleurInitiale);
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        System.out.println(e.getX() + " " + e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        System.out.println(e.getX() + " " + e.getY());
    }

    
}
