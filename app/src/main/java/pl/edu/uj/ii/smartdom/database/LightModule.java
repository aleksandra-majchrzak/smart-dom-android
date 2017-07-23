package pl.edu.uj.ii.smartdom.database;

/**
 * Created by Mohru on 22.07.2017.
 */

public class LightModule extends Module {
    private boolean isOn = false;

    public LightModule(String serverId, String name, String roomServerId) {
        super(serverId, name, roomServerId);
    }

    public LightModule(String serverId, String name, String roomServerId, boolean isOn) {
        super(serverId, name, roomServerId);

        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
