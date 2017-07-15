package pl.edu.uj.ii.smartdom.server.entities;

/**
 * Created by Mohru on 14.07.2017.
 */

public class LoginResponse {
    public String userLogin;
    public String userToken;

    public LoginResponse(String userLogin, String userToken) {
        this.userLogin = userLogin;
        this.userToken = userToken;
    }
}
