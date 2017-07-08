package pl.edu.uj.ii.smartdom.server.listeners;

/**
 * Created by Mohru on 08.07.2017.
 */

public interface GetHumiditySubscriberListener extends OnErrorListener {

    void onHumidityReceived(double humidity);
}
