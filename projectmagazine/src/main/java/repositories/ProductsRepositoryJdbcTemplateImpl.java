package repositories;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import models.Product;

import javax.sql.DataSource;
import java.util.List;

public class ProductsRepositoryJdbcTemplateImpl implements ProductsRepository {


    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_SEARCH = "select * from product where product.name alike %?";

    //language=SQL
    private static final String SQL_SELECT_PRODUCT_BY_ID =
            "select * from product where id = ?";

    //language=SQL
    private static final String SQL_SELECT_ALL_PRODUCTS =
            "select * from product";

    //language=SQL
    private static final String SQL_INSERT_PRODUCT =
            "insert into product(name,price,img) values (?,?,?)";

    //language=SQL
    private static final String SQL_SELECT_BY_NAME =
            "select * from product where name = ?";

    //language=SQL
    private static final String SQL_DELETE_PRODUCT_BY_ID =
            "DELETE from product where product.id = ? cascade";



    private RowMapper<Product> productRowMapper = (resultSet, i) -> Product.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .price(resultSet.getString("price"))
            .img(resultSet.getString("img"))
            .build();

    public ProductsRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Product> findAllByTitleSearch(String title) {
        String SQL_SEARCH = "select * from product where product.name ilike ?";
        return jdbcTemplate.query(SQL_SEARCH, productRowMapper, "%" + title + "%");
    }


    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_PRODUCTS, productRowMapper);
    }

    @Override
    public Product find(Long id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_PRODUCT_BY_ID,
                productRowMapper, id);
    }

    @Override
    public void save(Product model) {
        jdbcTemplate.update(SQL_INSERT_PRODUCT, model.getName(), model.getPrice(),model.getImg());
    }


    @Override
    public void update(Product model) {

    }

    @Override
    public Product findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, productRowMapper, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Product> findAllByTitle(String title) {
        return jdbcTemplate.query(SQL_SEARCH, productRowMapper, "%" + title + "%");
    }

    @Override
    public void deleteById(Long id){
        String SQL_DELETE2 = "DELETE from product where product.id = ?";
        String SQL_DELETE1 = "DELETE from product_basket where product_id = ?";

        jdbcTemplate.update(SQL_DELETE1,id);
        jdbcTemplate.update(SQL_DELETE2,id);
    }
}