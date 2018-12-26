package repositories;

import models.Image;
import models.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {


    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_SELECT_USER_BY_ID =
            "select * from users where id = ?";

    //language=SQL
    private static final String SQL_SELECT_ALL_USERS =
            "select * from users";

    //language=SQL
    private static final String SQL_INSERT_USER =
            "insert into users(name,password,email,role,first_name,surname) values (?,?,?,2,?,?)";

    //language=SQL
    private static final String SQL_SELECT_BY_NAME =
            "select * from users where name = ?";

    //language=SQL
    private static final String SQL_SELECT_USER_ROLE =
            "select roles.id,roles.name from roles join users u on roles.id = u.role where u.id = ?;";

    //language=SQL
    private static String SQL_UPDATE_LOGO = "update users SET logo = ?,logo_name = ? where id = ?";

    private RowMapper<Role> roleRowMapper = (resultSet, i) -> Role.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name")).build();

    private RowMapper<User> userRowMapper = (resultSet, i) -> User.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .surname(resultSet.getString("surname"))
            .first_name(resultSet.getString("first_name"))
            .email(resultSet.getString("email"))
            .build();

    private RowMapper<User> userRowMapper2 = (resultSet, i) -> User.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("name"))
            .passwordHash(resultSet.getString("password"))
            .build();

    public RowMapper<Image> imageRowMapper = (resultSet, i) -> Image.builder()
            .imageData(resultSet.getBytes("logo")).imageFileName(resultSet.getString("logo_name")).build();

    PreparedStatement updateLogoStatement;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_USERS, userRowMapper);
    }

    @Override
    public User find(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_USER_BY_ID,
                    userRowMapper, id);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void save(User model) {
        jdbcTemplate.update(SQL_INSERT_USER, model.getName(), model.getPasswordHash(),model.getEmail(),model.getFirst_name(),model.getSurname());
    }


    @Override
    public void update(User model) {

    }

    @Override
    public User findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, userRowMapper2, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Role getRoleByUser(User user){
        return jdbcTemplate.queryForObject(SQL_SELECT_USER_ROLE,roleRowMapper,user.getId());
    }

    @Override
    public void setLogo(User user, String logoName, InputStream logo) throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("di9cbdy4");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/db_11_702");
        updateLogoStatement = dataSource.getConnection().prepareStatement(SQL_UPDATE_LOGO);
        updateLogoStatement.setBinaryStream(1, logo);
        updateLogoStatement.setString(2, logoName);
        updateLogoStatement.setLong(3, user.getId());
        updateLogoStatement.executeUpdate();
    }

    @Override
    public Image getLogo(User user) {
        //language=SQL
        String SQL = "SELECT * FROM users where id = ?";
        return jdbcTemplate.queryForObject(SQL,imageRowMapper,user.getId());
    }

}
