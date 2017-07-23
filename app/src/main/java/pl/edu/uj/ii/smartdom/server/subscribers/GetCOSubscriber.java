package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.MeteoResponse;
import pl.edu.uj.ii.smartdom.server.listeners.GetCOSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 08.07.2017.
 */

public class GetCOSubscriber extends Subscriber<MeteoResponse> {

    private GetCOSubscriberListener listener;

    public GetCOSubscriber(GetCOSubscriberListener listener) {
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
        listener.onCOReceived(meteo.co);
    }
}
