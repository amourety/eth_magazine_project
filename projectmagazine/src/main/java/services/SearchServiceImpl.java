package services;

import models.Product;
import repositories.ProductsRepository;

import java.util.List;

public class SearchServiceImpl implements SearchService {

    private ProductsRepository productsRepository;

    public SearchServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<Product> search(String title) {
        return productsRepository.findAllByTitleSearch(title);
    }
}
