package appli2.ihm;

import static java.lang.Math.round;
import static java.lang.Math.abs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

import appli2.Controleur;
import metier.reseau.Reseau;
import metier.Cuve;
import metier.Position;
import metier.Tube;


public class PanelRendu extends JPanel implements MouseListener, MouseMotionListener
{
    private Controleur ctrl;
    private Reseau reseau;

    private Cuve cuveActive;


    public PanelRendu(Controleur ctrl, Reseau reseau) {
        this.ctrl = ctrl;
        this.reseau = reseau;

        this.addMouseListener      (this);
        this.addMouseMotionListener(this);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Color couleurInitiale = g.getColor();

        /* Variable */ /* je l'ai instancie explicitement pour éviter le problème du "not initialised" */
        int xCuve  = 0,
            yCuve  = 0;

        int xOrig = 0,
            yOrig = 0,
            xDest = 0,
            yDest = 0;

        int xSection = 0,
            ySection = 0;

        int xPosInfo = 0,
            yPosInfo = 0;

        int widthCuve  = 0,
            heightCuve = 0;

        Color couleur = new Color(0, 0, 0);


        int cpt = 0;
        for (Tube tube : this.reseau.getEnsTubes())
        {
            // Détermination de la position et de la taille du tube
            xOrig = (tube.getCuveA().getPosition().x());
            yOrig = (tube.getCuveA().getPosition().y());
            xDest = (tube.getCuveB().getPosition().x());
            yDest = (tube.getCuveB().getPosition().y());

            xSection = (xOrig + xDest) / 2;
            ySection = (yOrig + yDest) / 2;

            // Dessin des tubes
            g.setColor(Color.GRAY);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new java.awt.BasicStroke(tube.getSection()));
            g2.drawLine(xOrig, yOrig, xDest, yDest);

            // affichage de la section des tubes
            g.setColor(Color.BLACK);
            g2.setStroke(new java.awt.BasicStroke(1));
            if (abs(xOrig-xDest) > abs(yOrig-yDest))
                g.drawString(""+tube.getSection(), xSection, ySection+20);
            else
                g.drawString(""+tube.getSection(), xSection+20, ySection);


            cpt ++;
        }


        // Determination de la position des cuves + de la couleur + de la taille + Dessin des cuves
        cpt = 0;
        for (Cuve cuve : this.reseau.getEnsCuves())
        {
            // Determination de la taille
            widthCuve  = cuve.getCapacite()/10;
            heightCuve = cuve.getCapacite()/10;

            // Determination de la position
            yCuve = cuve.getPosition().y() - widthCuve  / 2;
            xCuve = cuve.getPosition().x() - heightCuve / 2;


            // Determination de la couleur
            int temp = (int)(round(cuve.getContenu() / 2));
            if (temp <= 255)
                // du blanc vers le rouge
                couleur = new Color(255, 255-temp, 255-temp);
            else
                // du noir vers le rouge
                couleur = new Color(500-temp, 0, 0);


            // Dessin des cuves
            g.setColor(couleur);
            g.fillOval(xCuve, yCuve, widthCuve, heightCuve);
            g.setColor(Color.BLACK);
            g.drawOval(xCuve, yCuve, widthCuve, heightCuve);

            // Determination de la position des informations
            switch (cuve.getPosInfo().getValeur())
            {
                case 0  : { xPosInfo = cuve.getPosition().x(); yPosInfo = cuve.getPosition().y()+ cuve.getCapacite()/2; }
                case 1  : { xPosInfo = cuve.getPosition().x(); yPosInfo = cuve.getPosition().y(); }
                case 2  : { xPosInfo = cuve.getPosition().x(); yPosInfo = cuve.getPosition().y(); }
                case 3  : { xPosInfo = cuve.getPosition().x(); yPosInfo = cuve.getPosition().y(); }
                default : { xPosInfo = cuve.getPosition().x()+cuve.getCapacite()/2; yPosInfo = cuve.getPosition().y()+cuve.getCapacite()/2; }
            }

            // Affichage des informations
            g.setColor(Color.BLACK);
            g.drawString("Cuve : " + cuve.getIdentifiant(), xPosInfo, yPosInfo+20);
            g.drawString(cuve.getContenu() + "/" + cuve.getCapacite(), xPosInfo, yPosInfo);


            cpt++;
        }


        g.setColor(couleurInitiale);
    }



    @Override
    public void mouseDragged(MouseEvent e)
    {
        if (this.cuveActive != null)
        {
            this.cuveActive.setPosition(new Position(e.getX(), e.getY()));
            this.repaint();
        }
    }


    // utilise pour capturer le clique de l'utilisateur
    @Override
    public void mousePressed(MouseEvent e)
    {
        for (Cuve cuve : this.reseau.getEnsCuves())
        {
            if (e.getX() > cuve.getPosition().x() - cuve.getCapacite()/20 && e.getX() < cuve.getPosition().x() + cuve.getCapacite()/20 &&
                e.getY() > cuve.getPosition().y() - cuve.getCapacite()/20 && e.getY() < cuve.getPosition().y() + cuve.getCapacite()/20 )
            {
                this.cuveActive = cuve;
                break;
            }
        }
    }



    // permet d'enlever le focus de la dernière cuve active
    @Override
    public void mouseReleased(MouseEvent e)
    {
        this.cuveActive = null;
    }


    // Inutile
    public void mouseMoved  (MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited (MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}

    
}


// ancien code pour les tubes
/*
            // Détermination de la position du tube
            int xOrig = tube.getCuveA().getPosition().xCuve() + (tube.getCuveA().getPosition().xCuve());
            int yOrig = tube.getCuveA().getPosition().yCuve() + (tube.getCuveA().getPosition().xCuve());

            int xDest = tube.getCuveB().getPosition().xCuve() + (tube.getCuveB().getPosition().xCuve());
            int yDest = tube.getCuveB().getPosition().yCuve() + (tube.getCuveB().getPosition().xCuve());

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
*/




// ancien code pour les cuves
/*
            // Détermination de la position de la cuve
            xCuve = cuve.getPosition().xCuve();
            yCuve = cuve.getPosition().yCuve();

            // Détermination de la taille des cuves sur l'ihm
            widthCuve  = (cuve.getCapacite() / 10) * (this.ctrl.getWidthFrame() / 400);
            heightCuve = (cuve.getCapacite() / 10) * (this.ctrl.getWidthFrame() / 400);

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
            g.fillOval(xCuve, yCuve, widthCuve, heightCuve);
            g.setColor(Color.BLACK);
            g.drawOval(xCuve, yCuve, widthCuve, heightCuve);


            // Dessin des noms des cuves
            switch (cuve.getPosInfo().getValeur())
            {
                case 0 -> g.drawString(""+cuve.getIdentifiant(), xCuve + widthCuve + 5, yCuve + heightCuve / 2);
                case 1 -> g.drawString(""+cuve.getIdentifiant(), xCuve + widthCuve / 2, yCuve + heightCuve + 5);
                case 2 -> g.drawString(""+cuve.getIdentifiant(), xCuve - widthCuve - 5, yCuve + heightCuve / 2);
                case 3 -> g.drawString(""+cuve.getIdentifiant(), xCuve + widthCuve / 2, yCuve - heightCuve - 5);

                default -> g.drawString(""+cuve.getIdentifiant(), xCuve + widthCuve + 5, yCuve + heightCuve / 2);
            }
*/