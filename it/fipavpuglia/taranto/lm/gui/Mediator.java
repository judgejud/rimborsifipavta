package it.fipavpuglia.taranto.lm.gui;

import it.fipavpuglia.taranto.lm.core.Anagrafica;
import it.fipavpuglia.taranto.lm.core.Carta;
import it.fipavpuglia.taranto.lm.core.Kernel;
import it.fipavpuglia.taranto.lm.gui.events.*;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.lp.myUtils.Swing;
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

    void invokeTestDestinations(Component parent) {
        String name = Swing.getFile(parent, "Selezionare il file excel del rimborso",
                fnfeXLS, new File(core.getCurDir()));
        if (name!=null)
            core.testDestinations(new File(name));
    }

    void invokeRimborsi(Component parent) {        
        String name = Swing.getFile(parent, "Selezionare il file excel del rimborso",
                fnfeXLS, new File(core.getCurDir()));
        if (name!=null)
            core.writeRimborsi(new File(name));
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

    void loadXML() {
        core.loadXML();
    }

    void saveAnagrafica(Vector dataVector) {
        TreeMap<String, Anagrafica> tm = new TreeMap<String, Anagrafica>();
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
                String nome = (String)temp.elementAt(3);
                String sesso = (String)temp.elementAt(4);
                String data_nascita = (String)temp.elementAt(5);
                String luogo_nascita = (String)temp.elementAt(6);
                String codice_fiscale = (String)temp.elementAt(7);
                String residenza_città = (String)temp.elementAt(8);
                String indirizzo = (String)temp.elementAt(9);
                String cap = String.valueOf(temp.elementAt(10));
                
                Anagrafica a = new Anagrafica(nome, luogo_nascita, data_nascita, codice_fiscale,
                        residenza_città, indirizzo, cap, ruolo, sesso, residenza_carta);
                
                tm.put(code, a);
            }
        }
        if (!_break)
            core.saveAnagrafica(tm);
    }
/*
    void saveEccezioni(Vector dataVector) {
        String arbitro = null;
        String _date = null;
        TreeMap<String, TreeSet<Date>> tm = new TreeMap<String, TreeSet<Date>>();
        boolean _break = false;
        try {
            for (int i=0; i<dataVector.size(); i++){
                Vector temp = (Vector) dataVector.elementAt(i);
                arbitro = ((String) temp.elementAt(0)).toUpperCase();
                _date = (String) temp.elementAt(1);
                Date d = core.convertStringToDate(_date);
                if (tm.containsKey(arbitro)){
                    if (tm.get(arbitro).contains(d)){
                        printAlert("Trovato doppione: " + arbitro + " " + _date);
                        _break = true;
                        break;
                    }
                } else
                    tm.put(arbitro, new TreeSet<Date>());
                tm.get(arbitro).add(d);
            }
            if (!_break)
                core.saveEccezioni(tm);
        } catch (ParseException ex) {
            printAlert("Non posso aggiungere la data pechè non nel formato gg/mm/yyyy: "
                    + arbitro + " " + _date);
        }
    }
*/
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

    void invokeTestArbitri(Container parent) {
        String name = Swing.getFile(parent, "Selezionare il file excel del rimborso",
                fnfeXLS, new File(core.getCurDir()));
        if (name!=null)
            core.testArbitri(new File(name));
    }    

    void saveOption(Vector dataVector) {
        float[] values = new float[4];        
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

    void saveDesignazioni(Object selectedItem, Vector dataVector) {
        core.saveDesignazioni(selectedItem, dataVector);
    }

    void testPDF() {
        core.testPDF();
    }

    void testXLS() {
        core.testXLS();
    }

    String getNameTableAnagrafica(){
        return core.getTABLE_ANAGRAFICA();
    }
    /*
    String getNameTableEcccezioni(){
        return core.getTABLE_ECCEZIONI();
    }
    */
    String getNameTableCarta(){
        return core.getTABLE_CARTA();
    }

    String getNameTableOptions(){
        return core.getTABLE_OPTIONS();
    }

    String getNameComboArbitri(){
        return core.getCOMBO_ARBITRI();
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