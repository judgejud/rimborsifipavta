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
    private final String TAG_ARBITRO_NOME = "NOME";
    private final String TAG_ARBITRO_PAESE = "PAESE";    
    private final String TAG_CARTA_PAESE1 = "PAESE1";
    private final String TAG_CARTA_PAESE2 = "PAESE2";
    private final String TAG_CARTA_KM = "KM";
    private final String TAG_ECCEZIONI_NOME = "NOME";
    private final String TAG_ECCEZIONI_DATA = "DATA";
    //VARIABLES PRIVATE
    private Element root;
    //private org.w3c.dom.Document w3cEccezioni;
    private org.jdom.Document jdomCarta, jdomArbitri, jdomEccezioni;
    private TreeMap<String, String> tmArbitri = null;
    private TreeMap<Carta, String> tmCarta = null;
    private TreeMap<String, TreeSet<Date>> tmEccezioni = null;
    /**inizializza il documento Carta per la scrittura */
    void initializeWriterCarta(){
        root = new Element(TAG_ROOT);
        jdomCarta = new Document(root);
    }
    /**inizializza il documento Arbitri per la scrittura */
    void initializeWriterArbitri(){
        root = new Element(TAG_ROOT);
        jdomArbitri = new Document(root);
    }
    /**inizializza il documento Eccezioni per la scrittura */
    void initializeWriterEccezioni(){
        root = new Element(TAG_ROOT);
        jdomEccezioni = new Document(root);
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
    void addItemArbitro(String key, String value) {
        Element item = new Element(TAG_ITEM);
        Element nome = new Element(TAG_ARBITRO_NOME);
        nome.setText(key);
        Element paese = new Element(TAG_ARBITRO_PAESE);
        paese.setText(value);
        item.addContent(nome);
        item.addContent(paese);
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
    void writeArbitri(File xml) throws IOException{
        write().output(jdomArbitri, new FileOutputStream(xml));
    }
    /**Scrive l'xml eccezioni
     *
     * @throws IOException
     */
    void writeEccezioni(File xml) throws IOException{
        write().output(jdomEccezioni, new FileOutputStream(xml));
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
    @SuppressWarnings("unchecked")
	ArrayList<String[]> initializeReaderArbitro(File xml) throws JDOMException, IOException{
        ArrayList<String[]> array = null;
        if (xml.exists()){
            //Creo un SAXBuilder e con esso costruisco un document
            jdomArbitri = new SAXBuilder().build(xml);
            int size = jdomArbitri.getRootElement().getChildren().size();
            if (size>0){
                tmArbitri = new TreeMap<String, String>();
                array = new ArrayList<String[]>();
                Iterator iterator = jdomArbitri.getRootElement().getChildren().iterator();
                while(iterator.hasNext()){
                    Element role = (Element)iterator.next();
                    String arbitro = role.getChild(TAG_ARBITRO_NOME).getText().toUpperCase();
                    String residenza = role.getChild(TAG_ARBITRO_PAESE).getText().toUpperCase();
                    String[] temp = {arbitro, residenza};
                    array.add(temp);
                    tmArbitri.put(arbitro, residenza);
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    TreeMap<String, String> getMapArbitri() {
        return tmArbitri;
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