package it.fipavpuglia.taranto.lm.core;

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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class Pdf {
    private Document documento;
    private Font arial10n = FontFactory.getFont("Arial", 10, Font.NORMAL);
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

        float[] colsWidth3 = {5.5f, 1.25f, 1.2f, 1.5f, 3.5f};
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
        documento.add(tabella);
    }
    
    void printPartite() throws FileNotFoundException, DocumentException{
        float[] colsWidth = {0.3f, 0.7f, 2f, 1, 0.4f, 1.1f, 1.5f, 1.5f, 1.5f, 1f};
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

        

        documento.add(tabella);
    }

    void close(){
        documento.close();
    }

    /**Stampa i dati in una tabella 
     * 
     * @param dati dati da stampare
     * @param titolo titolo colonne
     * @param lunghezza lunghezza in percentuale delle colonne
     */
    void stampaTabella(Object[][] dati, Object[] titolo, float[] lunghezza)
            throws FileNotFoundException, DocumentException{
        int righe=dati.length;
        int colonne=dati[0].length;
        //documento.add(new Paragraph(nomedocumento));
        documento.add(new Paragraph("\n"));
        PdfPTable tabella = new PdfPTable(lunghezza);
        if (colonne==2)
            tabella.setWidthPercentage(50);
        else if (colonne==3)
            tabella.setWidthPercentage(75);
        else if (colonne==4)
            tabella.setWidthPercentage(90);
        else if (colonne>4)
            tabella.setWidthPercentage(100);
        tabella.setHeaderRows(1);
        if (titolo!=null){
            for (int i=0; i<colonne; i++)
                tabella.addCell(new Phrase(titolo[i].toString()));
        }
        for (int i=0; i<righe; i++)
            for (int j=0; j<colonne; j++) {
                String temp = "";
                if (dati[i][j]!=null)
                    temp = dati[i][j].toString();
                tabella.addCell(temp);
            }
        documento.add(tabella);
        documento.close();
    }
} // end class