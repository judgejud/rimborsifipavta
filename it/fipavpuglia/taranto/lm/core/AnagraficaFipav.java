package it.fipavpuglia.taranto.lm.core;
/**
 *
 * @author luca
 */
public class AnagraficaFipav {    
    private String role;
    private String city_card;
    private String group;
    private String assegno;

    public AnagraficaFipav(String role, String city_card, String group, String assegno) {
        this.role = role;
        this.city_card = city_card;
        this.group = group;
        this.assegno = assegno;
    }
   
    public String getCity_card() {
        return city_card;
    }

    public void setCity_card(String city_card) {
        this.city_card = city_card;
    }    

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAssegno() {
        return assegno;
    }

    public void setAssegno(String assegno) {
        this.assegno = assegno;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}