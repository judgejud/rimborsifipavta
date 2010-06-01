package it.fipavpuglia.taranto.lm.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import it.fipavpuglia.taranto.lm.gui.events.*;

import org.lp.myUtils.Util;
import org.lp.myUtils.lang.Lang;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.jdom.JDOMException;
/**
 *
 * @author luca
 */
public class Kernel {    
    private static Kernel core = null;
    private final String TABLE_ANAGRAFICA="Anagrafica";
    private final String TABLE_DESIGNAZIONI="Designazioni";
    private final String TABLE_CARTA="Carta";
    private final String TABLE_OPZIONI="Opzioni";
    private final String COMBO_ARBITRI="Arbitri";
    private final String COMBO_LOCALITA="Localita";
    private final String DESIGNAZIONI_DIR = "designazioni" + File.separator;
    //private final String TABLE_ECCEZIONI="Eccezioni";
    private final File XML_CARTA = new File("cartapolimetrica.xml");
    private final File XML_ANAGRAFICA = new File("anagrafica.xml");
    //private final File XML_ECCEZIONI = new File("eccezioni.xml");
    private final File XML_OPZIONI = new File("opzioni.xml");
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
    private float costoKM, costoSingolaP, costoDoppiaP, costoReferto, limiteKm;
    private String curDir;
    private List listenerTextPane = new ArrayList();
    private List listenerFrame = new ArrayList();

    private TreeMap<String, Anagrafica> tmAnagrafica;
    private TreeMap<Carta, String> tmCarta;
    private TreeMap<String, TreeSet<Date>> tmEccezioni;
    private HashMap<String, ArrayList<Vector>> calcoli;

    /**Costruttore privato*/
    private Kernel(){
        curDir = Lang.getFileUserDir().toURI().getPath();
    }

    public static Kernel getInstance(){
        if (core==null)
            core = new Kernel();
        return core;
    }

    void loadXML() {
        Xml load = new Xml();
        try {
            fireNewFrameEvent(TABLE_ANAGRAFICA, load.initializeReaderAnagrafica(XML_ANAGRAFICA));
            tmAnagrafica = load.getMapAnagrafica();
            fireNewFrameEvent(COMBO_ARBITRI, tmAnagrafica.keySet().toArray());
            fireNewFrameEvent(TABLE_CARTA, load.initializeReaderCarta(XML_CARTA));
            fireNewFrameEvent(COMBO_LOCALITA, load.getSetCarta().toArray());
            tmCarta = load.getMapCarta();
            ArrayList<Object> opt = load.initializeReaderOpzioni(XML_OPZIONI);
            if (opt!=null){
                costoKM = ((Float)opt.get(0)).floatValue();
                costoSingolaP = ((Float)opt.get(1)).floatValue();
                costoDoppiaP = ((Float)opt.get(2)).floatValue();
                costoReferto = ((Float)opt.get(3)).floatValue();
                limiteKm = ((Float)opt.get(4)).floatValue();
                fireNewFrameEvent(TABLE_OPZIONI, opt.toArray());
            }
            //fireNewFrameEvent(TABLE_ECCEZIONI,temp.initializeReaderEccezioni(XML_ECCEZIONI));
            //tmEccezioni = temp.getMapEccezioni();
        } catch (JDOMException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();        
        }
    }

