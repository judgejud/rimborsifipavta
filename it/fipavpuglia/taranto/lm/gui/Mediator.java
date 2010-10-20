package it.fipavpuglia.taranto.lm.gui;

import it.fipavpuglia.taranto.lm.core.AnagraficaFipav;
import it.fipavpuglia.taranto.lm.core.AnagraficaPersona;
import it.fipavpuglia.taranto.lm.core.Carta;
import it.fipavpuglia.taranto.lm.core.Kernel;
import it.fipavpuglia.taranto.lm.gui.events.*;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfacility.Text;

import org.jfacility.swing.Swing;
/**
 *
 * @author luca
 */
class Mediator {
    private static Mediator proxy = null;
    private Kernel core = Kernel.getInstance();
    private final FileNameExtensionFilter fnfeXLS =
            new FileNameExtensionFilter("Excel file", new String[] { "xls", "xlsx" });
    private final FileNameExtensionFilter fnfeZIP =
            new FileNameExtensionFilter("ZIP file", "zip");
    private final FileNameExtensionFilter fnfePDF =
            new FileNameExtensionFilter("PDF file", "pdf");
    private List listenerTextPane  = new ArrayList();
    private Object oldArbitro = "";

    private Mediator(){}
    /**Restituisce l'istanza corrente del mediator
     *
     * @return istanza mediator
     */
    static Mediator getInstance(){
        if (proxy==null)
            proxy = new Mediator();
        return proxy;
    }
    /**Invoca la creazione di una nuova carta polimetrica
     *
     * @param parent
     */
    void invokeNewPolymetric(Component parent) {
        String name = Swing.getFile(parent, "Selezionare il file excel della carta polimetrica",
                fnfeXLS, new File(core.getCurDir()));
        if (name!=null)
            core.createNewPolymetric(new File(name));
    }

    void invokeBackupXml(Component parent) {
        String name = Swing.getFile(parent, "Creare il file zip per il backup",
                fnfeZIP, new File(core.getCurDir()));
        if (name!=null)
            core.backupXml(name);
    }

    String actualTime(){
        Calendar cal = new GregorianCalendar();
        String s = cal.get(Calendar.DATE)+"/"+ (cal.get(Calendar.MONTH)+1)+"/"+ 
                cal.get(Calendar.YEAR) + " " + addZero(cal.get(Calendar.HOUR_OF_DAY)) + ":" +
                addZero(cal.get(Calendar.MINUTE)) + ":" +
                addZero(cal.get(Calendar.SECOND));
        return s;
    }
    /**Aggiunge lo zero ai numeri < 10
     *
     * @param n intero da controllare
     * @return Stringa con/senza 0
     */
    private String addZero(int n){
        String s = new String();
        if (n<10)
            s = "0";
        s += String.valueOf(n);
        return s;
    }

    void saveAnagraficaPersona(Vector dataVector) {
        TreeMap<String, AnagraficaPersona> tm = new TreeMap<String, AnagraficaPersona>();
        boolean _break = false;
        String code="";
        for (int i=0; i<dataVector.size(); i++){
            Vector temp = (Vector)dataVector.elementAt(i);
            code = ((String)temp.elementAt(0)).toUpperCase();
            if (tm.containsKey(code)){
                _break=true;
                printAlert("Trovato doppione: " + code);
                break;
            } else {
                String nome = (String)temp.elementAt(1);
                String sesso = (String)temp.elementAt(2);
                String data_nascita = (String)temp.elementAt(3);
                String luogo_nascita = (String)temp.elementAt(4);
                String codice_fiscale = (String)temp.elementAt(5);
                String residenza_città = (String)temp.elementAt(6);
                String indirizzo = (String)temp.elementAt(7);
                String cap = String.valueOf(temp.elementAt(8));
                
                AnagraficaPersona a = new AnagraficaPersona(nome, luogo_nascita,
                        data_nascita, codice_fiscale, residenza_città,
                        indirizzo, cap, sesso);
                
                tm.put(code, a);
            }
        }
        if (!_break)
            core.saveAnagraficaPersona(tm);
    }

    void saveAnagraficaFipav(Vector dataVector) {
        TreeMap<String, AnagraficaFipav> tm = new TreeMap<String, AnagraficaFipav>();
        boolean _break = false;
        String code="";
        for (int i=0; i<dataVector.size(); i++){
            Vector temp = (Vector)dataVector.elementAt(i);
            code = ((String)temp.elementAt(0)).toUpperCase();
            if (tm.containsKey(code)){
                _break=true;
                printAlert("Trovato doppione: " + code);
                break;
            } else {
                String residenza_carta = ((String)temp.elementAt(1)).toUpperCase();
                String ruolo = (String)temp.elementAt(2);
                String comitato = (String)temp.elementAt(3);                
                String assegno = String.valueOf(temp.elementAt(12));

                AnagraficaFipav a = new AnagraficaFipav(ruolo, residenza_carta, comitato,
                        assegno);

                tm.put(code, a);
            }
        }
        if (!_break)
            core.saveAnagraficaFipav(tm);
    }

