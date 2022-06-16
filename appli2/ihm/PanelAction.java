package appli2.ihm;

import javax.swing.*;

import appli2.Controleur;
import metier.reseau.Reseau;

import java.awt.event.*;
import java.awt.BorderLayout;

public class PanelAction extends JPanel implements ActionListener
{
    private Controleur ctrl;
    private Reseau reseau;

    private JButton boutonPrecedent;
    private JButton boutonSuivant;

    private JButton boutonAjouter;


    public PanelAction(Controleur ctrl, Reseau reseau)
    {
        this.ctrl = ctrl;

        this.setLayout(new BorderLayout());

        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        /* Création des panels */
        JPanel panelBouton = new JPanel();

        /* Création des boutons */
        this.boutonPrecedent = new JButton("◀");
        this.boutonSuivant   = new JButton("▶");


        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        /* Ajout des boutons au panel bouton*/
        panelBouton.add(this.boutonPrecedent);
        panelBouton.add(this.boutonSuivant);

        /* Ajout des panels au panel principal */
        this.add(panelBouton, BorderLayout.WEST);


        /*---------------------------*/
        /* Activation des composants */
        /*---------------------------*/
        this.boutonSuivant  .addActionListener(this);
        this.boutonPrecedent.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.boutonSuivant)
        {
            //this.reseau.update();
            this.ctrl.maj();
        }
        
        if (e.getSource() == this.boutonPrecedent)
        {
            
        }        
    }


}
