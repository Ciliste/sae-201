package appli1.ihm.editeur.tube;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.JComboBox;

import java.awt.Component;
import java.awt.event.*;
import java.util.List;

public class EditeurCuve extends AbstractCellEditor implements TableCellEditor, ItemListener, ActionListener {

    private TableModel model;

    private int row, col;
    
    private Character val;
    private JComboBox<Character> ddlstCuve;

    public EditeurCuve(TableModel model, int row, int col, List<Character> choix) {

        this.model = model;
        this.row = row;
        this.col = col;

        if (this.model.getValueAt(row, col) instanceof Character) {

            this.val = (Character) this.model.getValueAt(row, col);
        } 
        else {

            this.val = ' ';
        }

        Character[] ids = new Character[choix.size()];
        for (int i = 0; i < choix.size(); i++) {
            ids[i] = choix.get(i);
        }

        this.ddlstCuve = new JComboBox<Character>(ids);
        this.ddlstCuve.addItemListener(this);
        this.ddlstCuve.addActionListener(this);
    }

    public Object getCellEditorValue() {

        return this.val;
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        try {

            this.val = (Character) value;
        } catch (Exception e) {

            this.val = ' ';
        }

        this.ddlstCuve.setSelectedItem(value);

        this.model.setValueAt(this.val, this.row, this.col);

        return this.ddlstCuve;
    }

    public void actionPerformed(ActionEvent event) {

        this.val = (Character) this.ddlstCuve.getSelectedItem();
        this.model.setValueAt(this.val, this.row, this.col);
    }

    public void itemStateChanged(ItemEvent event) {

        if (event.getSource() == this.ddlstCuve) {

            this.val = (Character) this.ddlstCuve.getSelectedItem();
            this.model.setValueAt(this.val, this.row, this.col);
        }
    }
}