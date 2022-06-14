package appli1.ihm.editeur.cuve;

import java.lang.InterruptedException;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import javax.swing.text.Position;

import metier.Cuve.PositionInfo;

public class EditeurPosInfo extends AbstractCellEditor implements TableCellEditor, ItemListener {

    private TableModel model;

    private int row, col;

    private PositionInfo val;
    private JComboBox<PositionInfo> ddlstPosInfo;

    public EditeurPosInfo(TableModel model, int row, int col) {

        this.model = model;
        this.row = row;
        this.col = col;

        this.val = PositionInfo.HAUT;

        this.ddlstPosInfo = new JComboBox<PositionInfo>(PositionInfo.values());
        this.ddlstPosInfo.addItemListener(this);
    }

    public Object getCellEditorValue() {

        return this.val.getLib();
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        if (value == null) {

            this.val = PositionInfo.HAUT;
        } else if (value.equals(PositionInfo.HAUT.getLib())) {

            this.ddlstPosInfo.setSelectedItem(PositionInfo.HAUT);
        } else if (value.equals(PositionInfo.BAS.getLib())) {

            this.ddlstPosInfo.setSelectedItem(PositionInfo.BAS);
        } else if (value.equals(PositionInfo.GAUCHE.getLib())) {

            this.ddlstPosInfo.setSelectedItem(PositionInfo.GAUCHE);
        } else if (value.equals(PositionInfo.DROITE.getLib())) {

            this.ddlstPosInfo.setSelectedItem(PositionInfo.DROITE);
        } else {

            this.ddlstPosInfo.setSelectedItem(null);
        }

        return this.ddlstPosInfo;
    }

    public void itemStateChanged(ItemEvent event) {

        if (event.getSource() == this.ddlstPosInfo) {

            this.val = (PositionInfo) this.ddlstPosInfo.getSelectedItem();
            if (this.val != null) this.model.setValueAt(this.val.getLib(), this.row, this.col);
        }
    }
}