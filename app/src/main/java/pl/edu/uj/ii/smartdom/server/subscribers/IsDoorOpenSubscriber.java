package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.DoorResponse;
import pl.edu.uj.ii.smartdom.server.listeners.IsDoorOpenSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 08.07.2017.
 */

public class IsDoorOpenSubscriber extends Subscriber<DoorResponse> {

    private IsDoorOpenSubscriberListener listener;

    public IsDoorOpenSubscriber(IsDoorOpenSubscriberListener listener) {
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
    public void onNext(DoorResponse response) {
        listener.onIsDoorOpenReceived(response.isOpen);
    }
}
