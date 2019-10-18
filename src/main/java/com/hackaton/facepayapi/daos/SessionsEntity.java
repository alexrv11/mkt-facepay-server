package com.hackaton.facepayapi.daos;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "sessions")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SessionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "session_id", updatable = false, nullable = false)
    private long sessionId;

    @Column(name = "user_name", updatable = false, nullable = false)
    private String userName;

    @Column(name = "logged", updatable = false, nullable = false)
    private Boolean logged;

    @Column(name = "dt_logged_in")
    private Timestamp dtLoggedIn;

    @Column(name = "dt_logged_out")
    private Timestamp dtLoggedOut;

    public SessionsEntity() {
    }

    public SessionsEntity(String userName) {
        this.userName = userName;
        this.logged = true;
        this.dtLoggedIn = new Timestamp(System.currentTimeMillis());
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getLogged() {
        return logged;
    }

    public void setLogged(Boolean logged) {
        this.logged = logged;
    }

    public Timestamp getDtLoggedIn() {
        return dtLoggedIn;
    }

    public void setDtLoggedIn(Timestamp dtLoggedIn) {
        this.dtLoggedIn = dtLoggedIn;
    }

    public Timestamp getDtLoggedOut() {
        return dtLoggedOut;
    }

    public void setDtLoggedOut(Timestamp dtLoggedOut) {
        this.dtLoggedOut = dtLoggedOut;
    }
}
