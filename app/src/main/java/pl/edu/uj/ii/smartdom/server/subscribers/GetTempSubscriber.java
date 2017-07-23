package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.MeteoResponse;
import pl.edu.uj.ii.smartdom.server.listeners.GetTempSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 08.07.2017.
 */

public class GetTempSubscriber extends Subscriber<MeteoResponse> {

    private GetTempSubscriberListener listener;

    public GetTempSubscriber(GetTempSubscriberListener listener) {
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
        listener.onTemperatureReceived(meteo.temperature);
    }
}