    void saveCarta(Vector dataVector) {
        Carta c = null;
        try {
            TreeMap<Carta, String> tm = new TreeMap<Carta, String>();
            boolean _break = false;
            for (int i=0; i<dataVector.size(); i++){
                Vector temp = (Vector)dataVector.elementAt(i);
                String from = ((String)temp.elementAt(0)).toUpperCase();
                String to = ((String)temp.elementAt(1)).toUpperCase();
                c = new Carta(from, to);
                if (tm.containsKey(c)){
                    _break=true;
                    break;
                } else {
                    String km = String.valueOf(temp.elementAt(2));
                    Integer.parseInt(km);
                    tm.put(c, km);
                }
            }
            if (_break)
                printAlert("Coppia paesi presente nell'xml :" + c.getPartenza() + " - "
                        + c.getArrivo());
            else
                core.saveCarta(tm);
        } catch (NumberFormatException ex) {
            printAlert("km non valido per la coppia: " + c.getPartenza() + " - " +
                    c.getArrivo());
        }
    }

    void saveOption(Vector dataVector) {
        float[] values = new float[5];
        Vector temp = null;
        try{
            for (int i=0; i<dataVector.size(); i++){
                temp = (Vector)dataVector.elementAt(i);
                values[i] = Float.valueOf(temp.elementAt(1).toString()).floatValue();
            }
            core.saveOptions(values);
        } catch (NullPointerException e){
            printAlert(temp.elementAt(0).toString() + ": immettere un valore del tipo 0.0");
        }
    }

    void invokeNewArbitro(Object selectedItem) {
        if (!oldArbitro.equals(selectedItem)){
            oldArbitro = selectedItem;
            core.changeDesignazioni(selectedItem);
        }
    }

    void invokeCalcoli(String from, String to) {
        try {
            Date begin = Text.convertStringToDate(from);
            Date end = Text.convertStringToDate(to);
            core.fireCalcoli(begin, end);
        } catch (ParseException e) {
            printAlert("controllare le date per il calcolo nel formato gg/mm/yyyy");
        }
    }

    void saveDesignazioni(Object selectedItem, Vector dataVector) {
        core.saveDesignazioni(selectedItem, dataVector);
    }

    void invokePrintPdf(Container parent, String from, String to) {
        String name = Swing.getFile(parent, "dare un nome al file PDF da generare",
                fnfePDF, new File(core.getCurDir()));
        if (name!=null)
            core.createPDF(name, from+"-"+to);
    }

    String getNameTableAnagraficaPersona(){
        return core.getTABLE_ANAGRAFICA_PERSONA();
    }

    String getNameTableAnagraficaFipav(){
        return core.getTABLE_ANAGRAFICA_FIPAV();
    }
    
    String getNameTableCarta(){
        return core.getTABLE_CARTA();
    }

    String getNameTableOptions(){
        return core.getTABLE_OPTIONS();
    }

    String getNameComboArbitri(){
        return core.getCOMBO_ARBITRI();
    }

    String getNameComboLocalita(){
        return core.getCOMBO_LOCALITA();
    }

    String getNameTableDesignaz(){
        return core.getTABLE_DESIGNAZIONI();
    }

    private void printAlert(String print){
        fireNewTextPaneEvent(print, MyTextPaneEvent.ALERT);
    }

    void setFrameListener(MyFrameEventListener listener){
        core.addMyFrameEventListener(listener);
    }

    void setTextPaneListener(MyTextPaneEventListener listener){
        core.addMyTextPaneEventListener(listener);
        addMyTextPaneEventListener(listener);
    }
    // This methods allows classes to register for MyEvents
    public synchronized void addMyTextPaneEventListener(MyTextPaneEventListener listener) {
        listenerTextPane.add(listener);
    }
    // This methods allows classes to unregister for MyEvents
    public synchronized void removeMyTextPaneEventListener(MyTextPaneEventListener listener) {
        listenerTextPane.remove(listener);
    }

    private synchronized void fireNewTextPaneEvent(String msg, String type) {
        MyTextPaneEvent event = new MyTextPaneEvent(this, msg, type);
        Iterator listeners = listenerTextPane.iterator();
        while(listeners.hasNext() ) {
            MyTextPaneEventListener myel = (MyTextPaneEventListener)listeners.next();
            myel.objReceived(event);
        }
    }
}