package pl.edu.uj.ii.smartdom.server.entities;

/**
 * Created by Mohru on 17.07.2017.
 */

public class Authentication {
    private String username;
    private String token;
    private String serverAddress;

    public Authentication(String username, String token, String serverAddress) {
        this.username = username;
        this.token = token;
        this.serverAddress = serverAddress;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getServerAddress() {
        return serverAddress;
    }
}
