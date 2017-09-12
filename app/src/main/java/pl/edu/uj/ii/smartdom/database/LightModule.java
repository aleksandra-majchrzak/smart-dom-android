package pl.edu.uj.ii.smartdom.database;

import pl.edu.uj.ii.smartdom.enums.ModuleType;

/**
 * Created by Mohru on 22.07.2017.
 */

public class LightModule extends Module {
    private boolean isOn = false;

    public LightModule() {
    }

    public LightModule(String serverId, String name, Room room) {
        super(serverId, name, room);
    }

    public LightModule(String serverId, String name, Room room, boolean isOn) {
        super(serverId, name, room);

        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    @Override
    public ModuleType getType() {
        return ModuleType.LIGHT_MODULE;
    }
}
