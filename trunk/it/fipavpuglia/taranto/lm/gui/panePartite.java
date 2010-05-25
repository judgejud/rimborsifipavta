package it.fipavpuglia.taranto.lm.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.lp.myUtils.Swing;
/**
 *
 * @author luca
 */
public class panePartite extends paneAbstract{

    private static panePartite jpanel;
    private final String[] columnNames = {"Data", "Designazione", "Località", "Concentramento",
            "Uso Macchina", "Spese documentate", "Rimborso referto", "Rimborso altre spese"};
    private JComboBox jcbArbitri;

    private panePartite(){
        super();
        initTableModel();
        initPane();
        setVisible(true);
    }

    static panePartite getPanel(){
        if (jpanel==null)
            jpanel = new panePartite();
        return jpanel;
    }

    private void initPane() {
        jpb.add(new JLabel("Tabella Arbitro"));
        jcbArbitri = new JComboBox();
        jcbArbitri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jcbArbitri.getSelectedIndex()>0)
                    proxy.invokeNewArbitro(jcbArbitri.getSelectedItem());
            }
        });
        jpb.add(jcbArbitri);        
    }
    @Override
    void initTableModel() {
        dtm = new DefaultTableModel(null, columnNames) {
            Class[] types = new Class[]{String.class, String.class, String.class, Boolean.class, 
                Boolean.class, Float.class, Boolean.class, Float.class};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        jtable.setModel(dtm);

        Swing.setTableDimensionLockColumn(jtable, 0, 80);
        Swing.setTableDimensionLockColumn(jtable, 3, 100);
        Swing.setTableDimensionLockColumn(jtable, 4, 90);
        Swing.setTableDimensionLockColumn(jtable, 5, 120);
        Swing.setTableDimensionLockColumn(jtable, 6, 110);
        Swing.setTableDimensionLockColumn(jtable, 7, 130);
    }

    @Override
    void save() {
        if (jcbArbitri.getSelectedIndex()>0 && dtm.getRowCount()>0)
            proxy.saveDesignazioni(jcbArbitri.getSelectedItem(), dtm.getDataVector());
    }

    @Override
    void addRow() {
        if (jcbArbitri.getSelectedIndex()>0)
            dtm.insertRow(dtm.getRowCount(),
                new Object[]{null,null, null, false, true, 0.00, false , 0.00});
    }

    void setComboValues(Object[] arrayO) {
        if (jcbArbitri.getItemCount()>0)
            jcbArbitri.setModel(null);
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel(arrayO);
        dcbm.insertElementAt(null, 0);
        jcbArbitri.setModel(dcbm);
        jcbArbitri.setSelectedIndex(0);
    }
}