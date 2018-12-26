package repositories;

import models.Image;
import models.Role;
import models.User;

import java.io.InputStream;
import java.sql.SQLException;


public interface UsersRepository extends CrudRepository<User> {
    User findByName(String name);
    Role getRoleByUser(User user);
    void setLogo(User user, String logoName, InputStream logo)  throws SQLException;
    Image getLogo(User user);
}