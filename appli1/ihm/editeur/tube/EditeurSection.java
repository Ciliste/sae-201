package appli1.ihm.editeur.tube;

import java.awt.Component;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import metier.Tube;

public class EditeurSection extends AbstractCellEditor implements TableCellEditor, ActionListener {

    private TableModel model;
    private JPanel panelBtns;

    private JTextField txtSection;

    private int row, col;
    
    private int val;
    
    public EditeurSection(TableModel model, int row, int col) {

        this.model = model;
        this.row = row;
        this.col = col;
        this.val = Tube.SECTION_MIN;

        this.panelBtns = new JPanel();
        this.panelBtns.setLayout(new BorderLayout());

        JButton btnPlus = new JButton("+");
        btnPlus.addActionListener(this);
        this.panelBtns.add(btnPlus, BorderLayout.EAST);

        JButton btnMoins = new JButton("-");
        btnMoins.addActionListener(this);
        this.panelBtns.add(btnMoins, BorderLayout.WEST);

        this.txtSection = new JTextField(5);
        this.txtSection.addActionListener(this);
        this.txtSection.setText(String.valueOf(this.val));
        this.panelBtns.add(this.txtSection, BorderLayout.CENTER);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        if (value instanceof Integer) {

            this.val = (Integer) value;
        }

        return this.panelBtns;
    }

    @Override
    public Object getCellEditorValue() {

        return this.val;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.txtSection) {

            try {

                this.val = Integer.parseInt(((JTextField) e.getSource()).getText());
                if (this.val < Tube.SECTION_MIN) this.val = Tube.SECTION_MIN;
                if (this.val > Tube.SECTION_MAX) this.val = Tube.SECTION_MAX;
                this.fireEditingStopped();
            }
            catch (Exception err) {

                this.txtSection.setText(String.valueOf(this.val));
            }
        }
        if (e.getSource() instanceof JButton) {

            if (((JButton) e.getSource()).getText().equals("+")) {

                this.val++;
                if (this.val > Tube.SECTION_MAX) this.val = Tube.SECTION_MAX;
            }
            else if (((JButton) e.getSource()).getText().equals("-")) {

                this.val--;
                if (this.val < Tube.SECTION_MIN) this.val = Tube.SECTION_MIN;
            }
            this.txtSection.setText(String.valueOf(this.val));
        }

        this.model.setValueAt(this.val, this.row, this.col);
    }
}