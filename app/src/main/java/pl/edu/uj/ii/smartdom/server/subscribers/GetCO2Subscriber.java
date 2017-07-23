package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.MeteoResponse;
import pl.edu.uj.ii.smartdom.server.listeners.GetCO2SubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 08.07.2017.
 */

public class GetCO2Subscriber extends Subscriber<MeteoResponse> {

    private GetCO2SubscriberListener listener;

    public GetCO2Subscriber(GetCO2SubscriberListener listener) {
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
        listener.onCO2Received(meteo.co2);
    }
}
