package pl.edu.uj.ii.smartdom.server;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import pl.edu.uj.ii.smartdom.server.entities.Blind;
import pl.edu.uj.ii.smartdom.server.entities.Light;
import pl.edu.uj.ii.smartdom.server.entities.LightResponse;
import pl.edu.uj.ii.smartdom.server.entities.LoginResponse;
import pl.edu.uj.ii.smartdom.server.entities.MeteoResponse;
import pl.edu.uj.ii.smartdom.server.entities.RoomResponse;
import pl.edu.uj.ii.smartdom.server.entities.User;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Query;
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
    public Observable<ArrayList<RoomResponse>> getRooms(@Header("Authorization") String authToken, @Query("login") String login) {
        return Observable.just(new ArrayList<RoomResponse>());
    }

    @Override
    public Observable<LightResponse> turnOnLight(@Header("Authorization") String authToken, @Query("login") String login, @Body Light light) {
        LightResponse resp = new LightResponse();
        return Observable.just(resp);
    }

    @Override
    public Observable<ResponseBody> turnOffLight(@Header("Authorization") String authToken, @Query("login") String login, @Body Light light) {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<ResponseBody> setStripColor(@Header("Authorization") String authToken, @Query("login") String login, @Body Light light) {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<ResponseBody> setStripBrightness(@Header("Authorization") String authToken, @Query("login") String login, @Body Light light) {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<MeteoResponse> getMeteo(@Header("Authorization") String authToken, @Query("login") String loginn, @Query("param") String param, @Query("moduleServerId") String serverId) {

        MeteoResponse result = new MeteoResponse();

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
    public Observable<ResponseBody> openBlind(@Header("Authorization") String authToken, @Query("login") String login, @Body Blind blind) {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<ResponseBody> closeBlind(@Header("Authorization") String authToken, @Query("login") String login, @Body Blind blind) {
        return Observable.just(mockResponse);
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
