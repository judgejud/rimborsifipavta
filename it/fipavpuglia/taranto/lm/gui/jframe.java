package it.fipavpuglia.taranto.lm.gui;

import it.fipavpuglia.taranto.lm.gui.events.MyFrameEvent;
import it.fipavpuglia.taranto.lm.gui.events.MyFrameEventListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.jfacility.java.lang.MySystem;
/**
 *
 * @author luca
 */
@SuppressWarnings("serial")
public class jframe extends JFrame implements WindowListener, MyFrameEventListener{
    private final Dimension SCREENSIZE = new Dimension(1024, 768);
    private final Dimension TABBEDSIZE = new Dimension(1024, 580);
    private final Dimension TEXTPANESIZE = new Dimension(1024, 140);
    private Mediator proxy = Mediator.getInstance();
    private JTabbedPane jtabbedpane;
    private textpaneLog jtpLog;
    private paneAnagraficaPersona jpAnagraficaPersona;
    private paneAnagraficaFipav jpAnagraficaFipav;
    private paneCarta jpCarta;
    private panePartite jpPartite;
    private paneOpzioni jpOpzioni;
    private paneCalcoli jpCalcoli;

    public jframe() {
        super("Gestione Rimborsi FIPAV TA by Mignogna Luca");
        this.setPreferredSize(SCREENSIZE);
        this.setMinimumSize(SCREENSIZE);
        this.setLayout(new BorderLayout());
        initMenuBar();
        initTabPanel();
        jtpLog = new textpaneLog();
        JScrollPane jScrollText1 = new JScrollPane(jtpLog);
        jScrollText1.setPreferredSize(TEXTPANESIZE);
        add(jScrollText1, BorderLayout.SOUTH);        
        proxy.setTextPaneListener(jtpLog);
        addWindowListener(this);
        jtpLog.appendOK("Versione java in uso: " + MySystem.getJavaVersion());
        this.setVisible(true);
    }

    private void initTabPanel(){
        jtabbedpane = new JTabbedPane();
        jtabbedpane.setPreferredSize(TABBEDSIZE);
        jpAnagraficaPersona = paneAnagraficaPersona.getPanel();
        jtabbedpane.addTab("Anagrafica Persona", jpAnagraficaPersona);
        jpAnagraficaFipav = paneAnagraficaFipav.getPanel();
        jtabbedpane.addTab("Anagrafica Fipav", jpAnagraficaFipav);
        jpCarta = paneCarta.getPanel();
        jtabbedpane.addTab("Carta Polimetrica", jpCarta);
        jpPartite = panePartite.getPanel();
        jtabbedpane.addTab("Designazioni", jpPartite);
        jpOpzioni = paneOpzioni.getPanel();
        jtabbedpane.addTab("Opzioni", jpOpzioni);
        jpCalcoli = paneCalcoli.getPanel();
        jtabbedpane.addTab("Calcoli", jpCalcoli);
        this.add(jtabbedpane, BorderLayout.CENTER);
        proxy.setFrameListener(this);
    }

    private void initMenuBar() {
        // Create the menu bar
        JMenuBar jmenuBar = new JMenuBar();        
        // Create a menu
        JMenu jmenuWork = new JMenu(" Operazioni ");
        //create items for menu operazioni
        JMenuItem jmnItem01 = new JMenuItem(" Crea XML Carta Polimetrica ");
        JMenuItem jmnItemPulisci = new JMenuItem(" Pulisci log ");
        JMenuItem jmnItemBackupXml = new JMenuItem(" Backup xml ");
        JMenuItem jmnItemExit = new JMenuItem(" Uscita ");
        
        jmnItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proxy.invokeNewPolymetric(getParent());
            }
        });        
        jmnItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jframe.this.dispose();
            }
        });
        jmnItemPulisci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtpLog.setText(null);
            }
        });
        jmnItemBackupXml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proxy.invokeBackupXml(jframe.this);
            }
        });        

        jmenuWork.add(jmnItem01);
        jmenuWork.add(jmnItemPulisci);
        jmenuWork.add(jmnItemBackupXml);
        jmenuWork.add(jmnItemExit);        
        jmenuBar.add(jmenuWork);
        this.setJMenuBar(jmenuBar);
    }

    @Override
    public void objReceived(MyFrameEvent evt) {
        if (evt.getNameDest().equals(proxy.getNameTableAnagraficaPersona()))
            jpAnagraficaPersona.addRows(evt.getArrayList());
        else if(evt.getNameDest().equals(proxy.getNameTableAnagraficaFipav()))
            jpAnagraficaFipav.addRows(evt.getArrayList());
        else if (evt.getNameDest().equals(proxy.getNameTableCarta()))
            jpCarta.addRows(evt.getArrayList());
        else if (evt.getNameDest().equals(proxy.getNameTableOptions()))
            jpOpzioni.setTableValues(evt.getArrayFloat());
        else if (evt.getNameDest().equals(proxy.getNameComboArbitri())){
            jpPartite.setComboArbitriValues(evt.getArrayString());
            jpAnagraficaFipav.setComboArbitriValues(evt.getArrayString());
        } else if (evt.getNameDest().equals(proxy.getNameComboLocalita()))
            jpPartite.setComboLocalitaValues(evt.getArrayString());
        else if (evt.getNameDest().equals(proxy.getNameTableDesignaz()))
            jpPartite.addRows(evt.getArrayList());
    }
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {
        this.dispose();
    }
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}