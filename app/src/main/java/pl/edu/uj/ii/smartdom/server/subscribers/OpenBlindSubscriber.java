package pl.edu.uj.ii.smartdom.server.subscribers;

import okhttp3.ResponseBody;
import pl.edu.uj.ii.smartdom.server.listeners.OpenBlindSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 27.08.2017.
 */

public class OpenBlindSubscriber extends Subscriber<ResponseBody> {

    private OpenBlindSubscriberListener listener;

    public OpenBlindSubscriber(OpenBlindSubscriberListener listener) {
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
    public void onNext(ResponseBody responseBody) {
        listener.onOpenBlindResult();
    }
}
