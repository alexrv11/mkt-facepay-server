package com.hackaton.facepayapi.services;

import com.hackaton.facepayapi.db.emulator.DB;
import com.hackaton.facepayapi.models.login.User;
import com.hackaton.facepayapi.models.login.response.LoginResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import static com.hackaton.facepayapi.utils.Constants.TYPE_PAYER;
import static com.hackaton.facepayapi.utils.Constants.TYPE_SELLER;

@Service
public class LoginService {


    public LoginResponse validateUser(User user) {
        if ( DB.getInstance().isValidPayer(user.getUserName(), user.getPassword()) )
            return new LoginResponse(HttpServletResponse.SC_OK, TYPE_PAYER);

        if ( DB.getInstance().isValidSeller(user.getUserName(), user.getPassword()) )
            return new LoginResponse(HttpServletResponse.SC_OK, TYPE_SELLER);

        return new LoginResponse(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
