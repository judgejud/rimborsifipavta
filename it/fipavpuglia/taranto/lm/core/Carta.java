package it.fipavpuglia.taranto.lm.core;
/**
 *
 * @author luca
 */
public class Carta implements Comparable<Carta>{
    private String partenza, arrivo;

    public Carta(String from, String to) {
        from = from.toUpperCase();
        to = to.toUpperCase();
        if (from.compareTo(to)<0){
            partenza = from;
            arrivo = to;
        } else {
            partenza = to;
            arrivo = from;
        }
    }

    public String getArrivo() {
        return arrivo;
    }

    public String getPartenza() {
        return partenza;
    }

    public int compareTo(Carta o) {
        if (this.getPartenza().equals(o.getPartenza()))
            return (this.getArrivo().compareTo(o.getArrivo()));
        else
            return (this.getPartenza().compareTo(o.getPartenza()));
    }
}