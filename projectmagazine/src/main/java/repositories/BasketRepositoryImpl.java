package repositories;

import lombok.SneakyThrows;
import lombok.ToString;
import models.Basket;
import models.Order;
import models.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@ToString
public class BasketRepositoryImpl implements BasketRepository {

    private JdbcTemplate jdbcTemplate;
    private UsersRepository usersRepository;
    //language=SQL
    private static final String SQL_INSERT_BASKET_PRODUCTS =
            "insert into product_basket(basket_id, product_id) values (?,?)";

    //language=SQL
    private static final String SQL_UPDATE_BASKET_PRODUCTS =
            "update product_basket set (basket_id,product_id) = (?,?) where basket_id = ? and order_id is null";
    //language=SQL
    private static final String SQL_SELECT_BASKET_BY_OWNER_ID =
            "select * from basket where owner_id = ?";

    //language=SQL
    private static final String SQL_INSERT_BASKET =
            "insert into basket(owner_id) values (?)";

    //language=SQL
    private static final String SQL_UPDATE_BASKET =
            "update basket set owner_id = ? where id = ?";

    //language=SQL
    private static final String SQL_DELETE_ALL_PRODUCT =
            "delete from product_basket where product_basket.basket_id = ? and order_id is null";


    //language=SQL
    private static final String SQL_SELECT_BASKET_PRODUCTS =
            "select product.id, product.name, product.price, product_basket.amount from product_basket, product where (basket_id = ? and product.id = product_id) and product_basket.order_id is null";

    private PreparedStatement insertBasketProductsStatement;
    private PreparedStatement updateBasketProductStatement;
    private PreparedStatement insertBasketStatement;
    private PreparedStatement updateBasketStatement;

    private RowMapper<Basket> basketRowMapper = (resultSet, i) -> Basket.builder()
            .id(resultSet.getLong("id"))
            .build();
    private RowMapper<Product> productRowMapper = (resultSet, i) -> Product.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .price(resultSet.getString("price"))
            .amount(resultSet.getInt("amount"))
            .build();

    private RowMapper<Integer> amountRowMapper = ((resultSet, i) -> resultSet.getInt("amount"));


    @SneakyThrows
    public BasketRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);
        insertBasketStatement = dataSource.getConnection().prepareStatement(SQL_INSERT_BASKET);
        updateBasketStatement = dataSource.getConnection().prepareStatement(SQL_UPDATE_BASKET);
        insertBasketProductsStatement = dataSource.getConnection().prepareStatement(SQL_INSERT_BASKET_PRODUCTS);
        updateBasketProductStatement = dataSource.getConnection().prepareStatement(SQL_UPDATE_BASKET_PRODUCTS);
    }

    public List<Basket> findAll() {
        return null;
    }

    public Basket find(Long id) {
        return null;
    }


    @SneakyThrows
    public void save(Basket model) {
        insertBasketStatement.setLong(1, model.getUser().getId());
        insertBasketStatement.executeUpdate();
        /*for (Product product : model.getProductList()) {
            jdbcTemplate.update(SQL_INSERT_BASKET_PRODUCTS, productRowMapper, model.getId(),product.getId());
        }
        jdbcTemplate.update(SQL_INSERT_BASKET, basketRowMapper, model.getId(), model.getOwner().getId());*/
    }

    @SneakyThrows
    public void update(Basket model) {

        for (Product product : model.getProducts()) {
            updateBasketProductStatement.setLong(1, model.getId());
            updateBasketProductStatement.setLong(3, model.getId());
            updateBasketProductStatement.setLong(2, product.getId());
            updateBasketProductStatement.executeUpdate();
        }

        updateBasketStatement.setLong(1, model.getUser().getId());
        updateBasketStatement.setLong(2, model.getId());

        updateBasketStatement.executeUpdate();


        /*for (Product product : model.getProductList()) {
            jdbcTemplate.update(SQL_INSERT_BASKET_PRODUCTS, productRowMapper, product.getId(), product.getName(), product.getCost());
        }
        jdbcTemplate.update(SQL_UPDATE_BASKET, basketRowMapper, model.getOwner().getId());*/
    }


    @Override
    public Basket findByOwnerId(Long ownerId) {
        Basket basket;
        try {
            basket = jdbcTemplate.queryForObject(SQL_SELECT_BASKET_BY_OWNER_ID, basketRowMapper, ownerId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

        basket.setUser(usersRepository.find(ownerId));
        basket.setProducts(getProductList(basket));
        return basket;
    }

    @Override
    public List<Product> getProductList(Basket basket) {
        return jdbcTemplate.query(SQL_SELECT_BASKET_PRODUCTS, productRowMapper, basket.getId());
    }
    @Override
    public void deleteAll(Basket basket) {
        jdbcTemplate.update(SQL_DELETE_ALL_PRODUCT,basket.getId());
    }

    public Integer getAmount(Product product,Basket basket){
        //language=SQL
        String qGetAmount = "SELECT amount from product_basket where (basket_id = ? and product_id = ?) and order_id is null";
        try {
            Integer amount = jdbcTemplate.queryForObject(qGetAmount, amountRowMapper, basket.getId(), product.getId());
            return amount;
        }
        catch (Exception e){
            return null;
        }
    }

    public void addItemToBasket(Product product, Basket basket){

        //language=SQL
        String qInsertItem = "INSERT INTO product_basket (basket_id,product_id) VALUES (?,?)";
            jdbcTemplate.update(qInsertItem, basket.getId(), product.getId());
    }
    public void removeItemToBasket(Product product, Basket basket){
        //language=SQL
        String qDeleteItem = "DELETE FROM product_basket WHERE (basket_id =? and product_id =?) and order_id is null";
            jdbcTemplate.update(qDeleteItem, basket.getId(), product.getId());
    }
    public void updateAmount(Product product, Basket basket,int newAmount){
        //language=SQL
        String qUpdateAmount = "UPDATE product_basket SET amount = ? WHERE (basket_id =? and product_id =?) and order_id is null";
        jdbcTemplate.update(qUpdateAmount,newAmount,basket.getId(),product.getId());
    }
    public List<Product> getProductsByOrder(Basket basket, Order order){
        //language=SQL
        String SQL_SELECT_BASKET_PRODUCTS_ORDER =
                "select product.id, product.name, product.price, product_basket.amount from product_basket, product where (basket_id = ? and product.id = product_id) and product_basket.order_id = ?";

        return jdbcTemplate.query(SQL_SELECT_BASKET_PRODUCTS_ORDER,productRowMapper, basket.getId(),order.getId());

    }


}
