package pl.edu.uj.ii.smartdom.server;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import pl.edu.uj.ii.smartdom.server.entities.Door;
import pl.edu.uj.ii.smartdom.server.entities.DoorResponse;
import pl.edu.uj.ii.smartdom.server.entities.LoginResponse;
import pl.edu.uj.ii.smartdom.server.entities.Meteo;
import pl.edu.uj.ii.smartdom.server.entities.RoomResponse;
import pl.edu.uj.ii.smartdom.server.entities.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    public Observable<ArrayList<RoomResponse>> getRooms(@Header("Authorization") String authToken, @Query("login") String login);

    //todo w tych metodach powinnam tez miec odniesienie do konkretnego kontrolera- jakis id kontrolera czy cos

    @POST("api/turnOnLight")
    public Observable<ResponseBody> turnOnLight(@Header("Authorization") String authToken, @Query("login") String login);

    @POST("api/turnOffLight")
    public Observable<ResponseBody> turnOffLight(@Header("Authorization") String authToken, @Query("login") String login);

    @POST("api/setStripColor")
    public Observable<ResponseBody> setStripColor(@Header("Authorization") String authToken, @Query("login") String login, @QueryMap Map<String, Integer> rgb);

    @GET("api/meteo")
    public Observable<Meteo> getMeteo(@Header("Authorization") String authToken, @Query("login") String login, @Query("param") String param);

    @POST("api/openDoor")
    public Observable<DoorResponse> openDoor(@Header("Authorization") String authToken, @Query("login") String login, @Body Door door);

    @GET("api/openDoor")
    public Observable<DoorResponse> isDoorOpen(@Header("Authorization") String authToken, @Query("login") String login);

}
