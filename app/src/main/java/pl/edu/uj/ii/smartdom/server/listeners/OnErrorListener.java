package pl.edu.uj.ii.smartdom.server.listeners;

/**
 * Created by Mohru on 04.07.2017.
 */

public interface OnErrorListener {
    void onConnectionError();
    void onServerNotSet();
}
