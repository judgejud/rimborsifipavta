package it.fipavpuglia.taranto.lm.gui;

import javax.swing.table.DefaultTableModel;
import org.lp.myUtils.Swing;

/**
 *
 * @author luca
 */
public class panePartite extends paneAbstract{

    private static panePartite jpanel;
    private final String[] columnNames = {"Data", "Designazione", "Località", "Uso Macchina",
        "Spese documentate", "Rimborso referto", "Rimborso altre spese"};

    private panePartite(){
        super();
        initTableModel();
        setVisible(true);
    }

    static panePartite getPanel(){
        if (jpanel==null)
            jpanel = new panePartite();
        return jpanel;
    }

    @Override
    void initTableModel() {
        dtm = new DefaultTableModel(null, columnNames) {
            Class[] types = new Class[]{String.class, String.class, String.class, Boolean.class, 
                String.class, Boolean.class, String.class};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        jtable.setModel(dtm);

        Swing.setTableDimensionLockColumn(jtable, 0, 80);
        Swing.setTableDimensionLockColumn(jtable, 3, 90);
        Swing.setTableDimensionLockColumn(jtable, 4, 120);
        Swing.setTableDimensionLockColumn(jtable, 5, 110);
        Swing.setTableDimensionLockColumn(jtable, 6, 130);
        
    }

    @Override
    void save() {
        
    }

    @Override
    void addRow() {
        dtm.insertRow(0,new Object[]{null,null, null, true, null, false , null});
    }

}
