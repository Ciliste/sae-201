package appli1.ihm.table;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import appli1.ihm.editeur.cuve.EditeurCapacite;
import appli1.ihm.editeur.cuve.EditeurContenu;
import appli1.ihm.editeur.cuve.EditeurPosInfo;
import appli1.ihm.model.*;

public class CuveTable extends JTable {

    public CuveTable(CuveModel model) {

        super(model);
        this.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {

        try {
            
            return switch (column) {

                case 1 -> new EditeurCapacite(this.getModel(), row, column);
                case 2 -> new EditeurContenu(this.getModel(), row, column, (int) this.getModel().getValueAt(row, column - 1));
                case 3 -> new EditeurPosInfo(this.getModel(), row, column);

                default -> super.getCellEditor(row, column);
            };
        } 
        catch (Exception e) {
            
            return null;
        }
        
    }
}