package pl.edu.uj.ii.smartdom.server;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Mohru on 26.06.2017.
 */

public interface SmartDomApi {

    @GET("/turnOnLight")
    public Observable<ResponseBody> turnOnLight();

    @GET("/turnOffLight")
    public Observable<ResponseBody> turnOffLight();

    @GET("/setStripColor")
    public Observable<ResponseBody> setStripColor(@QueryMap Map<String, Integer> rgb);
}
