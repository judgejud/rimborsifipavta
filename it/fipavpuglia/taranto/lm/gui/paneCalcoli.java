package it.fipavpuglia.taranto.lm.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author luca
 */
public class paneCalcoli extends JPanel{

    private static paneCalcoli jpanel;
    private JTextField jtxtDateFrom, jtxtDateTo;
    private Mediator proxy = Mediator.getInstance();

    private paneCalcoli(){
        super(new GridBagLayout());
        init();
    }

    static paneCalcoli getPanel() {
        if (jpanel==null)
            jpanel = new paneCalcoli();
        return jpanel;
    }

    private void init(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        this.add(new JLabel("Data inizio calcoli"), gbc);
        jtxtDateFrom = new JTextField(10);
        gbc.gridx = 2;
        this.add(jtxtDateFrom, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(new JLabel("Data fine calcoli"), gbc);
        jtxtDateTo = new JTextField(10);
        gbc.gridx = 2;
        this.add(jtxtDateTo, gbc);
        JButton jbCalcoli = new JButton("Calcola");
        jbCalcoli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proxy.invokeCalcoli(jtxtDateFrom.getText(), jtxtDateTo.getText());
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        this.add(jbCalcoli, gbc);
        JButton jbStampaPdf = new JButton("Stampa PDF");
        jbStampaPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proxy.invokePrintPdf(jtxtDateFrom.getText(), jtxtDateTo.getText());
            }
        });
        gbc.gridy = 6;
        this.add(jbStampaPdf, gbc);
    }
}