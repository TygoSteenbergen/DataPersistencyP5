package Business;

import java.util.ArrayList;
import java.util.Date;

public class OVChipkaart {
    private  int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private Double saldo;
    private int reizigerId;
    private final ArrayList<Product> producten = new ArrayList<>();

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, Double saldo, int reizigerId) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reizigerId = reizigerId;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(int kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public void voegProductToe(Product product){
        producten.add(product);
        product.setOvChipkaart(this);
    }

    public void verwijderProduct(Product product){
        producten.remove(product);
        product.setOvChipkaart(null);
    }

    public ArrayList<Product> getProducten() {
        return producten;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }
}
