package appli2.ihm;

import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Dimension;


public class Frame extends JFrame
{
    private Panel panel;

    public Frame(Controleur controleur, Reseau reseau) 
    {
        this.setTitle("ton indentation de merde");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.panel = new Panel(controleur, reseau);
        this.add(this.panel);

        this.setMinimumSize(new Dimension(602, 602));

        this.setVisible(true);
    }
}