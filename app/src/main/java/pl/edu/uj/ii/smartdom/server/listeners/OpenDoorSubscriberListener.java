package pl.edu.uj.ii.smartdom.server.listeners;

/**
 * Created by Mohru on 08.07.2017.
 */

public interface OpenDoorSubscriberListener extends OnErrorListener {

    void onOpenDoorResult(boolean isDoorOpen);
}
