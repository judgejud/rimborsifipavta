package it.fipavpuglia.taranto.lm.gui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jfacility.swing.ComboBoxEditor;
import org.jfacility.swing.Swing;
/**
 *
 * @author luca
 */
class paneAnagraficaFipav extends paneAbstract{
    private static paneAnagraficaFipav jpanel;
    private final String[] columnNames = {"Codice", "Residenza Polimetrica", "Ruolo", 
        "Comitato", "Assegno"};
    private final String[] items_comitati = {"", "TARANTO", "BARI", "BRINDISI", "LECCE"};
    private final String[] items_role = {"", "Arbitro", "Osservatore", "Presidente",
        "Consigliere"};
    private ComboBoxEditor cbeArbitri;
    private cmbRenderer crArbitri;

    private paneAnagraficaFipav(){
        super();
        initTableModel();
        setVisible(true);
    }

    static paneAnagraficaFipav getPanel(){
        if (jpanel==null)
            jpanel = new paneAnagraficaFipav();
        return jpanel;
    }
    @Override
    void initTableModel(){        
        dtm = new DefaultTableModel(null, columnNames) {
            Class[] types = new Class[]{String.class, String.class, String.class, String.class,
                Boolean.class};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };        
        jtable.setModel(dtm);

        Swing.setTableDimensionLockColumn(jtable, 2, 100); //ruolo
        Swing.setTableDimensionLockColumn(jtable, 3, 100); //comitato
        Swing.setTableDimensionLockColumn(jtable, 4, 100); //assegno

        TableColumn col_code = jtable.getColumnModel().getColumn(0); //codice
        crArbitri = new cmbRenderer();
        col_code.setCellEditor(new ComboBoxEditor());
        col_code.setCellRenderer(crArbitri);

        TableColumn col_role = jtable.getColumnModel().getColumn(2); //ruolo
        col_role.setCellEditor(new ComboBoxEditor(items_role));
        col_role.setCellRenderer(new cmbRenderer(items_role));

        TableColumn col_comitato = jtable.getColumnModel().getColumn(3); //comitato
        col_comitato.setCellRenderer(new cmbRenderer(items_comitati));
        col_comitato.setCellEditor(new ComboBoxEditor(items_comitati));
    }

    @Override
    void addRow(){
        dtm.insertRow(0,new Object[]{null, null, null, null, false});
    }

    @Override
    void save(){
        proxy.saveAnagraficaFipav(dtm.getDataVector());
    }

    void setComboArbitriValues(String[] arrayS) {
        if (crArbitri.getItemCount()>0)
            crArbitri.setModel(new DefaultComboBoxModel());
        DefaultComboBoxModel dcbm = new DefaultComboBoxModel(arrayS);
        dcbm.insertElementAt(null, 0);
        crArbitri.setModel(dcbm);
        jtable.getColumnModel().getColumn(0).setCellEditor(new ComboBoxEditor(arrayS));
        //crArbitri.setSelectedIndex(0);
    }
}