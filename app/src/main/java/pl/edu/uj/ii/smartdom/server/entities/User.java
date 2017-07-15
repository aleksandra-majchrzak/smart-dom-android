package pl.edu.uj.ii.smartdom.server.entities;

/**
 * Created by Mohru on 14.07.2017.
 */

public class User {
    String login;
    String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
