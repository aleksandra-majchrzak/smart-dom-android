package pl.edu.uj.ii.smartdom.server;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import pl.edu.uj.ii.smartdom.server.entities.Authentication;
import pl.edu.uj.ii.smartdom.server.entities.Door;
import pl.edu.uj.ii.smartdom.server.entities.DoorResponse;
import pl.edu.uj.ii.smartdom.server.entities.LoginResponse;
import pl.edu.uj.ii.smartdom.server.entities.Meteo;
import pl.edu.uj.ii.smartdom.server.entities.RoomResponse;
import pl.edu.uj.ii.smartdom.server.entities.User;
import retrofit2.http.Body;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Mohru on 08.07.2017.
 */

public class MockSmartDomApi implements SmartDomApi {

    private static boolean isOpen = true;

    @Override
    public Observable<LoginResponse> login(@Body User user) {
        return null;
    }

    @Override
    public Observable<ArrayList<RoomResponse>> getRooms(@Body Authentication authentication) {
        return Observable.just(new ArrayList<RoomResponse>());
    }

    @Override
    public Observable<ResponseBody> turnOnLight(@Body Authentication authentication) {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<ResponseBody> turnOffLight(@Body Authentication authentication) {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<ResponseBody> setStripColor(@Body Authentication authentication, @QueryMap Map<String, Integer> rgb) {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<Meteo> getMeteo(@Body Authentication authentication, @Query("param") String param) {

        Meteo result = new Meteo();

        switch (param) {
            case "temperature":
                result.temperature = 23;
                break;
            case "humidity":
                result.humidity = 56;
                break;
            case "co2":
                result.co2 = 10;
                break;
            case "co":
                result.co = 11;
                break;
            case "gas":
                result.gas = 12;
                break;
            default:
                break;
        }

        return Observable.just(result);
    }

    @Override
    public Observable<DoorResponse> openDoor(@Body Authentication authentication, @Body Door door) {
        DoorResponse resp = new DoorResponse();
        resp.isOpen = door.isOpen;
        MockSmartDomApi.isOpen = door.isOpen;
        return Observable.just(resp);
    }

    @Override
    public Observable<DoorResponse> isDoorOpen(@Body Authentication authentication) {
        DoorResponse resp = new DoorResponse();
        resp.isOpen = isOpen;
        return Observable.just(resp);
    }

    private ResponseBody mockResponse = new ResponseBody() {

        @Nullable
        @Override
        public MediaType contentType() {
            return MediaType.parse("json");
        }

        @Override
        public long contentLength() {
            return 0;
        }

        @Override
        public BufferedSource source() {
            return null;
        }
    };
}
