package it.fipavpuglia.taranto.lm.gui;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.lp.myUtils.Swing;
/**
 *
 * @author luca
 */
class paneAnagrafica extends paneAbstract{
    private static paneAnagrafica jpanel;
    private final String[] columnNames = {"Codice", "Residenza Polimetrica", "Ruolo",
        "Cognome Nome", "Sesso", "Data nascita", "Luogo nascita", "Codice fiscale", "Residenza",
        "Indirizzo", "CAP"};
    private final String[] items_role = {"", "Arbitro", "Osservatore"};
    private final String[] items_sex = {"", "uomo", "donna"};

    private paneAnagrafica(){
        super();
        initTableModel();
        setVisible(true);
    }

    static paneAnagrafica getPanel(){
        if (jpanel==null)
            jpanel = new paneAnagrafica();
        return jpanel;
    }
    @Override
    void initTableModel(){        
        dtm = new DefaultTableModel(null, columnNames) {
            Class[] types = new Class[]{String.class, String.class, String.class, String.class,
                            String.class, String.class, String.class, String.class, String.class,
                            String.class, Integer.class};
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };        
        jtable.setModel(dtm);

        Swing.setTableDimensionLockColumn(jtable, 2, 80); //ruolo
        Swing.setTableDimensionLockColumn(jtable, 4, 50); //sesso
        Swing.setTableDimensionLockColumn(jtable, 5, 75); //data nascita
        Swing.setTableDimensionLockColumn(jtable, 7, 110); //codice fiscale
        Swing.setTableDimensionLockColumn(jtable, 10, 40); //cap

        TableColumn col_role = jtable.getColumnModel().getColumn(2);
        col_role.setCellEditor(new MyComboBoxEditor(items_role));
        col_role.setCellRenderer(new MyComboBoxRenderer(items_role));

        TableColumn col_sex = jtable.getColumnModel().getColumn(4);
        col_sex.setCellEditor(new MyComboBoxEditor(items_sex));
        col_sex.setCellRenderer(new MyComboBoxRenderer(items_sex));
        //cognome nome
        jtable.getColumnModel().getColumn(3).setCellRenderer(new JLabelExtendedRenderer());
        //indirizzo
        jtable.getColumnModel().getColumn(9).setCellRenderer(new JLabelExtendedRenderer());
    }
    @Override
    void addRow(){
        dtm.insertRow(0,new Object[]{null,null, "Arbitro", null,"uomo",null,
                        null,null,null,null,null});
    }
    @Override
    void save(){
        proxy.saveAnagrafica(dtm.getDataVector());
    }

    class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
        public MyComboBoxRenderer(String[] items) {
            super(items);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());                
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setSelectedItem(value);
            return this;
        }
    }

    class MyComboBoxEditor extends DefaultCellEditor {
        public MyComboBoxEditor(String[] items) {
            super(new JComboBox(items));
        }
    }

    class JLabelExtendedRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            //imposta il testo della cella
            String text = value.toString();
            setText(text);
            setToolTipText(Swing.getTextToolTip(table, column, this, text));
            this.repaint();
            return this;
        }
    } //end class JLabelRenderer
}