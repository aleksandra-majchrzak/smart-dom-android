package pl.edu.uj.ii.smartdom.server;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.edu.uj.ii.smartdom.server.entities.User;
import pl.edu.uj.ii.smartdom.server.listeners.GetCO2SubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetCOSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetGasSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetHumiditySubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetTempSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.IsDoorOpenSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.LoginSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.OnErrorListener;
import pl.edu.uj.ii.smartdom.server.listeners.OpenDoorSubscriberListener;
import pl.edu.uj.ii.smartdom.server.subscribers.GetCO2Subscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetCOSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetGasSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetHumiditySubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetTempSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.IsDoorOpenSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.LoginSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.OpenDoorSubscriber;
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
            //api = new MockSmartDomApi();
        }
    }

    public static SmartDomService getInstance() {
        if (service == null) {
            service = new SmartDomService();
        }
        return service;
    }

    public Subscription login(String login, String password, LoginSubscriberListener listener) {
        User user = new User(login, password);

        return api.login(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoginSubscriber(listener));
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

    public Subscription getTemperature(GetTempSubscriberListener listener) {
        return api.getMeteo("temperature")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GetTempSubscriber(listener));
    }

    public Subscription getHumidity(GetHumiditySubscriberListener listener) {
        return api.getMeteo("humidity")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GetHumiditySubscriber(listener));
    }

    public Subscription getCO(GetCOSubscriberListener listener) {
        return api.getMeteo("co")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GetCOSubscriber(listener));
    }

    public Subscription getCO2(GetCO2SubscriberListener listener) {
        return api.getMeteo("co2")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GetCO2Subscriber(listener));
    }

    public Subscription getGas(GetGasSubscriberListener listener) {
        return api.getMeteo("gas")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new GetGasSubscriber(listener));
    }

    public Subscription openDoor(boolean isOpen, OpenDoorSubscriberListener listener) {
        return api.openDoor(isOpen)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OpenDoorSubscriber(listener));

    }

    public Subscription isDoorOpen(IsDoorOpenSubscriberListener listener) {
        return api.isDoorOpen()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new IsDoorOpenSubscriber(listener));
    }
}
