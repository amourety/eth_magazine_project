package services;

import models.Auth;
import models.User;

public interface AuthService {
    Auth findByCookieValue(String cookieValue);
    void deleteCookieByUserId(User user);
}
