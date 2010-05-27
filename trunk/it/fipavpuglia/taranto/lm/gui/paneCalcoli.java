package it.fipavpuglia.taranto.lm.gui;

import javax.swing.JPanel;

/**
 *
 * @author luca
 */
public class paneCalcoli extends JPanel{

    private static paneCalcoli jpanel;

    private paneCalcoli(){
        super();
    }

    static paneCalcoli getPanel() {
        if (jpanel==null)
            jpanel = new paneCalcoli();
        return jpanel;
    }
}