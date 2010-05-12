package it.fipavpuglia.taranto.lm.core;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
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
            documento = new Document(PageSize.A4.rotate(),0,0,0,0);
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
        PdfPTable tabella = new PdfPTable(8);
        tabella.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabella.setWidthPercentage(95);
        Image image = Image.getInstance("logo_fipavtaranto.png");
        PdfPCell cImage = new PdfPCell(image);
        cImage.setRowspan(3);
        tabella.addCell(cImage);
        Phrase modulo = new Phrase("MODULO LIQUIDAZIONE PREMI/COMPENSI IDENNITA' DI TRASFERTA/RIMBORSI FORFETTARI DI SPESA");
        PdfPCell ctext = new PdfPCell(modulo);
        ctext.setColspan(6);
        tabella.addCell(ctext);
        tabella.addCell(cImage);
        ctext.setPhrase(null);
        tabella.addCell(ctext);
        Phrase legge = new Phrase("L.342/2000 ART. 37 COMMA 1, LETT. C) e D) D.P.R. 22/12/86 N. 917 ART. 81, COMMA 1 LETT. M) E ART. 83 COMMA 2");
        ctext.setPhrase(legge);
        tabella.addCell(ctext);
        documento.add(tabella);
    }
    
    void printAnagrafica() throws FileNotFoundException, DocumentException{        
        PdfPTable tabella = new PdfPTable(6);
        
    }
    
    void printPartite() throws FileNotFoundException, DocumentException{
        PdfPTable tabella = new PdfPTable(10);
        
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