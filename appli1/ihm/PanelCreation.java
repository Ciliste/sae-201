package appli1.ihm;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import java.awt.BorderLayout;
import java.awt.event.*;

import java.util.List;

import appli1.ihm.model.CuveModel;
import appli1.ihm.model.TubeModel;
import appli1.ihm.table.CuveTable;
import appli1.ihm.table.TubeTable;
import common.SharedContants;
import launchers.Controleur;
import metier.Cuve;
import metier.Tube;
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

        this.txtNbCuves = new JTextField(5);
        this.txtNbTubes = new JTextField(5);

        if (data == null) {

            this.modelCuve = new CuveModel();
            this.modelTube = new TubeModel();
        } 
        else {

            Object[][] cuves = new Object[data.getEnsCuves().size()][6];
            for (Cuve cuve : data.getEnsCuves()) {

                cuves[data.getEnsCuves().indexOf(cuve)][0] = cuve.getIdentifiant();
                cuves[data.getEnsCuves().indexOf(cuve)][1] = cuve.getCapacite();
                cuves[data.getEnsCuves().indexOf(cuve)][2] = cuve.getContenu();
                cuves[data.getEnsCuves().indexOf(cuve)][3] = cuve.getPosition().x();
                cuves[data.getEnsCuves().indexOf(cuve)][4] = cuve.getPosition().y();
                cuves[data.getEnsCuves().indexOf(cuve)][5] = cuve.getPosInfo().getLib();
            }
            this.txtNbCuves.setText(String.valueOf(data.getEnsCuves().size()));

            Object[][] tubes = new Object[data.getEnsTubes().size()][3];
            for (Tube tube : data.getEnsTubes()) {

                tubes[data.getEnsTubes().indexOf(tube)][0] = tube.getCuveA().getIdentifiant();
                tubes[data.getEnsTubes().indexOf(tube)][1] = tube.getCuveB().getIdentifiant();
                tubes[data.getEnsTubes().indexOf(tube)][2] = tube.getSection();

                for (Object obj : tubes[0]) {

                    System.out.println(obj);
                }
            }
            this.txtNbTubes.setText(String.valueOf(data.getEnsTubes().size()));

            this.modelCuve = new CuveModel(cuves);
            this.modelTube = new TubeModel(tubes);

            this.modelCuve.setEditable(true);
            this.modelTube.setEditable(true);
        }

        this.tblCuves = new CuveTable(this.modelCuve);
        this.tblTubes = new TubeTable(this.modelTube, this);

        this.tblCuves.repaint();
        this.tblTubes.repaint();

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
                
                if (Integer.parseInt(this.txtNbCuves.getText()) < 0) {

                    SharedContants.showError(this, new IllegalArgumentException("Veuillez saisir un nombre positif"));
                    return;
                } 
                else if (Integer.parseInt(this.txtNbCuves.getText()) > 26) {

                    SharedContants.showError(this, new IllegalArgumentException("Veuillez saisir un nombre inférieur ou égal à 26"));
                    return;
                }

                Object[][] temp = new Object[Integer.parseInt(this.txtNbCuves.getText())][4];
                for (int i = 0; i < temp.length; i++) {

                    temp[i] = new Object[] { (char) ('A' + i), Cuve.CAPACITE_MIN, 0.0, 0, 0, "HAUT" };
                    try {

                        if (this.modelCuve.getValueAt(i, 0) != null) {

                            temp[i][1] = this.modelCuve.getValueAt(i, 1);
                            temp[i][2] = this.modelCuve.getValueAt(i, 2);
                            temp[i][3] = this.modelCuve.getValueAt(i, 3);
                            temp[i][4] = this.modelCuve.getValueAt(i, 4);
                            temp[i][5] = this.modelCuve.getValueAt(i, 5);
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
                this.frame.fireCellEdited();
            } 
            catch (Exception err) {

                if (this.oldNbCuveValue != null) {

                    this.txtNbCuves.setText(this.oldNbCuveValue);
                }
                else {

                    this.txtNbCuves.setText("");
                }
                SharedContants.showError(this, new IllegalArgumentException("Veuillez saisir un nombre entier"));
            }
        }
        if (event.getSource() == this.txtNbTubes) {

            try {
                
                int nbTube = Integer.parseInt(this.txtNbTubes.getText());

                if (nbTube < 0) {

                    SharedContants.showError(this, new IllegalArgumentException("Veuillez saisir un nombre positif"));
                    return;
                }

                if (nbTube > (this.tblCuves.getRowCount() * (this.tblCuves.getRowCount() - 1)) / 2) {
                    
                    SharedContants.showError(this, new IllegalArgumentException("Il ne peut pas y avoir plus de n(n-2)/2 tubes (" + ((this.tblCuves.getRowCount()*(this.tblCuves.getRowCount()-1))/2) + ")"));
                    return;
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
                this.frame.fireCellEdited();
            } 
            catch (Exception err) {

                if (this.oldNbTubeValue != null) {

                    this.txtNbTubes.setText(this.oldNbTubeValue);
                }
                else {

                    this.txtNbCuves.setText("");
                }
                SharedContants.showError(this, new IllegalArgumentException("Veuillez saisir un nombre entier"));
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