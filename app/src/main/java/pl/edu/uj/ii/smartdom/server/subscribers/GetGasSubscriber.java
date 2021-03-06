package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.MeteoResponse;
import pl.edu.uj.ii.smartdom.server.listeners.GetGasSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 08.07.2017.
 */

public class GetGasSubscriber extends Subscriber<MeteoResponse> {

    private GetGasSubscriberListener listener;

    public GetGasSubscriber(GetGasSubscriberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        listener.onConnectionError();
    }

    @Override
    public void onNext(MeteoResponse meteo) {
        listener.onGasReceived(meteo.gas);
    }
}
