package appli1.ihm;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import appli1.Controleur;
import appli1.Controleur.MethodeSauvegarde;
import metier.reseau.Reseau;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FrameCreation extends JFrame {
    
    private static final int RIEN    = 0;
    private static final int NOUVEAU = 1;
    private static final int OUVERT  = 2;

    private static final int AUCUN   = 0;
    private static final int TRAVAIL = 1;
    
    private Controleur ctrl;
    private PanelCreation panelCrea;

    private JMenuItem mnuSaveFile;
    private JScrollPane scrollPaneCrea;
    private File fichierCourant;

    private int statutFichier;
    private int statutTravail;

    public FrameCreation(Controleur ctrl) {

        this.ctrl = ctrl;

        this.statutFichier = FrameCreation.RIEN;
        this.statutTravail = FrameCreation.AUCUN;

        this.setTitle("Création d'un réseau");
        this.setSize(500, 500);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

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
        
        menuBar.add(mnuFile);
        
        this.setJMenuBar(menuBar);

        this.scrollPaneCrea = new JScrollPane(new JPanel());
        this.add(this.scrollPaneCrea, BorderLayout.CENTER);

        this.setVisible(true);

        this.repaint();
        this.pack();
    }

    public void nouveau(ActionEvent event) {

        System.out.println("Nouveau");
        
        if (this.statutTravail == FrameCreation.AUCUN) {

            this.statutFichier = FrameCreation.NOUVEAU;
            this.statutTravail = FrameCreation.TRAVAIL;

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

                    this.statutTravail = FrameCreation.AUCUN;

                    this.nouveau(event);
                }
            }
        }
        
        this.repaint();
        this.pack();
    }

    public void ouvrir(ActionEvent event) {

        if (this.statutTravail == FrameCreation.AUCUN) {

            JFileChooser choose = new JFileChooser(
                FileSystemView
                .getFileSystemView()
                .getHomeDirectory()
            );

            choose.setFileFilter(new FileFilter() {
               
                public String getDescription() {

                    return "Réseaux (*.data)";
                }

                public boolean accept(File f) {

                    if (f.isDirectory()) {

                        return true;
                    } 
                    else {

                        try {

                            return Files.getAttribute(Path.of(""), "").equals("");
                        }
                        catch (Exception err) {return false;}
                    }
                }
            });

            choose.addChoosableFileFilter(new FileFilter() {
                
                public String getDescription() {

                    return "Data Files (*.data)";
                }

                public boolean accept(File f) {

                    if (f.isDirectory()) {
                        return true;
                    } 
                    else {
                        String filename = f.getName().toLowerCase();
                        return filename.endsWith(".data");
                    }
                }
            });

            int res = choose.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                
                this.fichierCourant = choose.getSelectedFile();
                try {

                    this.statutFichier = FrameCreation.OUVERT;
                    this.statutTravail = FrameCreation.TRAVAIL;

                    this.mnuSaveFile.setEnabled(true);

                    this.panelCrea = new PanelCreation(this.ctrl, this, this.ctrl.ouvrir(this.fichierCourant));
                    System.out.println("Lecture du fichier " + this.fichierCourant.getAbsolutePath());
                    this.scrollPaneCrea.setViewportView(this.panelCrea);

                    this.setTitle(this.fichierCourant.getName());
                }
                catch (Exception err) {err.printStackTrace();}
            }
        }
        else {

            String btns[] = { "Sauvegarder", "Ne pas sauvegarder", "Annuler" };

            int retour = JOptionPane.showOptionDialog(this, "Voulez-vous quitter sans sauvegarder ?\nVos changements vont être perdus...", "", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, new ImageIcon(), btns, btns[0]); 
            
            switch (retour) {

                case 0 : this.sauvegarder(event);;
                case 1 : {

                    this.statutTravail = FrameCreation.AUCUN;

                    this.ouvrir(event);
                }
            }
        }
        
        this.repaint();
        this.pack();
    }

    public void sauvegarder(ActionEvent event) {

        if (this.statutTravail == FrameCreation.AUCUN) {

            return;
        }

        if (this.statutTravail == FrameCreation.TRAVAIL) {
            
            this.statutTravail = FrameCreation.AUCUN;
            this.ctrl.sauvegarder(this.fichierCourant, this.panelCrea.getCuves(), this.panelCrea.getTubes());
        }
    }

    public void sauvegarderSous(Class<? extends Reseau> classe) {

        if (this.statutFichier == FrameCreation.RIEN) {

            return;
        }

        JFileChooser choose = new JFileChooser(
            FileSystemView
            .getFileSystemView()
            .getHomeDirectory()
        );

        choose.setFileFilter(new FileFilter() {
               
            public String getDescription() {

                return "Réseaux (*.data)";
            }

            public boolean accept(File f) {

                if (f.isDirectory()) {

                    return true;
                } 
                else {

                    try {

                        return Files.getAttribute(Path.of(""), "").equals("");
                    }
                    catch (Exception err) {return false;}
                }
            }
        });

        choose.addChoosableFileFilter(new FileFilter() {
            
            public String getDescription() {

                return "Data Files (*.data)";
            }

            public boolean accept(File f) {

                if (f.isDirectory()) {
                    return true;
                } 
                else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".data");
                }
            }
        });

        int res = choose.showSaveDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            
            if (this.statutFichier == FrameCreation.NOUVEAU) {

                this.fichierCourant = choose.getSelectedFile(); 
                this.statutFichier = FrameCreation.OUVERT;
            }
                
            this.ctrl.sauvegarderSous(choose.getSelectedFile(), classe, this.panelCrea.getCuves(), this.panelCrea.getTubes());
        }
    }
}