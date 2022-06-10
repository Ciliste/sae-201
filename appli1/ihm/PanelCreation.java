package appli1.ihm;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.*;

import java.util.HashMap;
import java.util.Map;

import appli1.Controleur;
import appli1.ihm.textfield.AbstractInputTextField;
import appli1.ihm.textfield.*;

public class PanelCreation extends JPanel implements ActionListener {

    private Controleur ctrl;
    private FrameCreation frame;

    private static final int INDENT = 25;
    private int curseurY;

    private Map<AbstractInputTextField, Integer> mapInput;

    public PanelCreation(Controleur ctrl, FrameCreation frame) {

        this.ctrl = ctrl;
        this.frame = frame;
        this.curseurY = 10;

        this.setLayout(null);

        this.mapInput = new HashMap<AbstractInputTextField, Integer>();

        this.addToPane(new JLabel("Nombre de cuves : "));

        CuveCapaciteInput nbCuveInput = new CuveCapaciteInput();
        this.addToPane(nbCuveInput);
        this.mapInput.put(nbCuveInput, 0);

        for (JTextField txtfld : mapInput.keySet()) {

            txtfld.addActionListener(this);
        }
    }

    public void addToPane(Component comp) {

        comp.setBounds(PanelCreation.INDENT, this.curseurY, comp.getPreferredSize().width,
                comp.getPreferredSize().height);
        this.add(comp);
        this.curseurY += comp.getPreferredSize().height + 5;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof AbstractInputTextField) {

            AbstractInputTextField txtfld = (AbstractInputTextField) e.getSource();
            if (this.mapInput.get(e.getSource()) == 0) {

                System.out.println("Nombre de cuves : " + txtfld.getText() + " " + txtfld.valeurEstValide());
            }
        }
    }
}