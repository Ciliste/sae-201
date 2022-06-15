package appli2.ihm;

import javax.swing.*;

import appli2.Controleur;

import java.awt.event.*;

public class PanelAction extends JPanel implements ActionListener
{
    private Controleur ctrl;

    private JButton boutonSuivant;
    private JButton boutonPrecedent;

    private JButton boutonAjouter;


    public PanelAction(Controleur ctrl)
    {
        this.ctrl = ctrl;

        // Création des composants
        this.boutonSuivant   = new JButton("Suivant");
        this.boutonPrecedent = new JButton("Précédent");


        // Ajout des composants
        this.add(this.boutonSuivant);
        this.add(this.boutonPrecedent);

        // Activation des composants
        this.boutonSuivant.addActionListener(this);
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
