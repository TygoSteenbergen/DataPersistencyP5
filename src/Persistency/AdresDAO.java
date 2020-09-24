package Persistency;

import Business.Adres;
import Business.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres);

    boolean update(Adres adres);

    boolean delete(Adres adres);

    Adres findByReiziger(Reiziger reiziger) throws SQLException;

    List<Adres> findAll() throws SQLException;
}
