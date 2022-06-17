package appli1.ihm;

import javax.management.JMException;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import common.SharedContants;
import launchers.Controleur;
import launchers.Controleur.MethodeSauvegarde;
import metier.Cuve;
import metier.Position;
import metier.Tube;
import metier.reseau.ListeAdjacence;
import metier.reseau.Reseau;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FrameCreation extends JFrame {
    
    private Controleur ctrl;
    private PanelCreation panelCrea;

    private JMenuItem mnuSaveFile;
    private JScrollPane scrollPaneCrea;
    private File fichierCourant;

    private int statutFichier;
    private int statutTravail;

    public FrameCreation(Controleur ctrl) {

        this.ctrl = ctrl;

        this.statutFichier = SharedContants.StatutFichier.RIEN.VAL;
        this.statutTravail = SharedContants.StatutTravail.AUCUN.VAL;

        this.setTitle("Création d'un réseau");
        this.setSize(500, 500);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.scrollPaneCrea = new JScrollPane(new JPanel());
        this.add(this.scrollPaneCrea, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        // Définition du menu déroulant "File" et de son contenu
        JMenu mnuFile = new JMenu( "Fichier" );
        mnuFile.setMnemonic('F');

        JMenuItem mnuNewFile = new JMenuItem("Nouveau");
        mnuNewFile.setIcon(new ImageIcon("icons/new.png" ));
        mnuNewFile.setMnemonic('N');
        mnuNewFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        mnuNewFile.addActionListener(this::nouveau);
        mnuFile.add(mnuNewFile);

        mnuFile.addSeparator();

        JMenuItem mnuOpenFile = new JMenuItem("Ouvrir");
        mnuOpenFile.setIcon(new ImageIcon("icons/open.png"));
        mnuOpenFile.setMnemonic('O');
        mnuOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        mnuOpenFile.addActionListener(this::ouvrir);
        mnuFile.add(mnuOpenFile);

        this.mnuSaveFile = new JMenuItem("Sauvegarder");
        this.mnuSaveFile.setIcon(new ImageIcon("icons/save.png"));
        this.mnuSaveFile.setMnemonic('S');
        this.mnuSaveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        this.mnuSaveFile.addActionListener(this::sauvegarder);
        mnuFile.add(this.mnuSaveFile);
        this.mnuSaveFile.setEnabled(false);

        JMenu mnuSaveAs = new JMenu( "Sauvegarder sous ..." );
        mnuSaveAs.setIcon(new ImageIcon("icons/save_as.png"));
        mnuSaveAs.setMnemonic('A');

        for (MethodeSauvegarde methode : MethodeSauvegarde.values()) {

            JMenuItem mnuSaveAsItem = new JMenuItem(methode.getNom());
            mnuSaveAsItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    FrameCreation.this.sauvegarderSous(methode.getClasseSauvegarde());
                }
            });
            mnuSaveAs.add(mnuSaveAsItem);
        }

        mnuFile.add(mnuSaveAs);
        
        JMenu mnuTools = new JMenu("Outils");
        mnuTools.setMnemonic('O');
        
        JMenuItem mnuRandom = new JMenuItem("Générer un réseau aléatoire");
        mnuRandom.setMnemonic('R');
        mnuRandom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        mnuRandom.addActionListener(this::random);
        mnuTools.add(mnuRandom);

        menuBar.add(mnuFile);
        menuBar.add(mnuTools);

        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }

    public void nouveau(ActionEvent event) {

        System.out.println("Nouveau");
        
        if (this.statutTravail == SharedContants.StatutTravail.AUCUN.VAL) {

            this.statutFichier = SharedContants.StatutFichier.NOUVEAU.VAL;
            this.statutTravail = SharedContants.StatutTravail.TRAVAIL.VAL;

            this.mnuSaveFile.setEnabled(false);

            this.panelCrea = new PanelCreation(this.ctrl, this, null);
            this.scrollPaneCrea.setViewportView(this.panelCrea);

            this.repaint();
            this.setTitle("Nouveau fichier");
        }
        else {

            String lesTextes[]={ "Sauvegarder", "Ne pas sauvegarder", "Annuler" }; // indice du bouton qui a été // cliqué ou CLOSED_OPTION 

            int retour = JOptionPane.showOptionDialog(this, "Voulez-vous quitter sans sauvegarder ?\nVos changements vont être perdus...", "", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(), lesTextes, lesTextes[0]); 
            
            switch (retour) {

                case 0 : this.sauvegarder(event);
                case 1 : {

                    this.statutTravail = SharedContants.StatutTravail.AUCUN.VAL;

                    this.nouveau(event);
                }
            }
        }
        
        this.repaint();
        this.pack();
    }

    public void ouvrir(ActionEvent event) {

        if (this.statutTravail == SharedContants.StatutTravail.AUCUN.VAL) {

            JFileChooser choose = new JFileChooser(
                FileSystemView
                .getFileSystemView()
                .getHomeDirectory()
            );

            choose.setFileFilter(SharedContants.FiltresFichier.FILTRE_FICHIER_RESEAU.filtre());

            choose.addChoosableFileFilter(SharedContants.FiltresFichier.FILTRE_FICHIER_DATA.filtre());

            int res = choose.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                
                this.fichierCourant = choose.getSelectedFile();
                try {

                    this.statutFichier = SharedContants.StatutFichier.OUVERT.VAL;
                    this.statutTravail = SharedContants.StatutTravail.AUCUN.VAL;

                    this.mnuSaveFile.setEnabled(true);

                    this.panelCrea = new PanelCreation(this.ctrl, this, this.ctrl.ouvrir(this.fichierCourant));
                    System.out.println("Lecture du fichier " + this.fichierCourant.getAbsolutePath());
                    this.scrollPaneCrea.setViewportView(this.panelCrea);

                    this.setTitle(this.fichierCourant.getName());
                }
                catch (Exception err) {SharedContants.showError(this, err);}
            }
        }
        else {

            String btns[] = { "Sauvegarder", "Ne pas sauvegarder", "Annuler" };

            int retour = JOptionPane.showOptionDialog(this, "Voulez-vous quitter sans sauvegarder ?\nVos changements vont être perdus...", "", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(), btns, btns[0]); 
            
            switch (retour) {

                case 0 : this.sauvegarder(event);;
                case 1 : {

                    this.statutTravail = SharedContants.StatutTravail.AUCUN.VAL;

                    this.ouvrir(event);
                }
            }
        }
        
        this.repaint();
        this.pack();
    }

    public void sauvegarder(ActionEvent event) {

        if (this.statutTravail == SharedContants.StatutTravail.AUCUN.VAL) {

            System.out.println(":C");
            return;
        }

        if (this.statutTravail == SharedContants.StatutTravail.TRAVAIL.VAL) {
            
            System.out.println("C:");
            this.statutTravail = SharedContants.StatutTravail.AUCUN.VAL;
            try {

                this.ctrl.sauvegarder(this.fichierCourant, this.panelCrea.getCuves(), this.panelCrea.getTubes());
            }
            catch (Exception err) {

                this.statutTravail = SharedContants.StatutTravail.TRAVAIL.VAL;
                SharedContants.showError(this, err);
            }
        }
    }

    public void sauvegarderSous(Class<? extends Reseau> classe) {

        if (this.statutFichier == SharedContants.StatutFichier.RIEN.VAL) {

            return;
        }

        JFileChooser choose = new JFileChooser(
            FileSystemView
            .getFileSystemView()
            .getHomeDirectory()
        );

        choose.setFileFilter(SharedContants.FiltresFichier.FILTRE_FICHIER_RESEAU.filtre());

        choose.addChoosableFileFilter(SharedContants.FiltresFichier.FILTRE_FICHIER_DATA.filtre());

        int res = choose.showSaveDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            
            if (this.statutFichier == SharedContants.StatutFichier.NOUVEAU.VAL) {

                this.fichierCourant = choose.getSelectedFile(); 
                this.statutFichier = SharedContants.StatutFichier.OUVERT.VAL;
            }
                
            this.ctrl.sauvegarderSous(choose.getSelectedFile(), classe, this.panelCrea.getCuves(), this.panelCrea.getTubes());
        }
    }

    public void fireCellEdited() {

        this.statutTravail = SharedContants.StatutTravail.TRAVAIL.VAL;
    }

    public void random(ActionEvent event) {

        this.nouveau(event);
        
        Reseau reseau = new ListeAdjacence();
        
        Cuve.resetCompteur();
        for (int cpt = 0; cpt < (int) (Math.random() * 27); cpt++) {

            int capa = Cuve.CAPACITE_MIN + (int) (Math.random() * (Cuve.CAPACITE_MAX - Cuve.CAPACITE_MIN));
            reseau.ajouterCuve(Cuve.creerCuve(
                capa,
                (Double) (Math.random() * capa),
                new Position(100 + (int) (Math.random() * (1000-100)), 100 + (int) (Math.random() * (600-100))),
                (int) (Math.random() * 4)
            ));
        }

        if (reseau.getEnsCuves().size() < 1) {this.random(event);}

        for (int cpt = 0; cpt < reseau.getEnsCuves().size() * (reseau.getEnsCuves().size() - 1) / 2; cpt++) {

            Cuve c1, c2;
            c1 = reseau.getEnsCuves().get((int) (Math.random() * reseau.getEnsCuves().size()));
            c2 = reseau.getEnsCuves().get((int) (Math.random() * reseau.getEnsCuves().size()));
            
            boolean bOk = true;
            for (Tube tube : reseau.getAdjacences().get(c1)) {

                if (tube.contains(c2)) {

                    bOk = false;
                    break;
                }
            }

            if (!bOk) {

                cpt--;
            }
            else {

                reseau.ajouterTube(Tube.creerTube(c1, c2, Tube.SECTION_MIN + (int) (Math.random() * (Tube.SECTION_MAX - Tube.SECTION_MIN))));
                this.panelCrea = new PanelCreation(this.ctrl, this, reseau);
                this.scrollPaneCrea.setViewportView(this.panelCrea);
            }
        }
    }
}