package it.fipavpuglia.taranto.lm.core;
//IMPORT JAVA
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**Scrive e legge su/da file xml le regole di destinazione
 *
 * @author luca
 */
class Xml {
    //VARIABLES PRIVATE FINAL    
    private final String TAG_ROOT = "ROOT";
    private final String TAG_ITEM = "ITEM";
    private final String TAG_ANAGRAFICA_CODICE = "CODICE";
    private final String TAG_ANAGRAFICA_PAESE = "PAESE";
    private final String TAG_ANAGRAFICA_NOME = "NOME";
    private final String TAG_ANAGRAFICA_RUOLO = "RUOLO";
    private final String TAG_ANAGRAFICA_DATAN = "DATA_NASCITA";
    private final String TAG_ANAGRAFICA_LUOGON = "LUOGO_NASCITA";
    private final String TAG_ANAGRAFICA_SESSO = "SESSO";
    private final String TAG_ANAGRAFICA_CODFIS = "CODICE_FISCALE";
    private final String TAG_ANAGRAFICA_CITTA = "RESIDENZA";
    private final String TAG_ANAGRAFICA_INDIRIZZO = "INDIRIZZO";
    private final String TAG_ANAGRAFICA_CAP = "CAP";
    private final String TAG_CARTA_PAESE1 = "PAESE1";
    private final String TAG_CARTA_PAESE2 = "PAESE2";
    private final String TAG_CARTA_KM = "KM";
    private final String TAG_ECCEZIONI_NOME = "NOME";
    private final String TAG_ECCEZIONI_DATA = "DATA";
    private final String TAG_OPTION_KM = "COSTO_KM";
    private final String TAG_OPTION_SINGLE = "COSTO_SINGLE";
    private final String TAG_OPTION_DUAL = "COSTO_DUAL";
    private final String TAG_OPTION_REFERT = "COSTO_REFERT";
    //VARIABLES PRIVATE
    private Element root;
    private org.jdom.Document jdomCarta, jdomAnagrafica, jdomEccezioni, jdomOptions;
    private TreeMap<String, Anagrafica> tmAnagrafica = null;
    private TreeMap<Carta, String> tmCarta = null;
    private TreeMap<String, TreeSet<Date>> tmEccezioni = null;
    /**inizializza il documento Carta per la scrittura */
    void initializeWriterCarta(){
        root = new Element(TAG_ROOT);
        jdomCarta = new Document(root);
    }
    /**inizializza il documento Arbitri per la scrittura */
    void initializeWriterAnagrafica(){
        root = new Element(TAG_ROOT);
        jdomAnagrafica = new Document(root);
    }
    /**inizializza il documento Eccezioni per la scrittura */
    void initializeWriterEccezioni(){
        root = new Element(TAG_ROOT);
        jdomEccezioni = new Document(root);
    }
    /**inizializza il documento Eccezioni per la scrittura */
    void initializeWriterOptions() {
        root = new Element(TAG_ROOT);
        jdomOptions = new Document(root);
    }
    /**
     *
     * @param _paese1
     * @param _paese2
     * @param _km
     */
    void addItemCarta(String _paese1, String _paese2, String _km){
        Element item = new Element(TAG_ITEM);
        Element paese1 = new Element(TAG_CARTA_PAESE1);
        paese1.setText(_paese1);
        Element paese2 = new Element(TAG_CARTA_PAESE2);
        paese2.setText(_paese2);
        Element km = new Element(TAG_CARTA_KM);
        km.setText(_km);
        item.addContent(paese1);
        item.addContent(paese2);
        item.addContent(km);
        root.addContent(item);
    }
    /**
     *
     * @param key
     * @param value
     */
    void addItemAnagrafica(String key, Anagrafica value) {
        Element item = new Element(TAG_ITEM);
        Element codice = new Element(TAG_ANAGRAFICA_CODICE);
        codice.setText(key);
        Element paese = new Element(TAG_ANAGRAFICA_PAESE);
        paese.setText(value.getCity_card());
        Element ruolo = new Element(TAG_ANAGRAFICA_RUOLO);
        ruolo.setText(value.getRole());
        Element nome = new Element(TAG_ANAGRAFICA_NOME);
        nome.setText(value.getSurname_name());
        Element data = new Element(TAG_ANAGRAFICA_DATAN);
        data.setText(value.getDate_born());
        Element luogo = new Element(TAG_ANAGRAFICA_LUOGON);
        luogo.setText(value.getCity_born());
        Element sesso = new Element(TAG_ANAGRAFICA_SESSO);
        sesso.setText(value.getSex());
        Element codfis = new Element(TAG_ANAGRAFICA_CODFIS);
        codfis.setText(value.getFiscal_code());
        Element città = new Element(TAG_ANAGRAFICA_CITTA);
        città.setText(value.getCity_residence());
        Element indirizzo = new Element(TAG_ANAGRAFICA_INDIRIZZO);
        indirizzo.setText(value.getAddress());
        Element cap = new Element(TAG_ANAGRAFICA_CAP);
        cap.setText(value.getCap());
        item.addContent(codice);
        item.addContent(paese);
        item.addContent(nome);
        item.addContent(ruolo);
        item.addContent(data);
        item.addContent(luogo);
        item.addContent(sesso);
        item.addContent(codfis);
        item.addContent(città);
        item.addContent(indirizzo);
        item.addContent(cap);
        root.addContent(item);
    }

