package appli1.ihm.editeur.cuve;

import java.awt.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;

public class EditeurCapacite extends AbstractCellEditor implements TableCellEditor, ChangeListener {

    private TableModel model;

    private int row, col;

    private int val;
    private JSpinner spnCapacite;

    public EditeurCapacite(TableModel model, int row, int col) {

        this.model = model;
        this.row = row;
        this.col = col;

        try {

            this.val = Integer.parseInt(model.getValueAt(row, col).toString());
        } catch (NumberFormatException e) {

            this.val = 200;
        }

        System.out.println("new EditeurCapacite");
        this.spnCapacite = new JSpinner(new SpinnerNumberModel(this.val, 200, 1000, 1));
        this.spnCapacite.addChangeListener(this);
    }

    public Object getCellEditorValue() {

        System.out.println("getCellEditorValue " + this.val);
        return this.val;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        System.out.format("\"%s\"\n", value.toString());

        try {

            this.val = Integer.parseInt(value.toString());
            this.spnCapacite.setValue(this.val);
        } catch (Exception e) {

            this.val = 200;
            this.spnCapacite.setValue(200);
        }

        this.model.setValueAt(this.val, this.row, this.col);

        return this.spnCapacite;
    }

    public void stateChanged(ChangeEvent event) {

        if (event.getSource() == this.spnCapacite) {

            this.val = (Integer) this.spnCapacite.getValue();
            this.model.setValueAt(this.val, this.row, this.col);
        }
    }
}