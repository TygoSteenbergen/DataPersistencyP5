package Persistency;

import Business.OVChipkaart;
import Business.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOPsql implements ProductDAO {
    private final Connection conn;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Product product) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO product (product_nummer, naam, beschrijving, prijs)\n" +
                        "VALUES (?, ?, ?, ?;");
        pst.setInt(1, product.getProductNummer());
        pst.setString(2, product.getNaam());
        pst.setString(3, product.getBeschrijving());
        pst.setDouble(4, product.getPrijs());
        pst.executeUpdate();
        pst.close();
        if (product.getOvChipkaart() != null) {
            PreparedStatement pst2 = conn.prepareStatement(
                    "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer)\n" +
                            "VALUES (?, ?;");
            pst2.setInt(1, product.getOvChipkaart().getKaartNummer());
            pst2.setInt(2, product.getProductNummer());
            pst2.executeUpdate();
            pst2.close();
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("UPDATE product\n" +
                "SET naam = ?, beschrijving = ?, prijs = ?\n" +
                "WHERE product_nummer = ? ;");
        pst.setString(1, product.getNaam());
        pst.setString(2, product.getBeschrijving());
        pst.setDouble(3, product.getPrijs());
        pst.setInt(4, product.getProductNummer());
        pst.executeUpdate();
        if (product.getOvChipkaart() != null) {
            PreparedStatement pst2 = conn.prepareStatement(
                    "UPDATE ov_chipkaart_product " +
                            "SET kaart_nummer = ? " +
                            "WHERE product_nummer = ?;");
            pst2.setInt(1, product.getOvChipkaart().getKaartNummer());
            pst2.setInt(2, product.getProductNummer());
            pst2.executeUpdate();
            pst2.close();
        }
    }

    @Override
    public void delete(Product product) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
        pst.setInt(1, product.getProductNummer());
        pst.executeUpdate();
        pst.close();
        if (product.getOvChipkaart() != null) {
            PreparedStatement pst2 = conn.prepareStatement(
                    "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ? AND product_nummer = ?");
            pst2.setInt(1, product.getOvChipkaart().getKaartNummer());
            pst2.setInt(2, product.getProductNummer());
            pst2.executeUpdate();
            pst2.close();
        }
    }

    public ArrayList<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        ArrayList<Product> producten = new ArrayList<>();
        PreparedStatement pst = conn.prepareStatement("SELECT c.*, d.geldig_tot, d.klasse, d.saldo, d.reiziger_id " +
                "FROM ov_chipkaart d\n" +
                "INNER JOIN\n" +
                "(SELECT b.product_nummer, a.naam, a.beschrijving, a.prijs, b.kaart_nummer " +
                "FROM ov_chipkaart_product b\n" +
                "INNER JOIN\n" +
                "(SELECT * FROM product )a " +
                "ON a.product_nummer = b.product_nummer\n" +
                "AND b.kaart_nummer = ?) c " +
                "ON c.kaart_nummer =  d.kaart_nummer");
        pst.setInt(1, ovChipkaart.getKaartNummer());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
            product.setOvChipkaart(new OVChipkaart(rs.getInt(5), rs.getDate(6), rs.getInt(7), rs.getDouble(8), rs.getInt(9)));
            producten.add(product);

        }
        return producten;
    }

    public ArrayList<Product> findAll() throws SQLException {
        ArrayList<Product> producten = new ArrayList<>();
        PreparedStatement pst = conn.prepareStatement("SELECT p.*, ov.* " +
                "FROM ov_chipkaart ov, product p, ov_chipkaart_product ovp\n" +
                "WHERE ov.kaart_nummer = ovp.kaart_nummer AND p.product_nummer = ovp.product_nummer");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
            product.setOvChipkaart(new OVChipkaart(rs.getInt(5), rs.getDate(6), rs.getInt(7), rs.getDouble(8), rs.getInt(9)));
            producten.add(product);
        }
        return producten;
    }
}
