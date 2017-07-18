package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.LoginResponse;
import pl.edu.uj.ii.smartdom.server.listeners.LoginSubscriberListener;
import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Created by Mohru on 14.07.2017.
 */

public class LoginSubscriber extends Subscriber<LoginResponse> {

    private LoginSubscriberListener listener;

    public LoginSubscriber(LoginSubscriberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {


    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            listener.onLoginError();
        } else {
            listener.onConnectionError();
        }
    }

    @Override
    public void onNext(LoginResponse loginResponse) {
        listener.onLoginSuccess(loginResponse.userLogin, loginResponse.userToken);
    }
}
