package appli1.ihm.editeur.cuve;

import java.awt.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;

public class EditeurContenu extends AbstractCellEditor implements TableCellEditor, ChangeListener {

    private TableModel model;

    private int row, col;

    private double val;
    private JSpinner spnCapacite;

    public EditeurContenu(TableModel model, int row, int col, int max) {

        this.model = model;
        this.row = row;
        this.col = col;

        try {

            this.val = Double.parseDouble(model.getValueAt(row, col).toString());
        } catch (NumberFormatException e) {

            this.val = 0;
        }

        System.out.println("new EditeurContenu");
        this.spnCapacite = new JSpinner(new SpinnerNumberModel(this.val, 0, max, 1));
        this.spnCapacite.addChangeListener(this);
    }

    public Object getCellEditorValue() {

        return (double) this.val;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        try {

            this.val = Integer.parseInt(value.toString());
        } catch (Exception e) {

            this.val = 200;
        }

        this.spnCapacite.setValue(this.val);

        this.model.setValueAt(this.val, this.row, this.col);

        return this.spnCapacite;
    }

    public void stateChanged(ChangeEvent event) {

        if (event.getSource() == this.spnCapacite) {

            this.val = (Double) this.spnCapacite.getValue();
            this.model.setValueAt(this.val, this.row, this.col);
        }
    }
}