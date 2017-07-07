package pl.edu.uj.ii.smartdom.server;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.edu.uj.ii.smartdom.server.listeners.OnErrorListener;
import pl.edu.uj.ii.smartdom.server.subscribers.SetStripeColorSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.TurnOffLightSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.TurnOnLightSubscriber;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mohru on 26.06.2017.
 */

public class SmartDomService {

    private static SmartDomService service;
    private static SmartDomApi api;

    private static final String BASE_URL = "http://192.168.100.106//";

    private SmartDomService() {
        if (api == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();

            api = retrofit.create(SmartDomApi.class);
        }
    }

    public static SmartDomService getInstance() {
        if (service == null) {
            service = new SmartDomService();
        }
        return service;
    }

    public Subscription turnOnLight(OnErrorListener listener) {
        return api.turnOnLight()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new TurnOnLightSubscriber(listener));
    }

    public Subscription turnOffLight(OnErrorListener listener) {
        return api.turnOffLight()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new TurnOffLightSubscriber(listener));
    }

    public Subscription setStripColor(int r, int g, int b, OnErrorListener listener) {
        Map<String, Integer> rgb = new HashMap<>();
        rgb.put("red", r);
        rgb.put("green", g);
        rgb.put("blue", b);

        return api.setStripColor(rgb)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SetStripeColorSubscriber(listener));
    }
}