package appli2.ihm;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.*;

import appli2.Controleur;

public class PanelSaisie extends JPanel implements ActionListener
{
    private Controleur ctrl;

    private JTextField txtNbCuve;
    private JTextField txtNbTube;

    private JTextField[] ensFields;

    private JButton btnValider;


    PanelSaisie(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setLayout(new GridLayout(1, 5));
        
        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        /* Création des panels */
        JPanel panelBouton = new JPanel();
        JPanel panelSaisie  = new JPanel(new GridLayout(1, 5));

        /* Création des boutons */
        this.btnValider = new JButton("Valider");

        /* Création des champs de texte */
        this.txtNbCuve = new JTextField(5);
        this.txtNbTube = new JTextField(5);

        /* Création du tableau de champs de texte */
        this.ensFields = new JTextField[5];

        for (int i = 0; i < 5; i++)
            this.ensFields[i] = new JTextField(5);


        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        /* Ajout des boutons au panel bouton*/
        panelBouton.add(this.btnValider);

        /* Ajout des champs de texte au panel saisie*/
        for (int i = 0; i < 5; i++)
            panelSaisie.add(this.ensFields[i]);

        /* Ajout des champs de texte au panel principal */
        this.add(this.txtNbCuve);
        this.add(this.txtNbTube);

        /* Ajout des panels au panel principal */
        this.add(panelBouton);
        this.add(panelSaisie);

        /*---------------------------*/
        /* Activation des composants */
        /*---------------------------*/
        this.btnValider.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }
}
