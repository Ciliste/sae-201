package appli1.ihm;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;

import java.util.List;

import appli1.Controleur;
import appli1.ihm.model.CuveModel;
import appli1.ihm.model.TubeModel;
import appli1.ihm.table.CuveTable;
import appli1.ihm.table.TubeTable;
import metier.Cuve;
import metier.reseau.Reseau;

public class PanelCreation extends JPanel implements ActionListener, FocusListener {
    
    private Controleur ctrl;
    private FrameCreation frame;

    private CuveModel modelCuve;
    private JTable tblCuves;
    private TubeModel modelTube;
    private JTable tblTubes;

    private JTextField txtNbCuves;
    private String oldNbCuveValue;
    private JTextField txtNbTubes;
    private JPanel panelTube;
    private String oldNbTubeValue;

    public PanelCreation(Controleur ctrl, FrameCreation frame, Reseau data) {

        this.ctrl = ctrl;
        this.frame = frame;

        this.setLayout(new BorderLayout());

        if (data == null) {

            this.modelCuve = new CuveModel();
            this.modelTube = new TubeModel();
        } 
        else {}

        this.tblCuves = new CuveTable(this.modelCuve);
        this.tblTubes = new TubeTable(this.modelTube, this);

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
        this.add(panelCuve, BorderLayout.WEST);

        this.panelTube = new JPanel();
        this.panelTube.setLayout(new BorderLayout());
        JPanel panelInputTube = new JPanel();
        panelInputTube.add(new JLabel("Nombre de tube : "));
        panelInputTube.add(this.txtNbTubes);
        this.panelTube.add(panelInputTube, BorderLayout.NORTH);
        this.panelTube.add(new JScrollPane(this.tblTubes), BorderLayout.CENTER);
        this.add(this.panelTube, BorderLayout.EAST);

        if (data == null) {

            this.txtNbTubes.setEnabled(false);
        }
    }

    public List<Character> getIds() {

        return this.modelCuve.getIds();
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == this.txtNbCuves) {

            try {
                
                Object[][] temp = new Object[Integer.parseInt(this.txtNbCuves.getText())][4];
                for (int i = 0; i < temp.length; i++) {

                    temp[i] = new Object[] { (char) ('A' + i), Cuve.CAPACITE_MIN, 0, "HAUT" };
                    try {

                        if (this.modelCuve.getValueAt(i, 0) != null) {

                            temp[i][1] = this.modelCuve.getValueAt(i, 1);
                            temp[i][2] = this.modelCuve.getValueAt(i, 2);
                            temp[i][3] = this.modelCuve.getValueAt(i, 3);
                        }
                    } 
                    catch (Exception err) {}
                }

                this.modelCuve = new CuveModel(temp);
                
                this.modelCuve.setEditable(true);
                this.tblCuves.setModel(this.modelCuve);

                if (Integer.parseInt(this.txtNbCuves.getText()) > 1) {

                    this.txtNbTubes.setEnabled(true);
                } 
                else {

                    this.txtNbTubes.setEnabled(false);
                }

                this.oldNbCuveValue = this.txtNbCuves.getText();
            } 
            catch (Exception err) {

                err.printStackTrace();
                if (this.oldNbCuveValue != null) {

                    this.txtNbCuves.setText(this.oldNbCuveValue);
                }
                else {

                    this.txtNbCuves.setText("");
                }
                //TODO: Fenêtre d'erreur
            }
        }
        if (event.getSource() == this.txtNbTubes) {

            try {
                
                int nbTube = Integer.parseInt(this.txtNbTubes.getText());

                if (nbTube > (this.tblCuves.getRowCount() * (this.tblCuves.getRowCount() - 1)) / 2) {

                    nbTube = this.tblCuves.getRowCount() * (this.tblCuves.getRowCount() - 1) / 2;
                    this.txtNbTubes.setText(Integer.toString(nbTube));
                    this.modelTube.setEditable(true);
                    // TODO: Fenêtre d'erreur
                }

                Object[][] temp = new Object[nbTube][4];
                for (int i = 0; i < temp.length; i++) {

                    temp[i] = new Object[] { "", "", 2 };
                    try {

                        if (this.modelTube.getValueAt(i, 0) != null) {

                            temp[i][0] = this.modelTube.getValueAt(i, 0);
                            temp[i][1] = this.modelTube.getValueAt(i, 1);
                            temp[i][2] = this.modelTube.getValueAt(i, 2);
                        }
                    } 
                    finally {}
                }

                this.modelTube = new TubeModel(temp);
                
                this.modelTube.setEditable(true);
                this.tblTubes.setModel(this.modelTube);

                this.oldNbTubeValue = this.txtNbTubes.getText();
            } 
            catch (Exception err) {

                if (this.oldNbTubeValue != null) {

                    this.txtNbTubes.setText(this.oldNbTubeValue);
                }
                else {

                    this.txtNbCuves.setText("");
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
        if (event.getSource() == this.txtNbTubes) {

            this.txtNbTubes.setText(this.oldNbTubeValue);
        }
    }

    public Object[][] getCuves() {

        return this.modelCuve.getDonnees();
    }

    public Object[][] getTubes() {

        return this.modelTube.getDonnees();
    }
}