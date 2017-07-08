package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.Meteo;
import pl.edu.uj.ii.smartdom.server.listeners.GetCOSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 08.07.2017.
 */

public class GetCOSubscriber extends Subscriber<Meteo> {

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
    public void onNext(Meteo meteo) {
        listener.onCOReceived(meteo.co);
    }
}
