package services;

import forms.UserForm;
import models.Image;
import models.Role;
import models.User;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public interface UsersService {
    void addUser(UserForm userForm);
    List<User> getUsers();
    User getCurrentUser(Cookie[] cookies);
    Role getRoleByUser(User user);
    User findByName(String username);
    User find(Long id);
    void setLogo(User user, String logoName, InputStream logo) throws SQLException;
    Image getLogo(User user);

}