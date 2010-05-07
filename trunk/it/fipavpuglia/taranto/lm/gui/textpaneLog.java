package it.fipavpuglia.taranto.lm.gui;

import it.fipavpuglia.taranto.lm.gui.events.MyTextPaneEventListener;
import it.fipavpuglia.taranto.lm.gui.events.MyTextPaneEvent;
//IMPORT JAVA
import java.awt.Color;
//IMPORT JAVAX
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
/**
 *
 * @author luca
 */
class textpaneLog extends JTextPane implements MyTextPaneEventListener{
    //PRIVATE
    private StyledDocument mysd;
    private Style styleOK, styleError, styleAlert;
    private Mediator proxy = Mediator.getInstance();
    /**Costruttore
     * Inizializza la myjtextpane
     */
    public textpaneLog() {
        super();
        this.setEditable(false);
        this.setBackground(Color.black);
        addStyle();
    }
    /**aggiunge gli stili per la jtextpane*/
    private void addStyle(){
        int dim = 12;
        mysd = (StyledDocument)this.getDocument();
        // Create a style object and then set the style attributes
        styleOK = mysd.addStyle("StyleOK", null);
        styleError = mysd.addStyle("StyleError", null);
        styleAlert = mysd.addStyle("StyleAlert", null);
        // Bold
        //StyleConstants.setBold(style, false);
        // Font family
        StyleConstants.setFontFamily(styleOK, "SansSerif");
        StyleConstants.setFontFamily(styleError, "SansSerif");
        StyleConstants.setFontFamily(styleAlert, "SansSerif");
        // Font size
        StyleConstants.setFontSize(styleOK, dim);
        StyleConstants.setFontSize(styleError, dim);
        StyleConstants.setFontSize(styleAlert, dim);
        // Foreground color
        StyleConstants.setForeground(styleOK, Color.green);
        StyleConstants.setForeground(styleError, Color.red);
        StyleConstants.setForeground(styleAlert, Color.yellow);
    }
    /**Aggiunge alla textpane il testo con stile OK
     *
     * @param msg testo da aggiungere
     */
    public void appendOK(String msg){
        append(msg, styleOK);
    }
    /**Aggiunge alla textpane il testo con stile ALERT
     *
     * @param msg testo da aggiungere
     */
    public void appendAlert(String msg){
        append(msg, styleAlert);
    }
    /**Aggiunge alla textpane il testo con stile ERROR
     *
     * @param msg testo da aggiungere
     */
    public void appendError(String msg){
        append(msg, styleError);
    }

    public void append(String msg, Style s){
        try {
            mysd.insertString(0, proxy.actualTime() + " " + msg + "\n", s);
        } catch (BadLocationException ex) {}
    }
    @Override
    public void objReceived(MyTextPaneEvent evt) {
        if (evt.getType().equals(MyTextPaneEvent.ERROR))
            appendError(evt.getMsg());
        else if (evt.getType().equals(MyTextPaneEvent.OK))
            appendOK(evt.getMsg());        
        else if (evt.getType().equals(MyTextPaneEvent.ALERT))
            appendAlert(evt.getMsg());
    }
}//end class