package services;

import models.Auth;
import models.User;
import repositories.AuthRepository;

public class AuthServiceImpl implements AuthService {
    private AuthRepository authRepository;

    public AuthServiceImpl(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Override
    public Auth findByCookieValue(String cookieValue) {
        return authRepository.findByCookieValue(cookieValue).get();
    }

    @Override
    public void deleteCookieByUserId(User user) {
        authRepository.deleteCookieByUserId(user);
    }
}
