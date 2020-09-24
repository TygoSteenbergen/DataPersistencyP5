import Business.Reiziger;
import Persistency.AdresDAO;
import Persistency.AdresDAOPsql;
import Persistency.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Dinglebugger";
        try {
            Connection conn = DriverManager.getConnection(url);
            ReizigerDAOPsql RDOA = new ReizigerDAOPsql(conn);
            AdresDAO ADAO = new AdresDAOPsql(conn, RDOA);
            AdresDAOPsql.testAdresDAO(ADAO, RDOA);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}