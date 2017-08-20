package pl.edu.uj.ii.smartdom.server.subscribers;

import android.util.Log;

import okhttp3.ResponseBody;
import pl.edu.uj.ii.smartdom.server.listeners.TurnOffLightSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 26.06.2017.
 */

public class TurnOffLightSubscriber extends Subscriber<ResponseBody> {

    private TurnOffLightSubscriberListener listener;

    public TurnOffLightSubscriber(TurnOffLightSubscriberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.d("TurnOffLightSub", "onError: " + e.getMessage());
        listener.onConnectionError();
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        Log.d("TurnOffLightSub", "onNext ");
        listener.onLightOff();
    }
}
