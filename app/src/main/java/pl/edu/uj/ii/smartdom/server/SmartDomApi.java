package pl.edu.uj.ii.smartdom.server;

import java.util.Map;

import okhttp3.ResponseBody;
import pl.edu.uj.ii.smartdom.server.entities.DoorResponse;
import pl.edu.uj.ii.smartdom.server.entities.LoginResponse;
import pl.edu.uj.ii.smartdom.server.entities.Meteo;
import pl.edu.uj.ii.smartdom.server.entities.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Mohru on 26.06.2017.
 */

public interface SmartDomApi {

    @POST("/login")
    public Observable<LoginResponse> login(@Body User user);

    //todo w tych metodach powinnam tez miec odniesienie do konkretnego kontrolera- jakis id kontrolera czy cos

    @GET("/turnOnLight")
    public Observable<ResponseBody> turnOnLight();

    @GET("/turnOffLight")
    public Observable<ResponseBody> turnOffLight();

    @GET("/setStripColor")
    public Observable<ResponseBody> setStripColor(@QueryMap Map<String, Integer> rgb);

    @GET("/meteo")
    public Observable<Meteo> getMeteo(@Query("param") String param);

    @POST("/openDoor")
    public Observable<DoorResponse> openDoor(@Body Boolean isOpen);

    @GET("/openDoor")
    public Observable<DoorResponse> isDoorOpen();

}
