package it.fipavpuglia.taranto.lm.gui;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author luca
 */
public class paneOpzioni extends paneAbstract{
    
    private static paneOpzioni jpanel;
    private final String[] columnNames = {"Descrizione", "Valore"};

    private paneOpzioni(){
        super();
        jbAdd.setVisible(false);
        jbRemove.setVisible(false);
        initTableModel();
        setVisible(true);
    }

    static paneOpzioni getPanel(){
        if (jpanel==null)
            jpanel = new paneOpzioni();
        return jpanel;
    }

    @Override
    void initTableModel() {
        dtm = new DefaultTableModel(null, columnNames) {
            Class[] types = new Class[]{String.class, Float.class};
            boolean[] canEdit = new boolean [] {false, true};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        dtm.addRow(new String[]{"Costo kilometrico", null});
        dtm.addRow(new String[]{"Costo singola partita", null});
        dtm.addRow(new String[]{"Costo doppia partita", null});
        dtm.addRow(new String[]{"Costo rimborso referto", null});
        jtable.setModel(dtm);
    }

    @Override
    void save() {
        proxy.saveOption(dtm.getDataVector());
    }
    @Override
    void addRow() {}
}