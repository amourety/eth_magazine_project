package repositories;

import models.Auth;
import models.User;


import java.util.Optional;

public interface AuthRepository extends CrudRepository<Auth> {
    Optional<Auth> findByCookieValue(String cookieValue);
    void deleteCookieByUserId(User user);
}
