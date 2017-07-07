package pl.edu.uj.ii.smartdom.server.subscribers;

import android.util.Log;

import okhttp3.ResponseBody;
import pl.edu.uj.ii.smartdom.server.listeners.OnErrorListener;
import rx.Subscriber;

/**
 * Created by Mohru on 26.06.2017.
 */

public class TurnOnLightSubscriber extends Subscriber<ResponseBody> {

    private OnErrorListener listener;

    public TurnOnLightSubscriber(OnErrorListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.d("TurnOnLightSub", "onError: " + e.getMessage());
        listener.onConnectionError();
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        Log.d("TurnOnLightSub", "onNext ");
    }
}