    void addItemEccezione(String key, String value) {
        Element item = new Element(TAG_ITEM);
        Element nome = new Element(TAG_ECCEZIONI_NOME);
        nome.setText(key);
        Element data = new Element(TAG_ECCEZIONI_DATA);
        data.setText(value);
        item.addContent(nome);
        item.addContent(data);
        root.addContent(item);
    }

    void addItemOption(float[] costo) {
        Element item = new Element(TAG_ITEM);
        Element km = new Element(TAG_OPTION_KM);
        km.setText(String.valueOf(costo[0]));
        Element single = new Element(TAG_OPTION_SINGLE);
        single.setText(String.valueOf(costo[1]));
        Element dual = new Element(TAG_OPTION_DUAL);
        dual.setText(String.valueOf(costo[2]));
        Element refert = new Element(TAG_OPTION_REFERT);
        refert.setText(String.valueOf(costo[3]));
        item.addContent(km);
        item.addContent(single);
        item.addContent(dual);
        item.addContent(refert);
        root.addContent(item);
    }
    /**Scrive l'xml carta
     *
     * @throws IOException
     */
    void writeCarta(File xml) throws IOException{
        write().output(jdomCarta, new FileOutputStream(xml));
    }
    /**Scrive l'xml arbitri
     *
     * @throws IOException
     */
    void writeAnagrafica(File xml) throws IOException{
        write().output(jdomAnagrafica, new FileOutputStream(xml));
    }
    /**Scrive l'xml eccezioni
     *
     * @throws IOException
     */
    void writeEccezioni(File xml) throws IOException{
        write().output(jdomEccezioni, new FileOutputStream(xml));
    }

