package appli1.ihm.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

import appli1.ihm.model.*;
import appli1.ihm.PanelCreation;
import appli1.ihm.editeur.tube.EditeurCuve;
import appli1.ihm.editeur.tube.EditeurSection;

public class TubeTable extends JTable {

    private PanelCreation panel;
    private TubeModel modelTube;

    public TubeTable(TubeModel modelTube, PanelCreation panel) {

        super(modelTube);
        this.panel = panel;
        this.modelTube = modelTube;

        this.getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {

        try {
            
            switch (column) {

                case 0, 1 -> {
                
                    List<Character> ids = this.panel.getIds();
                    Map<Character, List<Character>> adjs = new HashMap<Character, List<Character>>();

                    System.out.println(ids.toString());

                    for (Character id : ids) {

                        List<Character> adj = new ArrayList<Character>();

                        for (Object[] ligne : this.modelTube.getDonnees()) {

                            if (ligne[0] instanceof Character && ligne[1] instanceof Character) {

                                adj.add((Character) ligne[1]);
                                if (adjs.containsKey(ligne[1])) {

                                    adjs.get(ligne[1]).add((Character) ligne[0]);
                                }
                            }
                        }

                        adjs.put(id, adj);
                    }
                    
                    List<Character> choix = new ArrayList<Character>();
                    if (column == 0) {
                        
                        for (Character id : adjs.keySet()) {
                            
                            for (Character idBis : adjs.keySet()) {

                                if (id.equals(idBis)) continue;
                                if (!adjs.get(id).contains(idBis)) {
                                    
                                    if (!choix.contains(id)) choix.add(id);
                                }
                            }
                        }
                    }
                    else {
                        
                        Character id = (Character) this.getValueAt(row, 0);

                        for (Character idBis : adjs.keySet()) {
                            
                            System.out.format("Id : %c | IdBis : %c\n", id, idBis);
                            System.out.println(adjs.get(id).toString());
                            if (!id.equals(idBis) && !adjs.get(id).contains(idBis)) {
                                
                                choix.add(idBis);
                            }   
                        }
                    }

                    return new EditeurCuve(this.getModel(), row, column, choix);
                }
                case 2 -> {return new EditeurSection(this.getModel(), row, column);}
                default -> {return super.getCellEditor(row, column);}
            }
        } 
        catch (Exception e) {
            
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setModel(TableModel model) {
        
        super.setModel(model);
        if (model instanceof TubeModel) {
            
            this.modelTube = (TubeModel) model;
        }
    }
}