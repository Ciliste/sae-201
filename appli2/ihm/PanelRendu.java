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

    private int xOrigSourie;
    private int yOrigSourie;

    private int newXCuve;
    private int newYCuve;

    private int width ;
    private int height;

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

        Tube tube = null;

        Color couleur = new Color(0, 0, 0);

        int WidthPanelAction  = this.ctrl.getWidthPanelAction();
        int HeightPanelAction = this.ctrl.getHeightPanelAction();

        List<JPanel> lstPanel = new ArrayList<JPanel>();


        // Création d'un panel pour chaque cuve
        for (Cuve cuve : this.reseau.getEnsCuves())
            lstPanel.add(new JPanel());


        // Determination de la position des cuves + de la couleur + de la taille + Dessin des cuves
        int cpt = 0;
        for (Cuve cuve : this.reseau.getEnsCuves())
        {
            // Determination de la taille
            width  = cuve.getCapacite()/10;
            height = cuve.getCapacite()/10;

            // Determination de la position
            yCuve = cuve.getPosition().y() - width  / 2;
            xCuve = cuve.getPosition().x() - height / 2;


            // Determination de la couleur
            int temp = (int)(round(cuve.getContenu() / 2));
            if (temp <= 255)
                // du blanc vers le rouge
                couleur = new Color(255, 255-temp, 255-temp);
            else
                // du noir vers le rouge
                couleur = new Color(500-temp, 0, 0);


            // Determination de la position des tubes + de la taille + Dessin des tubes
            if (cpt < this.reseau.getEnsTubes().size())
            {
                tube = this.reseau.getEnsTubes().get(cpt);

                // Détermination de la position du tube
                xOrig = (tube.getCuveA().getPosition().x());
                yOrig = (tube.getCuveA().getPosition().y());
                xDest = (tube.getCuveB().getPosition().x());
                yDest = (tube.getCuveB().getPosition().y());


                //xSection = (xOrig + xDest) / 2;
                //ySection = (yOrig + yDest) / 2;
            }


            // Dessin des tubes
            if (tube != null)
            {
                g.setColor(Color.BLACK);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new java.awt.BasicStroke(tube.getSection()));
                g2.drawLine(xOrig, yOrig, xDest, yDest);

                g2.setStroke(new java.awt.BasicStroke(1));
            }


            // Dessin des cuves
            g.setColor(couleur);
            g.fillOval(xCuve, yCuve, width, height);
            g.setColor(Color.BLACK);
            g.drawOval(xCuve, yCuve, width, height);


            cpt++;
        }


        g.setColor(couleurInitiale);
    }



    @Override
    public void mouseDragged(MouseEvent e)
    {
        System.out.println(e.getX() + " : " + e.getY());


        this.newXCuve = e.getX() - (abs(e.getX() - this.xOrigSourie));
        this.newYCuve = e.getY() - (abs(e.getY() - this.yOrigSourie));

        for (Cuve cuve : this.reseau.getEnsCuves())
        {
            if (this.xOrigSourie > cuve.getPosition().x() && this.xOrigSourie < cuve.getPosition().x() + this.width &&
                this.yOrigSourie > cuve.getPosition().y() && this.yOrigSourie < cuve.getPosition().y() + this.height)
            {
                System.out.println("Cuve ");
                cuve.setPosition(new Position(this.newXCuve, this.newYCuve));
                this.repaint();
            }
        }
    }

    // utilise pour capturer le clique de l'utilisateur
    @Override
    public void mousePressed(MouseEvent e)
    {
        System.out.println("appui sur le clic en " + e.getX() + " : " + e.getY());

        this.xOrigSourie = e.getX();
        this.yOrigSourie = e.getY();
    }



    // normalement inutile mais pas sur
    @Override
    public void mouseReleased(MouseEvent e)
    {
        System.out.println("relachement de la sourie");
    }


    // Inutile pour le moment
    public void mouseMoved  (MouseEvent e)
    {
        //System.out.println(e.getX() + " : " + e.getY());
    }



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
            g.fillOval(xCuve, yCuve, width, height);
            g.setColor(Color.BLACK);
            g.drawOval(xCuve, yCuve, width, height);


            // Dessin des noms des cuves
            switch (cuve.getPosInfo().getValeur())
            {
                case 0 -> g.drawString(""+cuve.getIdentifiant(), xCuve + width + 5, yCuve + height / 2);
                case 1 -> g.drawString(""+cuve.getIdentifiant(), xCuve + width / 2, yCuve + height + 5);
                case 2 -> g.drawString(""+cuve.getIdentifiant(), xCuve - width - 5, yCuve + height / 2);
                case 3 -> g.drawString(""+cuve.getIdentifiant(), xCuve + width / 2, yCuve - height - 5);

                default -> g.drawString(""+cuve.getIdentifiant(), xCuve + width + 5, yCuve + height / 2);
            }
*/