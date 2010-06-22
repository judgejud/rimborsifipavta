package it.fipavpuglia.taranto.lm.core;
/**
 *
 * @author luca
 */
public class Anagrafica {
    private String surname_name;
    private String city_born;
    private String date_born;
    private String fiscal_code;
    private String city_residence;
    private String address;
    private String cap;
    private String role;
    private String sex;
    private String city_card;
    private String group;
    private String assegno;

    public Anagrafica(String surname_name, String city_born, String date_born,
            String fiscal_code, String city_residence, String address, String cap,
            String role, String sex, String city_card, String group, String assegno) {
        this.surname_name = surname_name;
        this.city_born = city_born;
        this.date_born = date_born;
        this.fiscal_code = fiscal_code;
        this.city_residence = city_residence;
        this.address = address;
        this.cap = cap;
        this.role = role;
        this.sex = sex;
        this.city_card = city_card;
        this.group = group;
        this.assegno = assegno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCity_born() {
        return city_born;
    }

    public void setCity_born(String city_born) {
        this.city_born = city_born;
    }

    public String getCity_card() {
        return city_card;
    }

    public void setCity_card(String city_card) {
        this.city_card = city_card;
    }

    public String getCity_residence() {
        return city_residence;
    }

    public void setCity_residence(String city_residence) {
        this.city_residence = city_residence;
    }

    public String getDate_born() {
        return date_born;
    }

    public void setDate_born(String date_born) {
        this.date_born = date_born;
    }

    public String getFiscal_code() {
        return fiscal_code;
    }

    public void setFiscal_code(String fiscal_code) {
        this.fiscal_code = fiscal_code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSurname_name() {
        return surname_name;
    }

    public void setSurname_name(String surname_name) {
        this.surname_name = surname_name;
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