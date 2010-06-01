package it.fipavpuglia.taranto.lm.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

public class Pdf {
    private Document documento;
    private Font arial10n = FontFactory.getFont("Arial", 10, Font.NORMAL);
    private Font arial9n = FontFactory.getFont("Arial", 9, Font.NORMAL);
    private Font arial10b = FontFactory.getFont("Arial", 10, Font.BOLD);
    private Font arial11b = FontFactory.getFont("Arial", 11, Font.BOLD);
    private PdfPTable tableIntestazione = null;
    /**Crea il pdf
     * 
     * @param tNomeFile  nome file
     * @param tNomeDocumento nome documento
     * @param verticale orientamento pagina, verticale true, orizzontale false
     */
    Pdf(String tNomeFile, String tNomeDocumento, boolean verticale) throws
            DocumentException, FileNotFoundException{
        //crea l'oggetto documento
        if (verticale)
            documento = new Document(PageSize.A4,5,5,5,5);
        else
            documento = new Document(PageSize.A4.rotate(),10,10,10,10);
        String nomepdf = tNomeFile + ".pdf";
        File f = new File(nomepdf);
        if (f.exists())
            f.delete();
        // crea il writer che ascolta il documento e directs a PDF-stream to a file
        PdfWriter.getInstance(documento, new FileOutputStream(nomepdf));
        // apertura documento
        documento.open();
    }
    
    void creaIntestazione() throws DocumentException, BadElementException,
            MalformedURLException, IOException{
        if (tableIntestazione==null){
            float[] colsWidth = {1f, 10f, 0.9f};
            tableIntestazione = new PdfPTable(colsWidth);
            tableIntestazione.setWidthPercentage(100);
            Image logosx = Image.getInstance("logosx.jpg");
            logosx.setCompressionLevel(0);
            PdfPCell cImage = new PdfPCell(logosx);
            cImage.setRowspan(3);
            cImage.setBorder(0);
            tableIntestazione.addCell(cImage);
            Phrase modulo = new Phrase("MODULO LIQUIDAZIONE PREMI/COMPENSI IDENNITA' DI " +
                    "TRASFERTA/RIMBORSI FORFETTARI DI SPESA",arial11b);
            PdfPCell ctext = new PdfPCell(modulo);
            ctext.setHorizontalAlignment(Element.ALIGN_CENTER);
            ctext.setBorder(0);
            tableIntestazione.addCell(ctext);
            Image logodx = Image.getInstance("logodx.jpg");
            logosx.setCompressionLevel(9);
            cImage = new PdfPCell(logodx);
            cImage.setRowspan(3);
            cImage.setBorder(0);
            cImage.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableIntestazione.addCell(cImage);
            ctext.setPhrase(new Phrase());
            tableIntestazione.addCell(ctext);
            Phrase legge = new Phrase("L.342/2000 ART. 37 COMMA 1, LETT. C) e D) D.P.R. 22/12/86 " +
                    "N. 917 ART. 81, COMMA 1 LETT. M) E ART. 83 COMMA 2", arial10n);
            ctext.setPhrase(legge);
            tableIntestazione.addCell(ctext);
        }
        documento.add(tableIntestazione);
        documento.add(new Paragraph());
    }
    
