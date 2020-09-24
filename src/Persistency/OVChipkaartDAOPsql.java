package Persistency;


import Business.OVChipkaart;
import Business.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public void save(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id )\n" +
                        "VALUES (?, ?, ?, ?, ?);");
        pst.setInt(1, ovChipkaart.getKaartNummer());
        pst.setDate(2, (Date) ovChipkaart.getGeldigTot());
        pst.setInt(3, ovChipkaart.getKlasse());
        pst.setDouble(4, ovChipkaart.getSaldo());
        pst.setInt(5, ovChipkaart.getReizigerId());
        pst.executeUpdate();
        pst.close();
        for (Product product : ovChipkaart.getProducten()) {
            PreparedStatement pst2 = conn.prepareStatement(
                    "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer)\n" +
                            "VALUES (?, ?;");
            pst2.setInt(1, ovChipkaart.getKaartNummer());
            pst2.setInt(2, product.getProductNummer());
            pst2.executeUpdate();
            pst2.close();
        }
    }

    @Override
    public void update(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("UPDATE ov_chipkaart\n" +
                "SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ?\n" +
                "WHERE kaart_nummer = ? ;");
        pst.setDate(1, (Date) ovChipkaart.getGeldigTot());
        pst.setInt(2, ovChipkaart.getKlasse());
        pst.setDouble(3, ovChipkaart.getSaldo());
        pst.setInt(4, ovChipkaart.getReizigerId());
        pst.setInt(5, ovChipkaart.getKaartNummer());
        pst.executeUpdate();
        for (Product product : ovChipkaart.getProducten()) {
            PreparedStatement pst2 = conn.prepareStatement("UPDATE ov_chipkaart_product\n" +
                    "SET product_nummer = ? WHERE kaart_nummer = ?;");
            pst2.setInt(1, product.getProductNummer());
            pst2.setInt(2, ovChipkaart.getKaartNummer());
            pst2.executeUpdate();
            pst2.close();
        }
    }

    @Override
    public void delete(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
        pst.setInt(1, ovChipkaart.getKaartNummer());
        pst.executeUpdate();
        pst.close();
        PreparedStatement pst2 = conn.prepareStatement("DELETE FROM  ov_chipkaart_product WHERE kaart_nummer = ?");
        pst2.setInt(1, ovChipkaart.getKaartNummer());
        pst2.executeUpdate();
        pst2.close();
    }

    @Override
    public OVChipkaart findByReiziger(int id) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?;");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new OVChipkaart(rs.getInt(1),
                    rs.getDate(2), rs.getInt(3), rs.getDouble(4)
                    , rs.getInt(5));
        } else {
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        PreparedStatement pst = conn.prepareStatement("SELECT * FROM adres;");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            ovChipkaarten.add(new OVChipkaart(rs.getInt(1),
                    rs.getDate(2), rs.getInt(3), rs.getDouble(4)
                    , rs.getInt(5)));
        }
        rs.close();
        pst.close();
        return ovChipkaarten;

    }
}
