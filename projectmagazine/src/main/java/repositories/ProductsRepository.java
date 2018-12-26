package repositories;

import models.Product;

import java.util.List;


public interface ProductsRepository extends CrudRepository<Product> {
    Product findByName(String name);
    List<Product> findAllByTitle(String title);
    List<Product> findAllByTitleSearch(String title);
    void deleteById(Long id);
}