    void printAnagrafica(Anagrafica ana, String periodo) throws FileNotFoundException,
            DocumentException{
        float[] colsWidth = {0.8f, 2f, 0.5f, 2f, 0.2f, 1f, 1f, 2f};
        PdfPTable tabella = new PdfPTable(colsWidth);
        tabella.setWidthPercentage(100);
        Phrase text;
        if (ana.getSex().equalsIgnoreCase("uomo"))
            text = new Phrase("Il Sottoscritto", arial10n);
        else
            text = new Phrase("La Sottoscritta", arial10n);
        PdfPCell cellText = new PdfPCell(text);
        cellText.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellText.setBorder(0);
        tabella.addCell(cellText);
        PdfPCell cellAnag = new PdfPCell(new Phrase(ana.getSurname_name(), arial10b));
        cellAnag.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellAnag.setBorder(0);
        tabella.addCell(cellAnag);
        if (ana.getSex().equalsIgnoreCase("uomo"))
            text = new Phrase("Nato a", arial10n);
        else
            text = new Phrase("Nata a", arial10n);
        cellText.setPhrase(text);
        tabella.addCell(cellText);
        cellAnag.setPhrase(new Phrase(ana.getCity_born(), arial10b));
        tabella.addCell(cellAnag);
        cellText.setPhrase(new Phrase("il", arial10n));
        tabella.addCell(cellText);
        cellAnag.setPhrase(new Phrase(ana.getDate_born(), arial10b));
        tabella.addCell(cellAnag);
        cellText.setPhrase(new Phrase("Cod. Fiscale", arial10n));
        tabella.addCell(cellText);
        cellAnag.setPhrase(new Phrase(ana.getFiscal_code(), arial10b));
        tabella.addCell(cellAnag);
        documento.add(tabella);

        float[] colsWidth2 = {0.8f, 2f, 0.5f, 3f, 0.5f, 1f};
        tabella = new PdfPTable(colsWidth2);
        tabella.setWidthPercentage(100);
        cellText = new PdfPCell(new Phrase("Residente a", arial10n));
        cellText.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellText.setBorder(0);
        tabella.addCell(cellText);
        cellAnag = new PdfPCell(new Phrase(ana.getCity_residence(), arial10b));
        cellAnag.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellAnag.setBorder(0);
        tabella.addCell(cellAnag);
        cellText.setPhrase(new Phrase("Indirizzo", arial10n));
        tabella.addCell(cellText);
        cellAnag.setPhrase(new Phrase(ana.getAddress(), arial10b));
        tabella.addCell(cellAnag);
        cellText.setPhrase(new Phrase("C.A.P.", arial10n));
        tabella.addCell(cellText);
        cellAnag.setPhrase(new Phrase(ana.getCap(), arial10b));
        tabella.addCell(cellAnag);
        documento.add(tabella);

        float[] colsWidth3 = {5.45f, 1.3f, 1.2f, 1.8f, 3.5f};
        tabella = new PdfPTable(colsWidth3);
        tabella.setWidthPercentage(100);
        cellText = new PdfPCell(new Phrase("chiede che gli siano liquidate le " +
                "seguenti competenze relative all'incarico di", arial10n));
        cellText.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellText.setBorder(0);
        tabella.addCell(cellText);
        cellAnag = new PdfPCell(new Phrase(ana.getRole(), arial10b));
        cellAnag.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellAnag.setBorder(0);
        tabella.addCell(cellAnag);
        cellText.setPhrase(new Phrase("per il periodo di", arial10n));
        tabella.addCell(cellText);
        cellAnag.setPhrase(new Phrase(periodo, arial10b));
        tabella.addCell(cellAnag);
        cellText.setPhrase(new Phrase("in occasione delle sotto elencate prestazioni",
                arial10n));
        tabella.addCell(cellText);
        tabella.setSpacingAfter(2f);
        documento.add(tabella);
    }
    
