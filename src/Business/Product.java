package Business;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private OVChipkaart ovChipkaart;

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public double getPrijs() {
        return prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public OVChipkaart getOvChipkaart() {
        return ovChipkaart;
    }

    public void setOvChipkaart(OVChipkaart ovChipkaart) {
        this.ovChipkaart = ovChipkaart;
    }
}
