package services;

import models.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    void deleteById(Long id);
    void addProduct(Product model);
}
