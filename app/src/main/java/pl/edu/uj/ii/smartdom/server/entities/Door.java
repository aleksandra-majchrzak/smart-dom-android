package pl.edu.uj.ii.smartdom.server.entities;

/**
 * Created by Mohru on 15.07.2017.
 */

public class Door extends Module {
    private boolean isOpen;

    public Door(String serverId, boolean isOpen) {
        super(serverId);
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
