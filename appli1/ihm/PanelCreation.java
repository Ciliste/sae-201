package appli1.ihm;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.*;

import java.util.HashMap;
import java.util.Map;

import appli1.Controleur;
import appli1.ihm.textfield.AbstractInputTextField;
import appli1.ihm.model.CuveModel;
import appli1.ihm.table.CuveTable;
import appli1.ihm.textfield.*;

public class PanelCreation extends JPanel implements ActionListener, FocusListener {
    
    private Controleur ctrl;
    private FrameCreation frame;

    private static final int INDENT = 25;
    private int curseurY;

    private CuveModel modelCuve;
    private JTable tblCuves;

    private JTextField txtNbCuves;
    private String oldNbCuveValue;
    private JTextField txtNbTubes;

    public PanelCreation(Controleur ctrl, FrameCreation frame, String data) {

        this.ctrl = ctrl;
        this.frame = frame;
        this.curseurY = 10;

        this.setLayout(new BorderLayout());

        if (data == null) {

            this.modelCuve = new CuveModel();
        } 
        else {}

        this.tblCuves = new CuveTable(this.modelCuve);

        this.txtNbCuves = new JTextField(5);
        this.txtNbTubes = new JTextField(5);

        this.txtNbCuves.addActionListener(this);
        this.txtNbTubes.addActionListener(this);

        this.txtNbCuves.addFocusListener(this);
        this.txtNbTubes.addFocusListener(this);

        JPanel panelCuve = new JPanel();
        panelCuve.setLayout(new BorderLayout());
        JPanel panelInputCuve = new JPanel();
        panelInputCuve.add(new JLabel("Nombre de cuve : "));
        panelInputCuve.add(this.txtNbCuves);
        panelCuve.add(panelInputCuve, BorderLayout.NORTH);
        panelCuve.add(new JScrollPane(this.tblCuves), BorderLayout.CENTER);
        this.add(panelCuve, BorderLayout.NORTH);

        JPanel panelTube = new JPanel();
        panelTube.setLayout(new BorderLayout());
        JPanel panelInputTube = new JPanel();
        panelInputTube.add(new JLabel("Nombre de tube : "));
        panelInputTube.add(this.txtNbTubes);
        panelTube.add(panelInputTube, BorderLayout.NORTH);
        // panelTube.add(new JScrollPane(new TubeTable(this.modelTube)), BorderLayout.CENTER);
        this.add(panelTube, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == this.txtNbCuves) {

            try {
                
                Object[][] temp = new Object[Integer.parseInt(this.txtNbCuves.getText())][4];
                for (int i = 0; i < temp.length; i++) {

                    temp[i] = new Object[] { (char) ('A' + i), "", "", "" };
                    try {

                        if (this.modelCuve.getValueAt(i, 0) != null) {

                            temp[i][1] = this.modelCuve.getValueAt(i, 1);
                            temp[i][2] = this.modelCuve.getValueAt(i, 2);
                            temp[i][3] = this.modelCuve.getValueAt(i, 3);
                        }
                    } 
                    finally {}
                }

                this.modelCuve = new CuveModel(temp);
                
                this.modelCuve.setEditable(true);
                this.tblCuves.setModel(this.modelCuve);

                this.oldNbCuveValue = this.txtNbCuves.getText();
            } 
            catch (Exception err) {

                err.printStackTrace();
                if (this.oldNbCuveValue != null) {

                    this.txtNbCuves.setText(this.oldNbCuveValue);
                }
                //TODO: Fenêtre d'erreur
            }
        }
    }

    public void focusGained(FocusEvent event) {}

    public void focusLost(FocusEvent event) {

        if (event.getSource() == this.txtNbCuves) {

            this.txtNbCuves.setText(this.oldNbCuveValue);
        }
    }
}