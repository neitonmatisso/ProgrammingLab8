
package ru.nik.serverPackage.accounts;


import org.hibernate.annotations.GenericGenerator;


import ru.nik.connectionPackage.Connection;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String pass;
    @Enumerated(EnumType.STRING)
    @Column (name = "priority")
    private UserPriority priority;
    @Transient
    private Connection connection;
    @Transient
    private String token;
    @Column(name = "solt")
    private String solt;
    public User(){

    }
    public User(String login, String pass, UserPriority priority, boolean isConnect, Connection connection) {
        this.login = login;
        this.pass = pass;
        this.priority = priority;
        this.connection = connection;
    }
    public User(String login, String pass, UserPriority priority, boolean isConnect) {
        this.login = login;
        this.pass = pass;
        this.priority = priority;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", priority=" + priority +
                ", connection=" + connection +
                ", token='" + token + '\'' +
                '}';
    }

    public String getSolt() {
        return solt;
    }

    public void setSolt(String solt) {
        this.solt = solt;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UserPriority getPriority() {
        return priority;
    }

    public void setPriority(UserPriority priority) {
        this.priority = priority;
    }


}
