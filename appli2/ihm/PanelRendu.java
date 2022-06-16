package appli2.ihm;

import static java.lang.Math.round;
import static java.lang.Math.abs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.*;

import appli2.Controleur;
import metier.reseau.Reseau;
import metier.Cuve;
import metier.Tube;


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


        for (Tube tube : this.reseau.getEnsTubes())
        {
            // Détermination de la position du tube
            int xOrig = tube.getCuveA().getPosition().x() + (tube.getCuveA().getPosition().x() / 2);
            int yOrig = tube.getCuveA().getPosition().y() + (tube.getCuveA().getPosition().x() / 2);

            int xDest = tube.getCuveB().getPosition().x() + (tube.getCuveB().getPosition().x() / 2);
            int yDest = tube.getCuveB().getPosition().y() + (tube.getCuveB().getPosition().x() / 2);

            int xSection = (xOrig + xDest) / 2;
            int ySection = (yOrig + yDest) / 2;

            if (abs(xOrig - xDest) < abs(yOrig - yDest))
            {
                g.drawString(""+tube.getSection(), xSection+tube.getSection(), ySection);
            }
            else
            {
                g.drawString(""+tube.getSection(), xSection, ySection+tube.getSection());
            }
            

            
            // Dessin du tube
            g.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new java.awt.BasicStroke(tube.getSection()));
            g2.drawLine(xOrig, yOrig, xDest, yDest);

            g2.setStroke(new java.awt.BasicStroke(1));
        }


        for (Cuve cuve : this.reseau.getEnsCuves())
        {
            // Détermination de la position de la cuve
            x = cuve.getPosition().x();
            y = cuve.getPosition().y();

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
  

            // Dessin des cuves
            g.setColor(couleur);
            g.fillOval(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, width, height);


            // Dessin des noms des cuves


            // Dessins des tubes
            
                

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
