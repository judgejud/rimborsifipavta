package it.fipavpuglia.taranto.lm.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author luca
 */
abstract class paneAbstract extends JPanel{
    protected JTable jtable;
    protected DefaultTableModel dtm;
    protected Mediator proxy = Mediator.getInstance();
    protected JPanel jpb;
    protected JButton jbAdd, jbRemove, jbUndo;
    private int pos=-1;
    private Vector v;
    /**Costruttore protetto, per essere invocato dai figli tramite ereditarietà*/
    protected paneAbstract(){
        super(new BorderLayout());
        initButtons();
        initTable();
    }        
    /**inizializza i bottoni del pannello nord*/
    private void initButtons(){
        jpb = new JPanel();
        jpb.setSize(new Dimension(800,30));

        jbAdd = new JButton("Aggiungi");
        jbRemove = new JButton("Rimuovi");
        JButton jbSave = new JButton("Salva");
        jbUndo = new JButton("Annulla");

        jbAdd.setToolTipText("Aggiungi rigo alla tabella");
        jbRemove.setToolTipText("Rimuove rigo selezionato dalla tabella");
        jbSave.setToolTipText("Salva la tabella");
        jbUndo.setToolTipText("Annulla ultima cancellazione");

        jbAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                addRow();
            }
        });
        jbRemove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                removeRow();
            }
        });
        jbSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                save();
            }
        });
        jbUndo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                undo();
            }
        });

        jpb.add(jbAdd);
        jpb.add(jbRemove);
        jpb.add(jbSave);
        jpb.add(jbUndo);
        this.add(jpb,BorderLayout.NORTH);
    }    
    
    private void removeRow(){
        pos = jtable.getSelectedRow();
        if (pos>-1){
            int col = dtm.getColumnCount();
            v = new Vector();
            for (int i=0; i<col; i++)
                v.add(dtm.getValueAt(pos, i));
            dtm.removeRow(pos);
        }
    }

    void addRows(ArrayList<String[]> al){
        if (al!=null)
            for (int i=0; i<al.size(); i++)
                dtm.addRow(al.get(i));
    }    

    private void initTable(){
        jtable = new JTable();
        jtable.setSelectionMode(0);
        jtable.getTableHeader().setReorderingAllowed(false);
        JScrollPane jscrollpane = new JScrollPane(jtable);
        this.add(jscrollpane, BorderLayout.CENTER);
    }

    private void undo(){
        if (pos>-1){
            dtm.insertRow(pos, v);
            pos =-1;
        }
    }

    abstract void initTableModel();
    abstract void save();
    abstract void addRow();    
}