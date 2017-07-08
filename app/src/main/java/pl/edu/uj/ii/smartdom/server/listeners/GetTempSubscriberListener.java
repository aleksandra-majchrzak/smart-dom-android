package pl.edu.uj.ii.smartdom.server.listeners;

/**
 * Created by Mohru on 08.07.2017.
 */

public interface GetTempSubscriberListener extends OnErrorListener {

    void onTemperatureReceived(double temperature);
}