    public void createNewPolymetric(File file) {
        try {
            Workbook workBook = WorkbookFactory.create(new FileInputStream(file));
            Sheet sheet = workBook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            ArrayList<String> alsPaesi = new ArrayList();
            int start = 1;
            Xml xml = new Xml();
            xml.initializeWriterCarta();
            tmCarta = new TreeMap<Carta, String>();
            while (rows.hasNext()){
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                if (row.getRowNum()==0){
                    while (cells.hasNext()){
                        Cell cell = cells.next();
                        if (cell.getColumnIndex()>0){
                            String value = cell.getStringCellValue();
                            if (value.equals(""))
                                break;
                            else
                                alsPaesi.add(value);
                        }
                    }
                } else {
                    String paese = null;
                    while (cells.hasNext()){
                        Cell cell = cells.next();
                        int count = cell.getColumnIndex();
                        if (count == 0)
                            paese = cell.getStringCellValue();
                        else if (count > start){
                            String km = String.valueOf((int)cell.getNumericCellValue());
                            String paese2 = alsPaesi.get(count-start);
                            tmCarta.put(new Carta(paese, paese2), km);
                            if (paese.compareTo(paese2)<0)
                                xml.addItemCarta(paese, paese2, km);
                            else
                                xml.addItemCarta(paese2, paese, km);
                        }
                    }
                    start++;
                    alsPaesi.remove(0);
                }
            }
            xml.writeCarta(XML_CARTA);
        } catch (InvalidFormatException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void testArbitri(File file) {
        try {            
            Workbook workBook = WorkbookFactory.create(new FileInputStream(file));
            boolean test = true;
            for (int i=0; i<workBook.getNumberOfSheets()-1; i++){
                Sheet sheet = workBook.getSheetAt(i);
                String arbitro = sheet.getSheetName().toUpperCase();
                String residenza = null;
                if (!tmAnagrafica.containsKey(arbitro)){
                    test = false;
                    residenza = sheet.getRow(5).getCell(5).getStringCellValue();
                    printAlert("Arbitro assente nell'xml: " + arbitro + " - " + residenza);
                }
            }
            if (test)
                printOk("test arbitri " + file.getName() + " ok");
        } catch (InvalidFormatException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void analyzeRimborsi(File file, boolean flag_test){
        try {
            long start = System.currentTimeMillis();
            Workbook workBook = WorkbookFactory.create(new FileInputStream(file));
            if (flag_test){
                boolean test = true;
                for (int i=0; i<workBook.getNumberOfSheets(); i++){
                    boolean temp = testSheet(workBook.getSheetAt(i));
                    if (test & !temp)
                        test = false;
                }
                if (test)
                    printOk("Le località in " + file.getName() +
                            " sono presenti nell'xml, puoi avviare la scrittura rimborsi.");
            } else {
                for (int i=0; i<workBook.getNumberOfSheets()-1; i++)
                    analyzeSheet(workBook.getSheetAt(i));                                
            }
            if (!flag_test){
                // Write the output to a file
                FileOutputStream fileOut = new FileOutputStream(file);
                workBook.write(fileOut);
                fileOut.close();
                long diff = System.currentTimeMillis() -start;
                printOk("Aggiornamento " +file.getName() + " terminato in " +
                        diff + " millisecondi");
            }        
        } catch (InvalidFormatException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        }
    }    

    private void analyzeSheet(Sheet sheet) throws NumberFormatException, IOException{
        String arbitro = sheet.getSheetName().toUpperCase();
        if (tmAnagrafica.containsKey(arbitro)){
            String residenza = tmAnagrafica.get(arbitro).getCity_card();
            Iterator<Row> rows = sheet.rowIterator();
            Date oldData = new Date(0);
            Iterator<Date> it = null;
            Date d = null;
            if (tmEccezioni!= null && tmEccezioni.containsKey(arbitro)){
                 it = tmEccezioni.get(arbitro).iterator();
                 d = it.next();
            }
            while (rows.hasNext()){
                Row row = (Row) rows.next ();
                // display row number in the console.
                if (row.getRowNum() >= 11){
                    Cell cLoc = row.getCell(18);
                    String localita = cLoc.getStringCellValue();
                    if (localita.equals(""))
                        break;
                    else {                        
                        int km = 0;
                        if (!residenza.toUpperCase().equalsIgnoreCase(localita.toUpperCase())) {
                            Cell cData = row.getCell(2);
                            Date actualDate = cData.getDateCellValue();
                            boolean flag = true;
                            if (it!=null && d.equals(actualDate)){
                                flag = false;
                                if (it.hasNext())
                                    d = it.next();
                            }
                            if (flag && !oldData.equals(actualDate)){
                                Carta c = new Carta(localita, residenza);
                                km = Integer.parseInt(tmCarta.get(c)) * 2;
                            } //end if data
                            oldData = actualDate;
                        }//end if
                        CellStyle style = row.getCell(24).getCellStyle();
                        int type = row.getCell(24).getCellType();
                        Cell cKM = row.createCell(24);
                        cKM.setCellStyle(style);
                        cKM.setCellType(type);
                        cKM.setCellValue(km);
                    }
                }
            } //end while
        } else //end if residenza
            printAlert("Arbitro assente nell'xml: "+arbitro);
    }

    private boolean testSheet(Sheet sheet) {
        String arbitro = sheet.getSheetName().toUpperCase();
        boolean verify = true;
        if (tmAnagrafica.containsKey(arbitro)){
            String residenza = tmAnagrafica.get(arbitro).getCity_card();
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()){
                Row row = (Row) rows.next ();
                // display row number in the console.
                if (row.getRowNum() >= 11){
                    String localita = row.getCell(18).getStringCellValue();
                    if (localita.equals(""))
                        break;
                    else {
                        if (!residenza.toUpperCase().equalsIgnoreCase(localita.toUpperCase())) {
                                Carta c = new Carta(localita, residenza);
                                if (!tmCarta.containsKey(c)) {
                                    printAlert("XML Carta: manca la seguente coppia di paesi: " +
                                        residenza + " " + localita);
                                        verify = false;
                                }
                        }//end if
                    }
                }
            } //end while
        } //end if residenza
        return verify;
    }

    public void saveAnagrafica(TreeMap<String, Anagrafica> tm) {
        if (tm.size()>0){
            try {
                Xml save = new Xml();
                Iterator<String> it = tm.keySet().iterator();
                save.initializeWriterAnagrafica();
                while (it.hasNext()) {
                    String key = it.next();
                    Anagrafica value = tm.get(key);
                    save.addItemAnagrafica(key, value);
                }
                save.writeAnagrafica(XML_ANAGRAFICA);
                printOk("XML anagrafica aggiornato");
                tmAnagrafica = tm;
                fireNewFrameEvent(COMBO_ARBITRI, tmAnagrafica.keySet().toArray());
            } catch (IOException ex) {
                printError(ex.getMessage());
                ex.printStackTrace();
            }
        } else
            printAlert("non ho elementi da salvare nell'xml anagrafica");
    }

    public void saveCarta(TreeMap<Carta, String> tm) {
        if (tm.size()>0){
            try {
                Xml save = new Xml();
                Iterator<Carta> it = tm.keySet().iterator();
                save.initializeWriterCarta();
                while (it.hasNext()) {
                    Carta key = it.next();
                    String value = tm.get(key);
                    save.addItemCarta(key.getPartenza(), key.getArrivo(), value);
                }
                save.writeCarta(XML_CARTA);
                printOk("XML Carta polimetrica aggiornata");
                tmCarta = tm;
            } catch (IOException ex) {
                printError(ex.getMessage());
                ex.printStackTrace();
            }
        } else
            printAlert("non ho elementi da salvare nell'xml Carta polimetrica");
    }
/*
    public void saveEccezioni(TreeMap<String, TreeSet<Date>> tm) {
        if (tm.size()>0){
            try {
                Xml save = new Xml();
                save.initializeWriterEccezioni();
                Iterator<String> it = tm.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    Iterator<Date> value = tm.get(key).iterator();
                    while (value.hasNext())
                        save.addItemEccezione(key, convertDateToString(value.next()));
                }
                save.writeEccezioni(XML_ECCEZIONI);
                printOk("XML eccezioni aggiornato");
                tmEccezioni = tm;
            } catch (IOException ex) {
                printError(ex.getMessage());
                ex.printStackTrace();
            } catch (ParseException ex) {
                printError(ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            XML_ECCEZIONI.delete();
            printOk(XML_ECCEZIONI.getName() + " cancellato");
            tmEccezioni = null;
        }
    }
*/
    public void backupXml(String backup) {
        ArrayList<File> files = new ArrayList<File>();
        if (XML_ANAGRAFICA.exists())
            files.add(XML_ANAGRAFICA);
        if (XML_CARTA.exists())
            files.add(XML_CARTA);
        //if (XML_ECCEZIONI.exists())
            //files.add(XML_ECCEZIONI);
        if (XML_OPZIONI.exists())
            files.add(XML_OPZIONI);
        if (files.size()>0){
            if (!backup.substring(backup.length()-4).toLowerCase().equalsIgnoreCase(".zip"))
                backup += ".zip";
            File f = new File(backup);
            try {
                Util.createZip(files, f);
                printOk("backup effettuato: " + f.getName());
            } catch (IOException ex) {
                printError(ex.getMessage());
                ex.printStackTrace();
            }
        } else
            printAlert("Non posso fare il backup degli xml in quanto non esiste nessun xml");
    }

    public void saveOptions(float[] values){
        Xml save = new Xml();        
        try {
            save.initializeWriterOptions();
            save.addItemOption(values);
            save.writeOpzioni(XML_OPZIONI);
            printOk("Opzioni salvate");
            costoKM = values[0];
            costoSingolaP = values[1];
            costoDoppiaP = values[2];
            costoReferto = values[3];
            limiteKm = values[4];
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveDesignazioni(Object name, Vector dataVector) {
        Xml save = new Xml();
        try {
            File file = new File(curDir + DESIGNAZIONI_DIR);
            if (!file.exists())
                file.mkdir();
            File xml = new File(file, name.toString()+".xml");
            save.initializeWriterDesignazioni();
            for (int i=0; i<dataVector.size(); i++){
                Vector temp = (Vector)dataVector.elementAt(i);
                String data = temp.elementAt(0).toString();
                String designazione = temp.elementAt(1).toString().toUpperCase();
                String localita = temp.elementAt(2).toString().toUpperCase();
                String concentramento = temp.elementAt(3).toString();
                String macchina = temp.elementAt(4).toString();
                String spesedoc = temp.elementAt(5).toString();
                String referto = temp.elementAt(6).toString();
                String spesenondoc = temp.elementAt(7).toString();
                save.addItemDesignazione(data, designazione, localita, concentramento, macchina,
                        spesedoc, referto, spesenondoc);
            }
            save.writeDesignazioni(xml);
            printOk(xml.getName() + " salvato");
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void testXLS() {

    }

    public void createPDF(String periodo) {
        Iterator<String> it = tmAnagrafica.keySet().iterator();
        try {
            Pdf pdf = new Pdf("test", "test", false);
            pdf.creaIntestazione();
            String nome = "MIGNOGNA";
            //String nome = it.next();
            pdf.printAnagrafica(tmAnagrafica.get(nome),periodo);
            pdf.printPartite(calcoli.get(nome));
            pdf.printChiusura();
            pdf.close();
            printOk("PDF creato correttamente");
        } catch (BadElementException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (DocumentException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public Date convertStringToDate(String _date) throws ParseException{
        return sdf.parse(_date);
    }

    String convertDateToString(Date d) throws ParseException{
        return sdf.format(d);
    }
    
    public void testDestinations(File file) {
        analyzeRimborsi(file, true);
    }

    public void writeRimborsi(File file){                   
        analyzeRimborsi(file, false);
    }
    
    public void changeDesignazioni(Object name) {
        File file = new File(curDir + DESIGNAZIONI_DIR + name.toString()+".xml");
        Xml load = new Xml();
        try {
            fireNewFrameEvent(TABLE_DESIGNAZIONI, load.initializeReaderDesignazioni(file));
        } catch (JDOMException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void fireCalcoli(Date begin, Date end) {
        //tmAnagrafica.keySet().iterator();
        calcoli = new HashMap<String, ArrayList<Vector>>();
        String name = "MIGNOGNA";
        File file = new File(curDir + DESIGNAZIONI_DIR + name.toString()+".xml");
        Xml load = new Xml();
        try {
            ArrayList<Vector> array = new ArrayList<Vector>();
            String residenza = tmAnagrafica.get(name).getCity_card();
            ArrayList<Object[]> dati = load.initializeReaderDesignazioni(file);
            int tot_km = 0;
            float tot_rimb = 0, tot_spesedoc = 0, tot_spesenon = 0, tot_partite = 0;
            Date oldDate = null;
            try {
                oldDate = convertStringToDate("01/01/2000");
            } catch (ParseException ex) {}
            for (int i=0; i<dati.size(); i++){
                Object partita[] = dati.get(i);
                Date data_design = null;
                try {
                    data_design = convertStringToDate(partita[0].toString());
                } catch (ParseException ex) {}
                if (data_design.getTime() >= begin.getTime()){
                    if (data_design.getTime() <= end.getTime()){
                        Vector v = new Vector();
                        int km = 0;
                        float rimb_km = 0, totale_riga = 0;
                        String localita = partita[2].toString();
                        boolean car = Boolean.parseBoolean(partita[4].toString());
                        if (car && !residenza.equalsIgnoreCase(localita) &&
                                !oldDate.equals(data_design))
                            km = Integer.parseInt(tmCarta.get(new Carta(residenza, localita))) * 2;
                        if (km>0){
                            rimb_km = km * costoKM;
                            tot_km += km;
                            tot_rimb += rimb_km;
                        }
                        float spesedoc = Float.parseFloat(partita[5].toString());
                        tot_spesedoc += spesedoc;
                        boolean referto = Boolean.parseBoolean(partita[6].toString());
                        float spesenond = Float.parseFloat(partita[7].toString());
                        if (referto)
                            spesenond += costoReferto;
                        tot_spesenon += spesenond;
                        //Determino il rimborso forfettario
                        float rimb_partita = 0;
                        boolean concentram = Boolean.parseBoolean(partita[3].toString());
                        if (concentram && !oldDate.equals(data_design))
                            rimb_partita = costoDoppiaP;
                        else if (!concentram && km>limiteKm)
                            rimb_partita = costoDoppiaP;
                        else if (!concentram)
                            rimb_partita = costoSingolaP;
                        tot_partite += rimb_partita;
                        totale_riga = rimb_km + spesedoc + spesenond + rimb_partita;
                        oldDate = data_design;
                        //TODO
                        v.add(partita[0]); //data
                        v.add(partita[1]); //designazione
                        v.add(localita);
                        v.add(km);
                        v.add(rimb_km);
                        v.add(spesedoc);
                        v.add(spesenond);
                        v.add(rimb_partita);
                        v.add(totale_riga);
                        array.add(v);
                    } else
                        break;
                }
            } //end for
            //calcolo TOTALI
            float totale = tot_rimb + tot_spesedoc + tot_spesenon + tot_partite;
            Vector v = new Vector();
            v.add(tot_km);
            v.add(tot_rimb);
            v.add(tot_spesedoc);
            v.add(tot_spesenon);
            v.add(tot_partite);
            v.add(totale);
            array.add(v);
            calcoli.put(name, array);

            printOk("Calcoli effettuati, puoi procedere con la stampa");
        } catch (JDOMException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            printError(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String getCurDir() {
        return curDir;
    }

    public String getTABLE_ANAGRAFICA() {
        return TABLE_ANAGRAFICA;
    }

    public String getTABLE_CARTA() {
        return TABLE_CARTA;
    }

    public String getTABLE_DESIGNAZIONI() {
        return TABLE_DESIGNAZIONI;
    }

    public String getTABLE_OPTIONS() {
        return TABLE_OPZIONI;
    }

    public String getCOMBO_ARBITRI(){
        return COMBO_ARBITRI;
    }

    public String getCOMBO_LOCALITA() {
        return COMBO_LOCALITA;
    }
    
    private void printAlert(String print){
        fireNewTextPaneEvent(print, MyTextPaneEvent.ALERT);
    }

    private void printError(String print){
        fireNewTextPaneEvent(print, MyTextPaneEvent.ERROR);
    }

    private void printOk(String print){
        fireNewTextPaneEvent(print, MyTextPaneEvent.OK);
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
    // This methods allows classes to register for MyEvents
    public synchronized void addMyFrameEventListener(MyFrameEventListener listener) {
        listenerFrame.add(listener);
    }
    // This methods allows classes to unregister for MyEvents
    public synchronized void removeMyFrameEventListener(MyFrameEventListener listener) {
        listenerFrame.remove(listener);
    }

    private synchronized void fireNewFrameEvent(String _name, ArrayList<Object[]> _array) {
        MyFrameEvent event = new MyFrameEvent(this, _name, _array);
        Iterator listeners = listenerFrame.iterator();
        while(listeners.hasNext()) {
            MyFrameEventListener myel = (MyFrameEventListener)listeners.next();
            myel.objReceived(event);
        }
    }

    private synchronized void fireNewFrameEvent(String _name, Object[] _array) {
        MyFrameEvent event = new MyFrameEvent(this, _name, _array);
        Iterator listeners = listenerFrame.iterator();
        while(listeners.hasNext()) {
            MyFrameEventListener myel = (MyFrameEventListener)listeners.next();
            myel.objReceived(event);
        }
    }    
}