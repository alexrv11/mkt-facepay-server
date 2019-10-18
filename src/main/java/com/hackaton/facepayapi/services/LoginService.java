package com.hackaton.facepayapi.services;

import com.hackaton.facepayapi.daos.SessionsEntity;
import com.hackaton.facepayapi.daos.UsersEntity;
import com.hackaton.facepayapi.models.login.LoginEntity;
import com.hackaton.facepayapi.models.login.User;
import com.hackaton.facepayapi.models.login.response.LoginResponse;
import com.hackaton.facepayapi.repositories.SessionsRepository;
import com.hackaton.facepayapi.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    SessionsRepository sessionsRepository;

    public LoginEntity validateUser(User userRequest) {

        String userName = userRequest.getUserName();
        String password = userRequest.getPassword();

        Optional<UsersEntity> dbUser = usersRepository.findByUserNameAndPassword(userName, password);

        return dbUser
                .map(usersEntity -> {
                    SessionsEntity sesion = new SessionsEntity(userName);
                    sessionsRepository.saveAndFlush(sesion);
                    LoginEntity res = new LoginEntity();
                    res.setSessionId(sesion.getSessionId());
                    res.setLoginResponse(new LoginResponse(HttpServletResponse.SC_OK, usersEntity.getUserType(), userName));
                    return res;
                })
                .orElseGet(() -> new LoginEntity(new LoginResponse(HttpServletResponse.SC_UNAUTHORIZED), 0));
    }
}
