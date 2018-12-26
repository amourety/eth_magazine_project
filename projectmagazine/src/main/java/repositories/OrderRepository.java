package repositories;

import models.Basket;
import models.Order;
import models.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order> {
    void addOrder(Order order, Basket basket);
    List<Order> findOrders(User user);
    void delete(Long id);
    String getTrack(Order order);
}
