package it.fipavpuglia.taranto.lm.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author luca
 */
class paneEccezioni extends paneAbstract{

    private static paneEccezioni jpanel;

    private paneEccezioni(){
        super();
        initTableModel();
        addButton();
        setVisible(true);
    }

    public static paneEccezioni getPanel(){
        if (jpanel==null)
            jpanel = new paneEccezioni();
        return jpanel;
    }    
    @Override
    void initTableModel(){
        String[] columnNames = {"Arbitro", "Data"};
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
        //proxy.saveEccezioni(dtm.getDataVector());
    }

    private void addButton() {
        JButton jbRemoveAll = new JButton("Rimuovi tutto");
        jbRemoveAll.setToolTipText("Rimuove tutte le righe della tabella");
        jbRemoveAll.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                removeAllRows();
            }
        });
        jpb.add(jbRemoveAll);
    }

    private void removeAllRows(){
        for (int i=dtm.getRowCount()-1; i>=0; i--)
            dtm.removeRow(i);
    }
}