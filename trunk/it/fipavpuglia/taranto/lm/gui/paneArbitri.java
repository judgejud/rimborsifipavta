package it.fipavpuglia.taranto.lm.gui;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author luca
 */
class paneArbitri extends paneAbstract{
    private static paneArbitri jpanel;

    private paneArbitri(){
        super();
        initTableModel();
        setVisible(true);
    }

    static paneArbitri getPanel(){
        if (jpanel==null)
            jpanel = new paneArbitri();
        return jpanel;
    }
    @Override
    void initTableModel(){
        String[] columnNames = {"Arbitro", "Residenza"};
        dtm = new DefaultTableModel(null, columnNames) {
            Class[] types = new Class[]{String.class, String.class};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        jtable.setModel(dtm);
    }
    @Override
    void addRow(){
        dtm.insertRow(0,new Object[]{null,null});
    }
    @Override
    void save(){
        proxy.saveArbitri(dtm.getDataVector());
    }
}