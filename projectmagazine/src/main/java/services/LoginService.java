package services;


import forms.LoginForm;
import models.User;

import java.util.Optional;

public interface LoginService {
    Optional<String> login(LoginForm loginForm);
    boolean isExistByCookie(String cookieValue);
    User getUserByCookie(String cookieValue);
}
