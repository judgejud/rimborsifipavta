package it.fipavpuglia.taranto.lm.core;
//IMPORT JAVA
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
//IMPORT JDOM
import java.util.TreeSet;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
/**Scrive e legge su/da file xml le 
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
    //private final String TAG_ECCEZIONI_NOME = "NOME";
    //private final String TAG_ECCEZIONI_DATA = "DATA";
    private final String TAG_OPTION_KM = "COSTO_KM";
    private final String TAG_OPTION_SINGLE = "COSTO_SINGLE";
    private final String TAG_OPTION_DUAL = "COSTO_DUAL";
    private final String TAG_OPTION_REFERT = "COSTO_REFERT";
    private final String TAG_DESIGNAZIONI_DATA = "DATA";
    private final String TAG_DESIGNAZIONI_LOCALITA = "LOCALITA";
    private final String TAG_DESIGNAZIONI_DESIGNAZIONE = "DESIGNAZIONE";
    private final String TAG_DESIGNAZIONI_MACCHINA = "MACCHINA";
    private final String TAG_DESIGNAZIONI_REFERTO = "REFERTO";
    private final String TAG_DESIGNAZIONI_CONCENTRAMENTO = "CONCENTRAMENTO";
    private final String TAG_DESIGNAZIONI_SPESEDOC = "SPESE_DOCUMENTATE";
    private final String TAG_DESIGNAZIONI_SPESENON = "SPESE_NON_DOCUM";

    //VARIABLES PRIVATE
    private Element root;
    private Document jdomCarta, jdomAnagrafica, jdomDesignazioni, jdomOptions;
    //private Document jdomEccezioni;
    private TreeMap<String, Anagrafica> tmAnagrafica = null;
    private TreeMap<Carta, String> tmCarta = null;
    private TreeSet<String> tsCarta;
    //private TreeMap<String, TreeSet<Date>> tmEccezioni = null;
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
    /*
    void initializeWriterEccezioni(){
        root = new Element(TAG_ROOT);
        jdomEccezioni = new Document(root);
    }
    /**inizializza il documento Eccezioni per la scrittura */
    void initializeWriterOptions() {
        root = new Element(TAG_ROOT);
        jdomOptions = new Document(root);
    }    
    /**inizializza il documento Eccezioni per la scrittura */
    void initializeWriterDesignazioni() {
        root = new Element(TAG_ROOT);
        jdomDesignazioni = new Document(root);
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
    /*
    void addItemEccezione(String key, String value) {
        Element item = new Element(TAG_ITEM);
        Element nome = new Element(TAG_ECCEZIONI_NOME);
        nome.setText(key);
        Element data = new Element(TAG_ECCEZIONI_DATA);
        data.setText(value);
        item.addContent(nome);
        item.addContent(data);
        root.addContent(item);
    }*/

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

    void addItemDesignazione(String _data, String _designazione, String _localita,
            String _concentramento, String _macchina, String _spesedoc, String _referto,
            String _spesenondoc) {
        //TODO
        Element item = new Element(TAG_ITEM);
        Element data = new Element(TAG_DESIGNAZIONI_DATA);
        data.setText(_data);
        Element designazione = new Element(TAG_DESIGNAZIONI_DESIGNAZIONE);
        designazione.setText(_designazione);
        Element localita = new Element(TAG_DESIGNAZIONI_LOCALITA);
        localita.setText(_localita);
        Element concentramento = new Element(TAG_DESIGNAZIONI_CONCENTRAMENTO);
        concentramento.setText(_concentramento);
        Element macchina = new Element(TAG_DESIGNAZIONI_MACCHINA);
        macchina.setText(_macchina);
        Element spesedoc = new Element(TAG_DESIGNAZIONI_SPESEDOC);
        spesedoc.setText(_spesedoc);
        Element referto = new Element(TAG_DESIGNAZIONI_REFERTO);
        referto.setText(_referto);
        Element spesenon = new Element(TAG_DESIGNAZIONI_SPESENON);
        spesenon.setText(_spesenondoc);

        item.addContent(data);
        item.addContent(designazione);
        item.addContent(localita);
        item.addContent(concentramento);
        item.addContent(macchina);
        item.addContent(spesedoc);
        item.addContent(referto);
        item.addContent(spesenon);
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
    /*
    void writeEccezioni(File xml) throws IOException{
        write().output(jdomEccezioni, new FileOutputStream(xml));
    }*/

    void writeOpzioni(File xml) throws IOException{
        write().output(jdomOptions, new FileOutputStream(xml));
    }

    void writeDesignazioni(File xml) throws IOException{
        write().output(jdomDesignazioni, new FileOutputStream(xml));
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
    ArrayList<Object[]> initializeReaderAnagrafica(File xml) throws JDOMException,
                IOException{
        ArrayList<Object[]> array = null;
        if (xml.exists()){
            //Creo un SAXBuilder e con esso costruisco un document
            jdomAnagrafica = new SAXBuilder().build(xml);
            int size = jdomAnagrafica.getRootElement().getChildren().size();
            if (size>0){
                tmAnagrafica = new TreeMap<String, Anagrafica>();
                array = new ArrayList<Object[]>();
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
    ArrayList<Object[]> initializeReaderCarta(File xml) throws JDOMException, IOException{
        ArrayList<Object[]> array = null;
        if (xml.exists()){
            //Creo un SAXBuilder e con esso costruisco un document
            jdomCarta = new SAXBuilder().build(xml);
            int size = jdomCarta.getRootElement().getChildren().size();
            if (size>0){
                array = new ArrayList<Object[]>();
                tmCarta = new TreeMap<Carta, String>();
                tsCarta = new TreeSet<String>();
                Iterator iterator = jdomCarta.getRootElement().getChildren().iterator();
                while(iterator.hasNext()){
                    Element role = (Element)iterator.next();
                    String paese1 = role.getChild(TAG_CARTA_PAESE1).getText().toUpperCase();
                    String paese2 = role.getChild(TAG_CARTA_PAESE2).getText().toUpperCase();
                    String km = role.getChild(TAG_CARTA_KM).getText().toUpperCase();
                    String[] temp = {paese1, paese2, km};
                    array.add(temp);
                    tmCarta.put(new Carta(paese1, paese2), km);
                    tsCarta.add(paese1);
                    tsCarta.add(paese2);
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
    /*
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
    }*/

    ArrayList<Object> initializeReaderOpzioni(File xml) throws JDOMException, IOException{
        ArrayList<Object> array = null;
        if (xml.exists()){
            //Creo un SAXBuilder e con esso costruisco un document
            jdomOptions = new SAXBuilder().build(xml);
            int size = jdomOptions.getRootElement().getChildren().size();
            if (size>0){
                array = new ArrayList<Object>();
                Iterator iterator = jdomOptions.getRootElement().getChildren().iterator();
                Element role = (Element)iterator.next();
                array.add(Float.valueOf(role.getChild(TAG_OPTION_KM).getText()));
                array.add(Float.valueOf(role.getChild(TAG_OPTION_SINGLE).getText()));
                array.add(Float.valueOf(role.getChild(TAG_OPTION_DUAL).getText()));
                array.add(Float.valueOf(role.getChild(TAG_OPTION_REFERT).getText()));
            }
        }
        return array;
    }

    ArrayList<Object[]> initializeReaderDesignazioni(File xml) throws JDOMException, IOException {
        ArrayList<Object[]> array = null;
        if (xml.exists()){
            //Creo un SAXBuilder e con esso costruisco un document
            jdomDesignazioni = new SAXBuilder().build(xml);
            int size = jdomDesignazioni.getRootElement().getChildren().size();
            if (size>0){
                array = new ArrayList<Object[]>();
                Iterator iterator = jdomDesignazioni.getRootElement().getChildren().iterator();
                while(iterator.hasNext()){
                    Element role = (Element)iterator.next();
                    Object data = role.getChild(TAG_DESIGNAZIONI_DATA).getText();
                    String design = role.getChild(TAG_DESIGNAZIONI_DESIGNAZIONE).getText()
                            .toUpperCase();
                    String city = role.getChild(TAG_DESIGNAZIONI_LOCALITA).getText().toUpperCase();
                    String conc = role.getChild(TAG_DESIGNAZIONI_CONCENTRAMENTO).getText();
                    String car = role.getChild(TAG_DESIGNAZIONI_MACCHINA).getText();
                    String cost1 = role.getChild(TAG_DESIGNAZIONI_SPESEDOC).getText();
                    String ref = role.getChild(TAG_DESIGNAZIONI_REFERTO).getText();
                    String cost2 = role.getChild(TAG_DESIGNAZIONI_SPESENON).getText();
                    Object[] temp = {data, design, city, Boolean.valueOf(conc), Boolean.valueOf(car),
                                        Float.valueOf(cost1), Boolean.valueOf(ref),
                                        Float.valueOf(cost2)};
                    array.add(temp);
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

    TreeSet<String> getSetCarta(){
        return tsCarta;
    }
    /**
     *
     * @return
     */
    /*
    TreeMap<String, TreeSet<Date>> getMapEccezioni() {
        return tmEccezioni;
    }
    */
}