package it.fipavpuglia.taranto.lm.core;

import de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel;

import it.fipavpuglia.taranto.lm.gui.jframe;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author luca
 */
public class Client {
    public static void main(String[] args) {
        final String appId = "fipavTaRimborsi";
        boolean alreadyRunning=false;
        try {
            JUnique.acquireLock(appId);
            alreadyRunning = false;
        } catch (AlreadyLockedException e) {
            JOptionPane.showMessageDialog(null, "C'è già la stessa applicazione aperta",
                    appId, JOptionPane.ERROR_MESSAGE);
            alreadyRunning = true;
        }
        if (!alreadyRunning) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(new SyntheticaStandardLookAndFeel());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    }
                    Kernel.getInstance();
                    new jframe();
                    Kernel.getInstance().loadXML();
                }
            });
        }//end if
    }//end main
}//end class