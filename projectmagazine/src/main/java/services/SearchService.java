package services;

import models.Product;

import java.util.List;

public interface SearchService {
    List<Product> search(String title);
}