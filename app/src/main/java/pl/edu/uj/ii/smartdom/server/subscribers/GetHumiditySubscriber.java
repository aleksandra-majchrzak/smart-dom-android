package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.MeteoResponse;
import pl.edu.uj.ii.smartdom.server.listeners.GetHumiditySubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 08.07.2017.
 */

public class GetHumiditySubscriber extends Subscriber<MeteoResponse> {

    private GetHumiditySubscriberListener listener;

    public GetHumiditySubscriber(GetHumiditySubscriberListener listener) {
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
        listener.onHumidityReceived(meteo.humidity);
    }
}
