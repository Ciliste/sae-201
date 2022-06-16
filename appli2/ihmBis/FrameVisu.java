package appli2.ihmBis;

import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileSystemView;

import common.SharedContants;
import launchers.Controleur;
import metier.Cuve;
import metier.reseau.Reseau;

public class FrameVisu extends JFrame {

    private Controleur ctrl;

    private JMenuItem mnuSaveFile;

    private int statutTravail;
    private File fichierCourant;

    public FrameVisu(Controleur ctrl) {

        this.ctrl = ctrl;

        this.statutTravail = SharedContants.StatutTravail.AUCUN.VAL;

        this.setTitle("Visualisation d'un réseau");
        this.setSize(500, 500);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu mnuFile = new JMenu( "Fichier" );
        mnuFile.setMnemonic('F');

        JMenuItem mnuOpenFile = new JMenuItem("Ouvrir");
        mnuOpenFile.setIcon(new ImageIcon("icons/open.png"));
        mnuOpenFile.setMnemonic('O');
        mnuOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        mnuOpenFile.addActionListener(this::ouvrir);
        mnuFile.add(mnuOpenFile);

        mnuFile.addSeparator();

        this.mnuSaveFile = new JMenuItem("Sauvegarder");
        this.mnuSaveFile.setIcon(new ImageIcon("icons/open.png"));
        this.mnuSaveFile.setMnemonic('O');
        this.mnuSaveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        this.mnuSaveFile.addActionListener(this::sauvegarder);
        mnuFile.add(this.mnuSaveFile);

        menuBar.add(mnuFile);

        JLabel lbl = new JLabel("Sheeeesh");
        lbl.setBounds(0, 0, 100, 100);
        this.add(lbl);

        this.setJMenuBar(menuBar);

        this.setVisible(true);

        this.repaint();
    }

    public void ouvrir(ActionEvent event) {

        System.out.println("Shesh");
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

                    this.statutTravail = SharedContants.StatutTravail.AUCUN.VAL;

                    this.mnuSaveFile.setEnabled(true);

                    this.drawReseau(this.ctrl.ouvrir(this.fichierCourant));

                    this.setTitle(this.fichierCourant.getName());
                }
                catch (Exception err) {err.printStackTrace();}
            }
        }
        else {


        }
    }

    public void sauvegarder(ActionEvent event) {


    }

    private void drawReseau(Reseau reseau) {

        System.out.println(reseau.serialize());

        this.getContentPane().removeAll();

        Integer minX = null;
        Integer maxX = null;
        Integer minY = null;
        Integer maxY = null;
        for (Cuve cuve : reseau.getEnsCuves()) {

            if (minX == null || cuve.getPosition().x() < minX) {
                minX = cuve.getPosition().x();
            }
            if (maxX == null || cuve.getPosition().x() > maxX) {
                maxX = cuve.getPosition().x();
            }
            if (minY == null || cuve.getPosition().y() < minY) {
                minY = cuve.getPosition().y();
            }
            if (maxY == null || cuve.getPosition().y() > maxY) {
                maxY = cuve.getPosition().y();
            }
        }

        for (Cuve cuve : reseau.getEnsCuves()) {

            this.add(new CuvePanel(this, cuve));
        }

        this.repaint();
    }
}