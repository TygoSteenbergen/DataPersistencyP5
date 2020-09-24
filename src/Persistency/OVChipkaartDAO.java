package Persistency;

import Business.OVChipkaart;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    void save(OVChipkaart ovChipkaart) throws SQLException;
    void update(OVChipkaart ovChipkaart) throws SQLException;
    void delete(OVChipkaart ovChipkaart) throws SQLException;
    OVChipkaart findByReiziger(int id) throws SQLException;
    List<OVChipkaart> findAll() throws SQLException;
}
