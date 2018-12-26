package services;

import models.Product;
import repositories.ProductsRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService{
    private ProductsRepository productsRepository;

    public ProductServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> findAll(){
        return productsRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }

    @Override
    public void addProduct(Product model){
        productsRepository.save(model);
    }

}
