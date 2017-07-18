package pl.edu.uj.ii.smartdom.server.listeners;

/**
 * Created by Mohru on 14.07.2017.
 */

public interface LoginSubscriberListener extends OnErrorListener {
    void onLoginError();

    void onLoginSuccess(String login, String token);
}
