package pl.edu.uj.ii.smartdom.server.listeners;

/**
 * Created by Mohru on 08.07.2017.
 */

public interface GetCOSubscriberListener extends OnErrorListener {

    void onCOReceived(double co);
}
