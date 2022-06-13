package appli1.ihm.model;

import javax.swing.table.AbstractTableModel;

import metier.Cuve.PositionInfo;

public class CuveModel extends AbstractTableModel {

    private static final String[] COLUMNS = { "ID", "Capacité", "Contenu", "Pos. Info" };

    Object donnees[][];
    String titres[];

    private boolean estEditable;

    public CuveModel() {

        this(
            new Object[][] {}, 
            CuveModel.COLUMNS
        );
    }

    public CuveModel(Object[][] donnees) {

        this(donnees, CuveModel.COLUMNS);
    }

    public CuveModel(Object[][] donnees, String[] titres) {

        this.donnees = donnees;

        this.titres = titres;

        this.estEditable = false;
    }

    public int getColumnCount() {

        return this.titres.length;
    }

    public Object getValueAt(int parm1, int parm2) {

        if (parm1 >= this.donnees.length || donnees[parm1] == null || donnees[parm1][parm2] == null) {

            return null;
        }

        return donnees[parm1][parm2];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {

        this.donnees[row][col] = value;
        this.fireTableCellUpdated(row, col);
    }

    public int getRowCount() {

        return donnees.length;
    }

    public String getColumnName(int col) {

        return this.titres[col];
    }

    public void setEditable(boolean etat) {

        this.estEditable = etat;
    }

    @Override
    public Class<?> getColumnClass(int col) {

        return switch (col) {

            case 0 -> Character.class;
            case 1, 2, 3, 4 -> Integer.class;
            // case 2 -> Double.class;
            case 5 -> PositionInfo.class;

            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {

        if (this.estEditable == false) {

            return false;
        }

        if (col == 0) {

            return false;
        }

        return true;
    }
}