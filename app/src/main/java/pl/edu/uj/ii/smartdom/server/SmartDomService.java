package pl.edu.uj.ii.smartdom.server;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.edu.uj.ii.smartdom.database.BlindMotorModule;
import pl.edu.uj.ii.smartdom.database.DoorMotorModule;
import pl.edu.uj.ii.smartdom.database.LightModule;
import pl.edu.uj.ii.smartdom.database.MeteoModule;
import pl.edu.uj.ii.smartdom.server.entities.Authentication;
import pl.edu.uj.ii.smartdom.server.entities.Blind;
import pl.edu.uj.ii.smartdom.server.entities.Door;
import pl.edu.uj.ii.smartdom.server.entities.Light;
import pl.edu.uj.ii.smartdom.server.entities.User;
import pl.edu.uj.ii.smartdom.server.listeners.CloseBlindSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetCO2SubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetCOSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetGasSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetHumiditySubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetRoomsSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetTempSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.IsDoorOpenSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.LoginSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.OnErrorListener;
import pl.edu.uj.ii.smartdom.server.listeners.OpenBlindSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.OpenDoorSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.TurnOffLightSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.TurnOnSubscriberListener;
import pl.edu.uj.ii.smartdom.server.subscribers.CloseBlindSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetCO2Subscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetCOSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetGasSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetHumiditySubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetRoomsSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.GetTempSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.IsDoorOpenSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.LoginSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.OpenBlindSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.OpenDoorSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.SetStripBrightnessSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.SetStripeColorSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.TurnOffLightSubscriber;
import pl.edu.uj.ii.smartdom.server.subscribers.TurnOnLightSubscriber;
import pl.edu.uj.ii.smartdom.utils.SSLUtils;
import pl.edu.uj.ii.smartdom.utils.StringPatterns;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mohru on 26.06.2017.
 */

public class SmartDomService {

    private static SmartDomService service;
    private static SmartDomApi api;

    //private static final String BASE_URL = "http://192.168.100.106//";
    //private static String BASE_URL = "http://192.168.0.52:81/";
    private static String BASE_URL = "";

    private SmartDomService() {
    }

    public static SmartDomService getInstance() {
        if (service == null) {
            service = new SmartDomService();
        }
        if (api == null && !TextUtils.isEmpty(BASE_URL)) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (SSLUtils.getSslContext() != null && SSLUtils.getTrustManager() != null)
                builder.sslSocketFactory(SSLUtils.getSslContext().getSocketFactory(), SSLUtils.getTrustManager());

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = builder.addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();

            api = retrofit.create(SmartDomApi.class);
            //api = new MockSmartDomApi();
        }
        return service;
    }

    public static void setServerAddress(String serverAddress) {
        if (StringPatterns.IP_ADDRESS_WITH_PORT.matcher(serverAddress).matches())
            BASE_URL = "https://" + serverAddress + "/";
        else
            BASE_URL = "";
    }

    public static void resetService() {
        api = null;
        service = null;
    }

    public Subscription login(String login, String password, LoginSubscriberListener listener) {
        if (hasBaseURL()) {
            User user = new User(login, password);
            return api.login(user)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new LoginSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription getRooms(GetRoomsSubscriberListener listener, Authentication authen) {
        if (hasBaseURL()) {
            return api.getRooms(authen.getToken(), authen.getUsername())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new GetRoomsSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription turnOnLight(TurnOnSubscriberListener listener, Authentication authen, LightModule lightModule) {
        if (hasBaseURL()) {
            return api.turnOnLight(authen.getToken(), authen.getUsername(), new Light(lightModule.getServerId(), null))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new TurnOnLightSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription turnOffLight(TurnOffLightSubscriberListener listener, Authentication authen, LightModule lightModule) {
        if (hasBaseURL()) {
            return api.turnOffLight(authen.getToken(), authen.getUsername(), new Light(lightModule.getServerId(), null))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new TurnOffLightSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription setStripColor(int r, int g, int b, OnErrorListener listener, Authentication authen, LightModule lightModule) {
        if (hasBaseURL()) {
            Map<String, Integer> rgb = new HashMap<>();
            rgb.put("red", r);
            rgb.put("green", g);
            rgb.put("blue", b);

            return api.setStripColor(authen.getToken(), authen.getUsername(), new Light(lightModule.getServerId(), rgb))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SetStripeColorSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription setStripBrightness(int brightness, OnErrorListener listener, Authentication authen, LightModule lightModule) {
        if (hasBaseURL()) {
            return api.setStripBrightness(authen.getToken(), authen.getUsername(), new Light(lightModule.getServerId(), brightness))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SetStripBrightnessSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription getTemperature(GetTempSubscriberListener listener, Authentication authen, MeteoModule module) {
        if (hasBaseURL()) {
            return api.getMeteo(authen.getToken(), authen.getUsername(), "temperature", module.getServerId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new GetTempSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription getHumidity(GetHumiditySubscriberListener listener, Authentication authen, MeteoModule module) {
        if (hasBaseURL()) {
            return api.getMeteo(authen.getToken(), authen.getUsername(), "humidity", module.getServerId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new GetHumiditySubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription getCO(GetCOSubscriberListener listener, Authentication authen, MeteoModule module) {
        if (hasBaseURL()) {
            return api.getMeteo(authen.getToken(), authen.getUsername(), "co", module.getServerId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new GetCOSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription getCO2(GetCO2SubscriberListener listener, Authentication authen, MeteoModule module) {
        if (hasBaseURL()) {
            return api.getMeteo(authen.getToken(), authen.getUsername(), "co2", module.getServerId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new GetCO2Subscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription getGas(GetGasSubscriberListener listener, Authentication authen, MeteoModule module) {
        if (hasBaseURL()) {
            return api.getMeteo(authen.getToken(), authen.getUsername(), "gas", module.getServerId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new GetGasSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription openDoor(DoorMotorModule door, OpenDoorSubscriberListener listener, Authentication authen) {
        if (hasBaseURL()) {
            return api.openDoor(authen.getToken(), authen.getUsername(), new Door(door.getServerId(), !door.isOpen()))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new OpenDoorSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription isDoorOpen(DoorMotorModule door, IsDoorOpenSubscriberListener listener, Authentication authen) {
        if (hasBaseURL()) {
            return api.isDoorOpen(authen.getToken(), authen.getUsername(), door.getServerId())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new IsDoorOpenSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription openBlind(BlindMotorModule blind, OpenBlindSubscriberListener listener, Authentication authen) {
        if (hasBaseURL()) {
            return api.openBlind(authen.getToken(), authen.getUsername(), new Blind(blind.getServerId(), blind.shouldStart()))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new OpenBlindSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    public Subscription closeBlind(BlindMotorModule blind, CloseBlindSubscriberListener listener, Authentication authen) {
        if (hasBaseURL()) {
            return api.closeBlind(authen.getToken(), authen.getUsername(), new Blind(blind.getServerId(), blind.shouldStart()))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CloseBlindSubscriber(listener));
        } else {
            listener.onServerNotSet();
            return null;
        }
    }

    private boolean hasBaseURL() {
        return !TextUtils.isEmpty(BASE_URL);
    }
}
