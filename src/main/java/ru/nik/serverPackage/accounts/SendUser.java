package ru.nik.serverPackage.accounts;

import ru.nik.connectionPackage.Connection;

import javax.persistence.*;

public class SendUser {
    private int ID;
    private String login;
    private UserPriority priority;
    private String token;

    public SendUser(User user){
        ID = user.getID();
        login = user.getLogin();
        priority = user.getPriority();
        token = user.getToken();
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserPriority getPriority() {
        return priority;
    }

    public void setPriority(UserPriority priority) {
        this.priority = priority;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }




}
