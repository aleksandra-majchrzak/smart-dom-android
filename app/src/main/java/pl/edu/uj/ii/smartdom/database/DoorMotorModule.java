package pl.edu.uj.ii.smartdom.database;

/**
 * Created by Mohru on 22.07.2017.
 */

public class DoorMotorModule extends Module {
    private boolean isOpen = false;

    public DoorMotorModule(String serverId, String name, String roomServerId) {
        super(serverId, name, roomServerId);
    }

    public DoorMotorModule(String serverId, String name, String roomServerId, boolean isOpen) {
        super(serverId, name, roomServerId);

        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
