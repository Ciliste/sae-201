package appli2.ihm;

import javax.swing.*;

import appli2.Controleur;

import java.awt.event.*;
import java.awt.BorderLayout;

public class PanelAction extends JPanel implements ActionListener
{
    private Controleur ctrl;

    private JButton boutonSuivant;
    private JButton boutonPrecedent;

    private JButton boutonAjouter;


    public PanelAction(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setLayout(new BorderLayout());

        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        JPanel panelBouton = new JPanel();
        JPanel panelAutre  = new JPanel();

        this.boutonSuivant   = new JButton("Suivant");
        this.boutonPrecedent = new JButton("Précédent");


        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        panelBouton.add(this.boutonSuivant);
        panelBouton.add(this.boutonPrecedent);

        this.add(panelBouton, BorderLayout.EAST);


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
            
        }
        
        if (e.getSource() == this.boutonPrecedent)
        {
            
        }        
    }


}
