package it.fipavpuglia.taranto.lm.gui;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author luca
 */
class paneCarta extends paneAbstract{

    private static paneCarta jpanel;

    private paneCarta(){
        super();
        initTableModel();
        setVisible(true);
    }

    static paneCarta getPanel(){
        if (jpanel==null)
            jpanel = new paneCarta();
        return jpanel;
    }
    @Override
    void initTableModel(){
        String[] columnNames = {"Paese", "Paese", "Km"};
        dtm = new DefaultTableModel(null, columnNames) {
            Class[] types = new Class[]{String.class, String.class, String.class};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        jtable.setModel(dtm);
    }
    @Override
    void addRow(){
        dtm.insertRow(0,new Object[]{null,null,null});
    }
    @Override
    void save(){
        proxy.saveCarta(dtm.getDataVector());
    }
}