    void writeOpzioni(File xml) throws IOException{
        write().output(jdomOptions, new FileOutputStream(xml));
    }
    /**
     *
     * @return
     */
    private XMLOutputter write(){
        //Creazione dell'oggetto XMLOutputter
        XMLOutputter outputter = new XMLOutputter();
        //Imposto il formato dell'outputter come "bel formato"
        outputter.setFormat(Format.getPrettyFormat());
        return outputter;
    }
    /**
     *
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    ArrayList<String[]> initializeReaderAnagrafica(File xml) throws JDOMException,
                IOException{
        ArrayList<String[]> array = null;
        if (xml.exists()){
            //Creo un SAXBuilder e con esso costruisco un document
            jdomAnagrafica = new SAXBuilder().build(xml);
            int size = jdomAnagrafica.getRootElement().getChildren().size();
            if (size>0){
                tmAnagrafica = new TreeMap<String, Anagrafica>();
                array = new ArrayList<String[]>();
                Iterator iterator = jdomAnagrafica.getRootElement().getChildren().iterator();
                while(iterator.hasNext()){
                    Element role = (Element)iterator.next();
                    String codice = getItemXml(role, TAG_ANAGRAFICA_CODICE).toUpperCase();
                    String paese = getItemXml(role, TAG_ANAGRAFICA_PAESE).toUpperCase();
                    String ruolo = getItemXml(role, TAG_ANAGRAFICA_RUOLO);
                    String nome = getItemXml(role, TAG_ANAGRAFICA_NOME);
                    String luogo = getItemXml(role, TAG_ANAGRAFICA_LUOGON);
                    String data = getItemXml(role, TAG_ANAGRAFICA_DATAN);
                    String sesso = getItemXml(role, TAG_ANAGRAFICA_SESSO);
                    String codfis = getItemXml(role, TAG_ANAGRAFICA_CODFIS);
                    String resid = getItemXml(role, TAG_ANAGRAFICA_CITTA);
                    String indirizzo = getItemXml(role, TAG_ANAGRAFICA_INDIRIZZO);
                    String cap = getItemXml(role, TAG_ANAGRAFICA_CAP);
                    String[] temp = {codice, paese, ruolo, nome, sesso, data, luogo, codfis,
                                    resid, indirizzo, cap};
                    array.add(temp);
                    Anagrafica a = new Anagrafica(nome, luogo, data, codfis, resid, indirizzo, 
                            cap, ruolo, sesso, paese);
                    tmAnagrafica.put(codice, a);
                }                
            }
        }
        return array;
    }

    private String getItemXml(Element elem, String tag){
        String value = "";
        try{
            value = elem.getChild(tag).getText();
        } catch (NullPointerException npe){}
        return value;
    }

    /**
     *
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    ArrayList<String[]> initializeReaderCarta(File xml) throws JDOMException, IOException{
        ArrayList<String[]> array = null;
        if (xml.exists()){
            //Creo un SAXBuilder e con esso costruisco un document
            jdomCarta = new SAXBuilder().build(xml);
            int size = jdomCarta.getRootElement().getChildren().size();
            if (size>0){
                array = new ArrayList<String[]>();
                tmCarta = new TreeMap<Carta, String>();
                Iterator iterator = jdomCarta.getRootElement().getChildren().iterator();
                while(iterator.hasNext()){
                    Element role = (Element)iterator.next();
                    String paese1 = role.getChild(TAG_CARTA_PAESE1).getText().toUpperCase();
                    String paese2 = role.getChild(TAG_CARTA_PAESE2).getText().toUpperCase();
                    String km = role.getChild(TAG_CARTA_KM).getText().toUpperCase();
                    String[] temp = {paese1, paese2, km};
                    array.add(temp);
                    tmCarta.put(new Carta(paese1, paese2), km);
                }
            }
        }
        return array;
    }
    /**
     *
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    ArrayList<String[]> initializeReaderEccezioni(File xml) throws JDOMException, IOException,
            ParseException{
        ArrayList<String[]> array = null;
        if (xml.exists()){
            //Creo un SAXBuilder e con esso costruisco un document
            jdomEccezioni = new SAXBuilder().build(xml);
            int size = jdomEccezioni.getRootElement().getChildren().size();
            if (size>0){
                array = new ArrayList<String[]>();
                tmEccezioni = new TreeMap<String, TreeSet<Date>>();
                Iterator iterator = jdomEccezioni.getRootElement().getChildren().iterator();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
                while(iterator.hasNext()){
                    Element role = (Element)iterator.next();
                    String arbitro = role.getChild(TAG_ECCEZIONI_NOME).getText().toUpperCase();
                    String data = role.getChild(TAG_ECCEZIONI_DATA).getText();
                    String[] temp = {arbitro, data};
                    array.add(temp);
                    if (!tmEccezioni.containsKey(arbitro))
                        tmEccezioni.put(arbitro, new TreeSet<Date>());
                    tmEccezioni.get(arbitro).add(sdf.parse(data));
                }
            }
        }
        return array;
    }
    /**
     *
     * @return
     */
    TreeMap<String, Anagrafica> getMapAnagrafica() {
        return tmAnagrafica;
    }
    /**
     *
     * @return
     */
    TreeMap<Carta, String> getMapCarta() {
        return tmCarta;
    }
    /**
     *
     * @return
     */
    TreeMap<String, TreeSet<Date>> getMapEccezioni() {
        return tmEccezioni;
    }
}