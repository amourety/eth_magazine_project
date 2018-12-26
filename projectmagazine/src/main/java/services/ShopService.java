package services;

import forms.ProductForm;
import models.Basket;
import models.Order;
import models.Product;
import models.User;

import javax.servlet.http.Cookie;
import java.util.List;

public interface ShopService {

    Basket findByOwnerId(Long id);

    Basket buy(Long productId, Cookie[] cookies, LoginService loginService);

    Basket getUserBasket(LoginService loginService, Cookie[] cookies);

    List<Product> getProducts(Basket basket);

    Basket delete(Long productId, Cookie[] cookies, LoginService loginService);

    Basket deleteAll(Cookie[] cookies, LoginService loginService);

    void addOrder(Cookie[] cookies,LoginService loginService);

    List<Order> getUserOrders(User user);
    void deleteOrder(Long id);

    List<Product> getProductsByOrder(Basket basket,Order order);
    String getTrack(Order order);
}