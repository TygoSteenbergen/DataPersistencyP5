package Persistency;

import Business.Product;

import java.sql.SQLException;

public interface ProductDAO {
    void save(Product product) throws SQLException;

    void update(Product product) throws SQLException;

    void delete(Product product) throws SQLException;
}
