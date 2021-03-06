package pl.edu.uj.ii.smartdom.server;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import pl.edu.uj.ii.smartdom.server.entities.Blind;
import pl.edu.uj.ii.smartdom.server.entities.Light;
import pl.edu.uj.ii.smartdom.server.entities.LightResponse;
import pl.edu.uj.ii.smartdom.server.entities.LoginResponse;
import pl.edu.uj.ii.smartdom.server.entities.MeteoResponse;
import pl.edu.uj.ii.smartdom.server.entities.RoomResponse;
import pl.edu.uj.ii.smartdom.server.entities.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
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
    public Observable<LightResponse> turnOnLight(@Header("Authorization") String authToken, @Query("login") String login, @Body Light light);

    @POST("api/turnOffLight")
    public Observable<ResponseBody> turnOffLight(@Header("Authorization") String authToken, @Query("login") String login, @Body Light light);

    @POST("api/setStripColor")
    public Observable<ResponseBody> setStripColor(@Header("Authorization") String authToken, @Query("login") String login, @Body Light light);

    @POST("api/setStripBrightness")
    public Observable<ResponseBody> setStripBrightness(@Header("Authorization") String authToken, @Query("login") String login, @Body Light light);

    @GET("api/meteo")
    public Observable<MeteoResponse> getMeteo(@Header("Authorization") String authToken, @Query("login") String login, @Query("param") String param, @Query("moduleServerId") String serverId);

    @POST("api/openBlind")
    public Observable<ResponseBody> openBlind(@Header("Authorization") String authToken, @Query("login") String login, @Body Blind blind);

    @POST("api/closeBlind")
    public Observable<ResponseBody> closeBlind(@Header("Authorization") String authToken, @Query("login") String login, @Body Blind blind);
}
