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

import org.lp.myUtils.lang.Lang;
/**
 *
 * @author luca
 */
@SuppressWarnings("serial")
public class jframe extends JFrame implements WindowListener, MyFrameEventListener{
    private final Dimension SCREENSIZE = new Dimension(1024, 600);
    private final Dimension TABBEDSIZE = new Dimension(1024, 440);
    private final Dimension TEXTPANESIZE = new Dimension(1024, 110);
    private Mediator proxy = Mediator.getInstance();
    private JTabbedPane jtabbedpane;
    private textpaneLog jtpLog;
    private paneAnagrafica jpArbitri;
    private paneCarta jpCarta;
    private paneEccezioni jpEccezioni;

    public jframe() {
        super("Gestione Rimborsi FIPAV TA by Mignogna Luca");
        this.setPreferredSize(SCREENSIZE);
        this.setMinimumSize(SCREENSIZE);
        this.setLayout(new BorderLayout());
        initMenuBar();
        initTabPanel();
        this.setVisible(true);
        jtpLog = new textpaneLog();
        JScrollPane jScrollText1 = new JScrollPane(jtpLog);
        jScrollText1.setPreferredSize(TEXTPANESIZE);
        add(jScrollText1, BorderLayout.SOUTH);        
        proxy.setTextPaneListener(jtpLog);
        addWindowListener(this);
        jtpLog.appendOK("Versione java in uso: " + Lang.getJavaVersion());
        proxy.loadXML();
    }

    private void initTabPanel(){
        jtabbedpane = new JTabbedPane();
        jtabbedpane.setPreferredSize(TABBEDSIZE);
        jpArbitri = paneAnagrafica.getPanel();
        jtabbedpane.addTab("Arbitri", jpArbitri);
        jpEccezioni = paneEccezioni.getPanel();
        jtabbedpane.addTab("Eccezioni rimborsi", jpEccezioni);
        jpCarta = paneCarta.getPanel();
        jtabbedpane.addTab("Carta Polimetrica", jpCarta);
        this.add(jtabbedpane, BorderLayout.CENTER);
        proxy.setFrameListener(this);
    }

    private void initMenuBar() {
        // Create the menu bar
        JMenuBar jmenuBar = new JMenuBar();        
        // Create a menu
        JMenu jmenuWork = new JMenu(" Operazioni ");
        JMenu jmenuTest = new JMenu(" Test ");
        //create items for menu operazioni
        JMenuItem jmnItem01 = new JMenuItem(" Crea XML Carta Polimetrica ");        
        JMenuItem jmnItemRimborsi = new JMenuItem(" Lavora rimborsi km ");
        JMenuItem jmnItemPulisci = new JMenuItem(" Pulisci log ");
        JMenuItem jmnItemBackupXml = new JMenuItem(" Backup xml ");
        JMenuItem jmnItemExit = new JMenuItem(" Uscita ");
        JMenuItem jmnTestRimborsi = new JMenuItem(" Testa presenza località xls in xml");
        JMenuItem jmnTestArbitri = new JMenuItem(" Testa presenza arbitri xls in xml ");
        jmnItem01.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proxy.invokeNewPolymetric(getParent());
            }
        });
        jmnTestRimborsi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proxy.invokeTestDestinations(getParent());
            }
        });
        jmnTestArbitri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proxy.invokeTestArbitri(getParent());
            }
        });
        jmnItemRimborsi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proxy.invokeRimborsi(getParent());
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
        jmenuWork.add(jmnItemRimborsi);
        jmenuWork.add(jmnItemPulisci);
        jmenuWork.add(jmnItemBackupXml);
        jmenuWork.add(jmnItemExit);
        jmenuTest.add(jmnTestArbitri);
        jmenuTest.add(jmnTestRimborsi);
        jmenuBar.add(jmenuWork);
        jmenuBar.add(jmenuTest);
        this.setJMenuBar(jmenuBar);
    }
    @Override
    public void objReceived(MyFrameEvent evt) {
        if (evt.getNametable().equals(proxy.getNameTableArbitri()))
            jpArbitri.addRows(evt.getArray());
        else if (evt.getNametable().equals(proxy.getNameTableCarta()))
            jpCarta.addRows(evt.getArray());
        else if (evt.getNametable().equals(proxy.getNameTableEcccezioni()))
            jpEccezioni.addRows(evt.getArray());
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