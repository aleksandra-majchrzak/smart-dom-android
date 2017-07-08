package pl.edu.uj.ii.smartdom.server;

import android.support.annotation.Nullable;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import pl.edu.uj.ii.smartdom.server.entities.Meteo;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Mohru on 08.07.2017.
 */

public class MockSmartDomApi implements SmartDomApi {
    @Override
    public Observable<ResponseBody> turnOnLight() {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<ResponseBody> turnOffLight() {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<ResponseBody> setStripColor(@QueryMap Map<String, Integer> rgb) {
        return Observable.just(mockResponse);
    }

    @Override
    public Observable<Meteo> getMeteo(@Query("param") String param) {

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
