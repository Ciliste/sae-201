package appli1.ihm.table;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import appli1.ihm.model.*;

public class CuveTable extends JTable {

    public CuveTable(AbstractTableModel model) {

        super(model);
        this.setDefaultRenderer(Integer.class, new DefaultTableCellRenderer());
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