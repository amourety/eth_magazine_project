package repositories;

import models.Basket;
import models.Order;
import models.Product;

import java.util.List;

public interface BasketRepository extends CrudRepository<Basket>{
    Basket findByOwnerId(Long ownerId);
    List<Product> getProductList(Basket basket);
    void addItemToBasket(Product product, Basket basket);
    void removeItemToBasket(Product product, Basket basket);
    void updateAmount(Product product, Basket basket,int newAmount);
    void deleteAll(Basket basket);
    Integer getAmount(Product product,Basket basket);
    List<Product> getProductsByOrder(Basket basket,Order order);
}
