package pl.edu.uj.ii.smartdom.server.entities;

/**
 * Created by Mohru on 17.07.2017.
 */

public class Authentication {
    private String username;
    private String token;

    public Authentication(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
