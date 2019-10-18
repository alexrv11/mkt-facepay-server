package com.hackaton.facepayapi.services;

import com.hackaton.facepayapi.daos.UsersEntity;
import com.hackaton.facepayapi.models.login.User;
import com.hackaton.facepayapi.models.login.response.LoginResponse;
import com.hackaton.facepayapi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UsersRepository usersRepository;

    public LoginResponse validateUser(User userRequest) {

        String userName = userRequest.getUserName();
        String password = userRequest.getPassword();

        Optional<UsersEntity> dbUser = usersRepository.findByUserNameAndPassword(userName, password);

        return dbUser
                .map(usersEntity -> new LoginResponse(HttpServletResponse.SC_OK, usersEntity.getUserType()))
                .orElseGet(() -> new LoginResponse(HttpServletResponse.SC_UNAUTHORIZED));
    }
}
