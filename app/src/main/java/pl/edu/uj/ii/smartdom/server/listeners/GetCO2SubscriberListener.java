package pl.edu.uj.ii.smartdom.server.listeners;

/**
 * Created by Mohru on 08.07.2017.
 */

public interface GetCO2SubscriberListener extends OnErrorListener {

    void onCO2Received(double co2);
}
