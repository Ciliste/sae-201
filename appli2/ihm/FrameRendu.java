package appli2.ihm;

import javax.swing.*;
import java.awt.event.*;

import appli2.Controleur;
import metier.reseau.Reseau;

import java.awt.Dimension;

 
public class FrameRendu extends JFrame implements ActionListener
{
    private PanelRendu panelRendu;

    private JMenuItem  menuiFichierOuvrir;
	private JMenuItem  menuiFichierQuitter;

    private Reseau reseau;


    public FrameRendu(Controleur ctrl, Reseau reseau) 
    {
        this.setTitle("Frame rendu");
        this.setLocation(0, 0);
        this.setMinimumSize(new Dimension(1000, 1000));



        /*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
		/* Barre de Menu */
		JMenuBar menuBar  = new JMenuBar();

		JMenu menuFichier = new JMenu("Fichier");
		menuFichier.setMnemonic('F');

		this.menuiFichierOuvrir  = new JMenuItem("Ouvrir");
		this.menuiFichierQuitter = new JMenuItem("Quitter");

		this.menuiFichierOuvrir .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		this.menuiFichierQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        

        /* Panel de rendu */
        this.panelRendu = new PanelRendu(ctrl, reseau);


        /*----------------------*/
		/* Ajout des composants */
		/*----------------------*/
        /* Dans le menu Fichier  */
        menuFichier.add(this.menuiFichierOuvrir);
		menuFichier.add(this.menuiFichierQuitter);

        /* Ajout du menu Fichier à la barre de menu */
        menuBar.add(menuFichier);

        /* Ajout de la barre de menu à la fenêtre */
        this.setJMenuBar(menuBar);

        /* Panel de rendu */
        this.add(this.panelRendu);



        /*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiFichierOuvrir .addActionListener(this);
		this.menuiFichierQuitter.addActionListener(this);


        /*--------------*/
        /* Finalisation */
        /*--------------*/
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }



	public void actionPerformed ( ActionEvent e )
	{
        String nomFichier = "";

		// Création et ouverture d'un JFileChooser pour affecter
		if ( e.getSource() == this.menuiFichierOuvrir )
		{
			JFileChooser fc = new JFileChooser("./");

			fc.setMultiSelectionEnabled(false);

			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION)
				nomFichier = fc.getSelectedFile().getAbsolutePath();

            //nomFichier
            
            System.out.println(nomFichier.length());
		}

		// Fermeture de l'application
		if ( e.getSource() == this.menuiFichierQuitter )
		{
			this.dispose();
		}
    }
}