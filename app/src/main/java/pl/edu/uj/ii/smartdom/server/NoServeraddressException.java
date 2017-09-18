package pl.edu.uj.ii.smartdom.server;

/**
 * Created by Mohru on 19.08.2017.
 */

public class NoServerAddressException extends Exception {
    NoServerAddressException() {
        super("No server address set. Did you forget to set address in app settings?");
    }
}
