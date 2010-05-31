package it.fipavpuglia.taranto.lm.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;

/**
 *
 * @author luca
 */
public class Http {
    
    private DefaultHttpClient client;
    private final String ADDRESS_ACI = 
            new String("http://servizi.aci.it/distanze-chilometriche-web/itinerarydatainsert.do");

    Http(){
        client = new DefaultHttpClient(new BasicHttpParams());
    }

    void post(String comuneFrom, String capFrom, String provinciaFrom, String regioneFrom,
            String comuneTo, String capTo, String provinciaTo, String regioneTo)
                throws IOException{
        HttpPost post = new HttpPost(ADDRESS_ACI);
        //parametri presi dall'html form action del sito
        List<NameValuePair> lnvp = new ArrayList<NameValuePair>();
        //partenza=TARANTO+%28TARANTO%29&partenza_latitudine=40.46924&partenza_longitudine=17.24001
        //arrivo=CRISPIANO+%28TARANTO%29&arrivo_latitudine=40.60469&arrivo_longitudine=17.22953
        lnvp.add(new BasicNameValuePair("geografia_partenza", "true"));
        lnvp.add(new BasicNameValuePair("partenza_comune_nome", comuneFrom));
        lnvp.add(new BasicNameValuePair("partenza_comune_cap", capFrom));
        lnvp.add(new BasicNameValuePair("partenza_provincia_nome", provinciaFrom));
        lnvp.add(new BasicNameValuePair("partenza_regione_nome", regioneFrom));
        lnvp.add(new BasicNameValuePair("geografia_arrivo", "true"));
        lnvp.add(new BasicNameValuePair("arrivo_comune_nome", comuneFrom));
        lnvp.add(new BasicNameValuePair("arrivo_comune_cap", capFrom));
        lnvp.add(new BasicNameValuePair("arrivo_provincia_nome", provinciaFrom));
        lnvp.add(new BasicNameValuePair("arrivo_regione_nome", regioneFrom));
        lnvp.add(new BasicNameValuePair("itinerary_type", "2"));
        lnvp.add(new BasicNameValuePair("Submit", "Continua"));

        post.setEntity(new UrlEncodedFormEntity(lnvp, HTTP.UTF_8));
        InputStream is = client.execute(post).getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public static void main(String[] args){
        Http h = new Http();
        try {
            h.post("Taranto", "74100", "taranto", "puglia", "crispiano", "74012", "taranto", "puglia");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}