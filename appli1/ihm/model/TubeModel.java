package appli1.ihm.model;

import javax.swing.table.AbstractTableModel;

public class TubeModel extends AbstractTableModel {
    
    private static final String[] COLUMNS = { "Cuve A", "Cuve B", "Section" };

    Object donnees[][];
    String titres[];

    private boolean estEditable;

    public TubeModel() {

        this(
            new Object[][] {}, 
            TubeModel.COLUMNS
        );
    }

    public TubeModel(Object[][] donnees) {

        this(donnees, TubeModel.COLUMNS);

        for (Object[] lig : donnees) {
            
            for (Object obj : lig) {

                System.out.print(obj + " ");
            }
        }
        System.out.println();
    }

    public TubeModel(Object[][] donnees, String[] titres) {

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

            case 0, 1 -> Character.class;
            case 2 -> Integer.class;

            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {

        if (this.estEditable == false) {

            return false;
        }

        return true;
    }

    public Object[][] getDonnees() {

        return this.donnees;
    }
}