    void printPartite(ArrayList<Vector> array) throws FileNotFoundException, DocumentException{
        float[] colsWidth = {0.3f, 0.8f, 2f, 1.5f, 0.4f, 1.1f, 1.5f, 1.5f, 1.5f, 0.8f};
        PdfPTable tabella = new PdfPTable(colsWidth);
        tabella.setWidthPercentage(100);
        PdfPCell cellText = new PdfPCell(new Phrase("\nN°", arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase("\nData", arial10b));
        cellText.setBorderWidth(1.5f);
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase("Motivo della prestazione \no\n n° di " +
                "gara/designazione", arial10b));
        cellText.setBorderWidth(1.5f);
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase("\nLocalità", arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase("\nKm", arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        //TODO: sistemare il 0,25
        cellText = new PdfPCell(new Phrase("Rimborso auto \n\n€ 0,25 a Km", arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase("Rimb. spese documentate \nes. autostrada/treno",
                arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase("Rimb spese non document. \nes. spediz. Referto",
                arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase("Rimb. forfettario indennità trasferta " +
                "art.37 l. 342/2000", arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase("\nTotale", arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        
        for (int i=0; i<array.size()-1; i++){
            Vector v = array.get(i);
            //NUMERO
            cellText = new PdfPCell(new Phrase(String.valueOf(i+1), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);            
            tabella.addCell(cellText);
            //DATA
            cellText = new PdfPCell(new Phrase(v.get(0).toString(), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
            //DESIGNAZIONE
            cellText = new PdfPCell(new Phrase(v.get(1).toString(), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
            //LOCALITA
            cellText = new PdfPCell(new Phrase(v.get(2).toString(), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
            //KM
            cellText = new PdfPCell(new Phrase(v.get(3).toString(), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
            //RIMBORSO AUTO
            cellText = new PdfPCell(new Phrase(euroFormat(v.get(4)), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
            //RIMBORSO SPESE DOCUMENTATE
            cellText = new PdfPCell(new Phrase(euroFormat(v.get(5)), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
            //RIMBORSO SPESE non DOCUMENTATE
            cellText = new PdfPCell(new Phrase(euroFormat(v.get(6)), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
            //RIMBORSO FORFETTARIO PARTITE
            cellText = new PdfPCell(new Phrase(euroFormat(v.get(7)), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
            //TOTALE RIGA
            cellText = new PdfPCell(new Phrase(euroFormat(v.get(8)), arial10n));
            cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellText.setBorderWidth(1f);
            tabella.addCell(cellText);
        }
        for (int i=0; i<23-array.size(); i++){
            for (int j=0; j<colsWidth.length; j++){
                cellText = new PdfPCell(new Phrase());
                cellText.setBorderWidth(1f);
                cellText.setMinimumHeight(10f);
                tabella.addCell(cellText);
            }
        }
        Vector v = array.get(array.size()-1);
        //TOTALE
        cellText = new PdfPCell(new Phrase("TOTALI", arial10b));
        cellText.setColspan(4);
        cellText.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellText.setBorderWidth(1f);
        tabella.addCell(cellText);
        //KM
        cellText = new PdfPCell(new Phrase(v.get(0).toString(), arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        //RIMBORSO AUTO
        cellText = new PdfPCell(new Phrase(euroFormat(v.get(1)), arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase(euroFormat(v.get(2)), arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase(euroFormat(v.get(3)), arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase(euroFormat(v.get(4)), arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        cellText = new PdfPCell(new Phrase(euroFormat(v.get(5)), arial10b));
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorderWidth(1.5f);
        tabella.addCell(cellText);
        documento.add(tabella);
    }

    void printChiusura() throws DocumentException{
        PdfPTable tabella = new PdfPTable(1);
        tabella.setWidthPercentage(100);
        Phrase text = new Phrase("DICHIARAZIONI DEL PERCIPIENTE", arial10b);
        PdfPCell cellText = new PdfPCell(text);
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorder(0);
        tabella.addCell(cellText);
        text = new Phrase("Il sottoscritto dichiara, sotto la propria responsabilità, di avere " +
                "effettivamente effettuato le prestazioni sopra esposte e che tutte le spese ed " +
                "indennità qui descritte sono derivanti dall'incarico conferitogli.", arial9n);
        cellText = new PdfPCell(text);
        cellText.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellText.setBorder(0);
        tabella.addCell(cellText);
        text = new Phrase("                              Giustificativi di spesa allegati n° _____",
                arial10b);
        cellText = new PdfPCell(text);
        cellText.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellText.setBorder(0);
        tabella.addCell(cellText);
        text = new Phrase("    Data ___________                                                  " +
                "Firma leggibile  ______________________________________", arial10n);
        cellText = new PdfPCell(text);
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorder(0);
        cellText.setMinimumHeight(1.7f);
        tabella.addCell(cellText);
        text = new Phrase("Il sottoscritto dichiara, sotto la propria responsabilità, di non aver" +
                " superato, col pagamento della suddetta indennità, il limite di € 7.500,00 " +
                "previsto dall'art.37, legge 342/2000. S'impegna, inoltre, a comunicare alla " +
                "Federazione il superamento di detto limite.", arial9n);
        cellText = new PdfPCell(text);
        cellText.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cellText.setBorder(0);
        tabella.addCell(cellText);
        text = new Phrase("    Data ___________                                                  " +
                "Firma leggibile  ______________________________________", arial10n);
        cellText = new PdfPCell(text);
        cellText.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellText.setBorder(0);
        cellText.setMinimumHeight(1.8f);
        tabella.addCell(cellText);
        documento.add(tabella);
    }

    void close(){
        documento.close();
    }

    private String euroFormat(Object obj){
        DecimalFormat df = (DecimalFormat)NumberFormat.getNumberInstance(Locale.ITALY);
        df.applyPattern("€ #,##0.00");
        return df.format(Float.parseFloat(obj.toString()));
    }
} // end class