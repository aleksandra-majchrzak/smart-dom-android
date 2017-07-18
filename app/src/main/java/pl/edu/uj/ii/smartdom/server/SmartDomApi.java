package pl.edu.uj.ii.smartdom.server;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import pl.edu.uj.ii.smartdom.server.entities.Authentication;
import pl.edu.uj.ii.smartdom.server.entities.Door;
import pl.edu.uj.ii.smartdom.server.entities.DoorResponse;
import pl.edu.uj.ii.smartdom.server.entities.LoginResponse;
import pl.edu.uj.ii.smartdom.server.entities.Meteo;
import pl.edu.uj.ii.smartdom.server.entities.RoomResponse;
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

    @POST("api/login")
    public Observable<LoginResponse> login(@Body User user);

    @GET("api/rooms")
    public Observable<ArrayList<RoomResponse>> getRooms(@Body Authentication authentication);

    //todo w tych metodach powinnam tez miec odniesienie do konkretnego kontrolera- jakis id kontrolera czy cos

    @POST("api/turnOnLight")
    public Observable<ResponseBody> turnOnLight(@Body Authentication authentication);

    @POST("api/turnOffLight")
    public Observable<ResponseBody> turnOffLight(@Body Authentication authentication);

    @POST("api/setStripColor")
    public Observable<ResponseBody> setStripColor(@Body Authentication authentication, @QueryMap Map<String, Integer> rgb);

    @GET("api/meteo")
    public Observable<Meteo> getMeteo(@Body Authentication authentication, @Query("param") String param);

    @POST("api/openDoor")
    public Observable<DoorResponse> openDoor(@Body Authentication authentication, @Body Door door);

    @GET("api/openDoor")
    public Observable<DoorResponse> isDoorOpen(@Body Authentication authentication);

}
