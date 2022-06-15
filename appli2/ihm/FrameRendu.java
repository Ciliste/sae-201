package appli2.ihm;

import javax.swing.JFrame;

import appli2.Controleur;
import metier.reseau.Reseau;

import java.awt.Dimension;


public class FrameRendu extends JFrame
{
    private PanelRendu panel;

    public FrameRendu(Controleur controleur, Reseau reseau) 
    {
        this.setTitle("ton indentation de merde");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.panel = new PanelRendu(controleur, reseau);
        this.add(this.panel);

        this.setMinimumSize(new Dimension(602, 602));

        this.setVisible(true);
    }
}