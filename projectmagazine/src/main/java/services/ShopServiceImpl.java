
package services;

import models.Basket;
import models.Order;
import models.Product;
import models.User;
import repositories.BasketRepository;
import repositories.OrderRepository;
import repositories.ProductsRepository;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

public class ShopServiceImpl implements ShopService {

    private BasketRepository basketRepository;
    private ProductsRepository productRepository;
    private OrderRepository orderRepository;

    public ShopServiceImpl(BasketRepository basketRepository, ProductsRepository productRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
    }
    public ShopServiceImpl(BasketRepository basketRepository, ProductsRepository productRepository, OrderRepository orderRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;

    }


    @Override
    public List<Product> getProducts(Basket basket) {
        return productRepository.findAll();
    }

    @Override
    public Basket delete(Long productId, Cookie[] cookies, LoginService loginService) {
        Product product = productRepository.find(productId);

        User basketOwner = getUser(cookies,loginService);

        Basket basket = basketRepository.findByOwnerId(basketOwner.getId());
        Integer currAmount = basketRepository.getAmount(product,basket);

        try {
        if(currAmount == 1){
            product.setAmount(1);
            basket.delete(product);
            basketRepository.removeItemToBasket(product,basket);
        }
        else
        {
            product.setAmount(currAmount-1);
            basket.delete(product);
            basketRepository.updateAmount(product,basket,currAmount-1);
        }

        }
        catch (Exception e){}

        return basket;
    }

    public void addOrder(Cookie[] cookies,LoginService loginService){
        User basketOwner = getUser(cookies,loginService);

        Basket basket = basketRepository.findByOwnerId(basketOwner.getId());

        List<Product> products = basket.getProducts();
        String orderStr = getOrderStr(products);

        Order order = Order.builder().user_id(basketOwner.getId())
                .text(orderStr).build();

        orderRepository.addOrder(order,basket);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderRepository.findOrders(user);
    }

    @Override
    public void deleteOrder(Long id){
        orderRepository.delete(id);
    }

    @Override
    public List<Product> getProductsByOrder(Basket basket, Order order) {
        return basketRepository.getProductsByOrder(basket,order);
    }

    @Override
    public String getTrack(Order order) {
        return orderRepository.getTrack(order);
    }


    @Override
    public Basket deleteAll(Cookie[] cookies, LoginService loginService) {
        User basketOwner = getUser(cookies,loginService);

        Basket basket;
        basket = basketRepository.findByOwnerId(basketOwner.getId());

        basket.deleteAll();
        basketRepository.deleteAll(basket);
        return basket;
    }

    @Override
    public Basket findByOwnerId(Long ownerId) {
        Basket basket = basketRepository.findByOwnerId(ownerId);
        basket.getProducts().addAll(getProducts(basket));
        return basket;
    }

    @Override
    public Basket buy(Long productId, Cookie[] cookies, LoginService loginService){
        Product product = productRepository.find(productId);
        User basketOwner = getUser(cookies,loginService);
        Basket basket = basketRepository.findByOwnerId(basketOwner.getId());
        Integer currAmount = basketRepository.getAmount(product,basket);
        product.setAmount(currAmount);
        if(currAmount == null){
            product.setAmount(1);
            basket.add(product);
            basketRepository.addItemToBasket(product,basket);
        }
        else
        {
            product.setAmount(currAmount+1);
            basket.add(product);
            basketRepository.updateAmount(product,basket,currAmount+1);
        }
        return basket;
    }

    @Override
    public Basket getUserBasket(LoginService loginService, Cookie[] cookies) {
        User basketOwner = getUser(cookies,loginService);
        Basket basket = basketRepository.findByOwnerId(basketOwner.getId());

        if (basket == null) {
            basket = new Basket(basketOwner);
            basketRepository.save(basket);
        }
        return basket;
    }

    public User getUser(Cookie[] cookies, LoginService loginService){
        User basketOwner = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                if (loginService.isExistByCookie(cookie.getValue())) {
                    basketOwner = loginService.getUserByCookie(cookie.getValue());
                    break;
                }
            }
        }
        return  basketOwner;
    }
    public String getOrderStr(List<Product> products){
        String orderStr = "";
        int sum = 0;
        for(int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            sum += Integer.valueOf(product.getPrice()) * product.getAmount();
            orderStr = orderStr + product.getName()+ "\n" + " " + product.getPrice() + "$\n" + " count:" + product.getAmount() + "\n";
        }
        orderStr += " total price:" + sum;
        return orderStr;
    }

}