package appli2.ihm;

import javax.swing.*;

import appli2.Controleur;
import metier.reseau.Reseau;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Font;

public class PanelAction extends JPanel implements ActionListener
{
    private Controleur ctrl;
    private Reseau reseau;

    private JButton[] ensBtn;

    private JButton btnAjouter;

    private boolean autoOn = false;


    public PanelAction(Controleur ctrl, Reseau reseau)
    {
        this.ctrl = ctrl;

        this.reseau = reseau;

        this.setLayout(new BorderLayout());

        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        /* Création des panels */
        JPanel panelBouton = new JPanel();

        /* Création des btns */
        this.ensBtn = new JButton[7];
        this.ensBtn[0] = new JButton("⏮");
        this.ensBtn[1] = new JButton("⏪ 10");
        this.ensBtn[2] = new JButton("◀");
        this.ensBtn[3] = new JButton("⏯");
        this.ensBtn[4] = new JButton("▶");
        this.ensBtn[5] = new JButton("10 ⏩");
        this.ensBtn[6] = new JButton("⏭");


        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        /* Ajout des btns au panel btn*/
        for (int i = 0; i < this.ensBtn.length; i++)
            panelBouton.add(this.ensBtn[i]);

        /* Ajout des panels au panel principal */
        this.add(panelBouton, BorderLayout.WEST);


        /*---------------------------*/
        /* Activation des composants */
        /*---------------------------*/
        for (int i = 0; i < this.ensBtn.length; i++)
            this.ensBtn[i].addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        // reveir au début
        if (e.getSource() == this.ensBtn[0])
        {
            for (int i = 0; i < 200; i++)
            {
                this.reseau.retour();
                this.ctrl.maj();
            }

            
        }

        // reculer de 10 étapes
        if (e.getSource() == this.ensBtn[1])
        {
            for (int i = 0; i < 10; i++)
            {
                this.reseau.retour();
            }

            this.ctrl.maj();
        }

        // reculer d'une étape
        if (e.getSource() == this.ensBtn[2])
        {
            //this.reseau.retour();
            this.ctrl.maj();
        }

        // mode automatique
        if (e.getSource() == this.ensBtn[3])
        {
            this.autoOn = !this.autoOn;
            // à modifier pour optenir le nombre d'étapes que doit faire le réseau pour obtenir un équilibre
            
            new Thread(new Runnable() {
                
                public void run() {

                    while (autoOn)
                    {
                        PanelAction.this.reseau.update();
                        ctrl.maj();

                        try{ Thread.sleep(100); }
                        catch (InterruptedException e) { System.out.println("La pause n'a pas fonctionné"); e.printStackTrace(); }
                    }
                }
            }).start();
        }

        // avancer de 1 étape
        if (e.getSource() == this.ensBtn[4])
        {
            this.reseau.update();
            this.ctrl.maj();
        }

        // avancer de 10 étapes
        if (e.getSource() == this.ensBtn[5])
        {
            for (int i = 0; i < 10; i++)
            {
                this.reseau.update();
                this.ctrl.maj();
            }
        }

        // aller à la fin
        if (e.getSource() == this.ensBtn[6])
        {
            // à modifier pour optenir le nombre d'étapes que doit faire le réseau pour obtenir un équilibre
            for (int i = 0; i < 200; i++)
            {
                this.reseau.update();
            }

            this.ctrl.maj();
        }
    }
}
