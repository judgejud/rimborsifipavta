package it.fipavpuglia.taranto.lm.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jfacility.javax.swing.ComboBoxEditor;
import org.jfacility.javax.swing.Swing;
/**
 *
 * @author luca
 */
class paneAnagraficaPersona extends paneAbstract{
    private static paneAnagraficaPersona jpanel;
    private final String[] columnNames = {"Codice", "Cognome Nome", "Sesso", "Data nascita", 
        "Luogo nascita", "Codice fiscale", "Residenza", "Indirizzo", "CAP"};
    private final String[] items_sex = {"", "uomo", "donna"};

    private paneAnagraficaPersona(){
        super();
        initTableModel();
        setVisible(true);
    }

    static paneAnagraficaPersona getPanel(){
        if (jpanel==null)
            jpanel = new paneAnagraficaPersona();
        return jpanel;
    }
    @Override
    void initTableModel(){        
        dtm = new DefaultTableModel(null, columnNames) {
            Class[] types = new Class[]{String.class, String.class, String.class, 
                        String.class, String.class, String.class, String.class,
                        String.class, Integer.class};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };        
        jtable.setModel(dtm);
        
        Swing.setTableDimensionLockColumn(jtable, 2, 60); //sesso
        Swing.setTableDimensionLockColumn(jtable, 3, 90); //data nascita
        Swing.setTableDimensionLockColumn(jtable, 5, 120); //codice fiscale
        Swing.setTableDimensionLockColumn(jtable, 8, 50); //cap
        
        TableColumn col_sex = jtable.getColumnModel().getColumn(2); //sesso
        col_sex.setCellEditor(new ComboBoxEditor(items_sex));
        col_sex.setCellRenderer(new cmbRenderer(items_sex));
        //cognome nome
        jtable.getColumnModel().getColumn(1).setCellRenderer(new JLabelExtendedRenderer());
        //indirizzo
        jtable.getColumnModel().getColumn(7).setCellRenderer(new JLabelExtendedRenderer());
    }

    @Override
    void addRow(){
        dtm.insertRow(0,
                new Object[]{null, null, null, null, null, null, null, null, null});
    }

    @Override
    void save(){
        proxy.saveAnagraficaPersona(dtm.getDataVector());
    }    

    class JLabelExtendedRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            //imposta il testo della cella
            String text = null;
            if (value!=null)
                text = value.toString();
            setText(text);
            if (text!=null)
                setToolTipText(Swing.getTextToolTip(table, column, this, text));
            this.repaint();
            return this;
        }
    } //end class JLabelRenderer
}