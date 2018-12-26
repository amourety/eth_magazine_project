package repositories;

import models.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository{


    private JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Order> orderRowMapper = (resultSet, i) -> Order.builder().
            id(resultSet.getLong("id")).
            user_id(resultSet.getLong("user_id")).
            basket_id(resultSet.getLong("basket_id")).
            text(resultSet.getString("text_order")).
            track(resultSet.getString("track")).
            build();

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order find(Long id) {
        return null;
    }

    public List<Order> findOrders(User user) {
        //language=SQL
        String SQL = "SELECT * FROM orders WHERE user_id = ?";
        return jdbcTemplate.query(SQL, orderRowMapper, user.getId());
    }

    @Override
    public void delete(Long id) {
        //language=SQL
        String SQL2 = "delete from product_basket where order_id = ?;";
        jdbcTemplate.update(SQL2,id);
        //language=SQL
        String SQL = "delete from orders where id = ?;";
        jdbcTemplate.update(SQL,id);
    }

    @Override
    public void save(Order model) {

    }

    @Override
    public void addOrder(Order order, Basket basket) {
        //language=SQL
        String SQL_INSERT_ORDER = "insert into orders (user_id) values (?)";
        jdbcTemplate.update(SQL_INSERT_ORDER, order.getUser_id());
        //language=SQL
        String SQL_SELECT_ID_ORDER = "select * from orders where basket_id is null and user_id = ?";

        Order order_with_id  = jdbcTemplate.queryForObject(SQL_SELECT_ID_ORDER, orderRowMapper, order.getUser_id());

        //language=SQL
        String SQL_UPDATE_ORDER_BASKET_ID = "update orders set text_order = ?, basket_id = ?, track = ? where id = ?";
        jdbcTemplate.update(SQL_UPDATE_ORDER_BASKET_ID, order.getText(), basket.getId(),"accepted", order_with_id.getId());

        //language=SQL
        String SQL_UPDATE_BASKET = "update product_basket set order_id = ? where basket_id = ? and order_id is null";
        jdbcTemplate.update(SQL_UPDATE_BASKET, order_with_id.getId(),basket.getId());
    }

    public String getTrack(Order order){
        //language=SQL
        String SQL_SELECT_TRACK = "SELECT * from orders where orders.id = ?";
        return jdbcTemplate.queryForObject(SQL_SELECT_TRACK, orderRowMapper, order.getId()).getTrack();
    }

    @Override
    public void update(Order model) {

    }
}
