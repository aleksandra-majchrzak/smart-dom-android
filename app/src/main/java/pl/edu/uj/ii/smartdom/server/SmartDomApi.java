package pl.edu.uj.ii.smartdom.server;

import java.util.Map;

import okhttp3.ResponseBody;
import pl.edu.uj.ii.smartdom.server.entities.Meteo;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Mohru on 26.06.2017.
 */

public interface SmartDomApi {

    //todo w tych metodach powinnam tez miec odniesienie do konkretnego kontrolera- jakis id kontrolera czy cos

    @GET("/turnOnLight")
    public Observable<ResponseBody> turnOnLight();

    @GET("/turnOffLight")
    public Observable<ResponseBody> turnOffLight();

    @GET("/setStripColor")
    public Observable<ResponseBody> setStripColor(@QueryMap Map<String, Integer> rgb);

    @GET("/meteo")
    public Observable<Meteo> getMeteo(@Query("param") String param);

}
