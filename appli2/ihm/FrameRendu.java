package appli2.ihm;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

import launchers.Appli2;
import common.SharedContants;
import metier.reseau.ListeAdjacence;
import metier.reseau.Reseau;

import java.awt.Dimension;

 
public class FrameRendu extends JFrame implements ActionListener
{
    private static String stringReseau;

    private PanelRendu panelRendu;

    private JMenuItem  menuiFichierOuvrir;
    private JMenuItem  menuiFichierSave;
	private JMenuItem  menuiFichierQuitter;

    // Test
    private JMenuItem  menuiFichierTest;

    private Reseau reseau;
    private Appli2 ctrl;
    private PanelAction panelAction;


    public FrameRendu(Appli2 ctrl, Reseau reseau) 
    {
        this.setTitle("Frame rendu");
        this.setLocation(0, 0);
        this.setMinimumSize(new Dimension(1000, 1000));

        this.setLayout(new BorderLayout());


        /*-------------------------*/
		/* Création des composants */
		/*-------------------------*/
        this.ctrl = ctrl;

        // Test
        this.reseau = reseau;

        this.panelAction = new PanelAction(this.ctrl, reseau);

		/* Barre de Menu */
		JMenuBar menuBar  = new JMenuBar();

		JMenu menuFichier = new JMenu("Fichier");
		menuFichier.setMnemonic('F');

		this.menuiFichierOuvrir  = new JMenuItem("Ouvrir");
        this.menuiFichierSave    = new JMenuItem("Enregistrer");
		this.menuiFichierQuitter = new JMenuItem("Quitter");

        // Test
        this.menuiFichierTest = new JMenuItem("Test");

		this.menuiFichierOuvrir .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        this.menuiFichierSave   .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		this.menuiFichierQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

        // Test
        this.menuiFichierTest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));


        /*----------------------*/
		/* Ajout des composants */
		/*----------------------*/
        /* Ajout des panels au panel principal */
        this.add(this.panelAction, BorderLayout.NORTH);

        /* Dans le menu Fichier  */
        menuFichier.add(this.menuiFichierOuvrir);
        menuFichier.add(this.menuiFichierSave);
        menuFichier.add(new JSeparator());
		menuFichier.add(this.menuiFichierQuitter);

        // Test
        menuFichier.add(new JSeparator());
        menuFichier.add(new JSeparator());
        menuFichier.add(this.menuiFichierTest);

        /* Ajout du menu Fichier à la barre de menu */
        menuBar.add(menuFichier);

        /* Ajout de la barre de menu à la fenêtre */
        this.setJMenuBar(menuBar);


        /*-------------------------------*/
		/* Activation des composants     */
		/*-------------------------------*/
		this.menuiFichierOuvrir .addActionListener(this);
        this.menuiFichierSave   .addActionListener(this);
		this.menuiFichierQuitter.addActionListener(this);

        // Test
        this.menuiFichierTest.addActionListener(this);


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
            {
                try
                {
			        this.reseau = this.ctrl.ouvrir(fc.getSelectedFile());
                }
                catch (Exception ex){ SharedContants.showError(this, ex); }
            }
		}


		// Fermeture de l'application
		if ( e.getSource() == this.menuiFichierQuitter )
		{
			this.dispose();
		}

        // Test
        if ( e.getSource() == this.menuiFichierTest )
        {
            this.panelRendu = new PanelRendu(this.ctrl, this.reseau);
            this.add(this.panelRendu, BorderLayout.CENTER);

            this.setSize(this.getWidth(), this.getHeight());
        }
    }

    

    public static String lireFichier(String nomFichier)
    {
        String sRet = "";

        File file = new File(nomFichier);

        try
        {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine())
                sRet += sc.nextLine();

            sc.close();

            System.out.println(sRet);
            return sRet;
        }catch(Exception e) { System.out.println("Erreur lors de la lecture du fichier"); e.printStackTrace(); return null; }
    }



    public int getWidthPanelAction () { return this.panelAction.getWidth(); }
    public int getHeightPanelAction() { return this.panelAction.getHeight(); }
}