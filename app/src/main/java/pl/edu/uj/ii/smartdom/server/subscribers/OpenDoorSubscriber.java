package pl.edu.uj.ii.smartdom.server.subscribers;

import pl.edu.uj.ii.smartdom.server.entities.DoorResponse;
import pl.edu.uj.ii.smartdom.server.listeners.OpenDoorSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 08.07.2017.
 */

public class OpenDoorSubscriber extends Subscriber<DoorResponse> {

    private OpenDoorSubscriberListener listener;

    public OpenDoorSubscriber(OpenDoorSubscriberListener listener) {
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
        listener.onOpenDoorResult(response.isOpen);
    }
}
