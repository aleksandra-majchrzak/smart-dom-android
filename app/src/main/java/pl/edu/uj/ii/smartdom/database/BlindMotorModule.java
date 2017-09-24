package pl.edu.uj.ii.smartdom.database;

import pl.edu.uj.ii.smartdom.enums.ConnectionType;
import pl.edu.uj.ii.smartdom.enums.ModuleType;

/**
 * Created by Mohru on 27.08.2017.
 */

public class BlindMotorModule extends Module {
    private boolean shouldStart = false;

    public BlindMotorModule() {
    }

    public BlindMotorModule(String serverId, String name, Room room) {
        super(serverId, name, room);
    }

    public BlindMotorModule(String serverId, String name, Room room, ConnectionType connectionType, String address) {
        super(serverId, name, room, connectionType, address);
    }

    public BlindMotorModule(String serverId, String name, Room room, boolean shouldStart) {
        super(serverId, name, room);

        this.shouldStart = shouldStart;
    }

    public boolean shouldStart() {
        return shouldStart;
    }

    public void setShouldStart(boolean shouldStart) {
        this.shouldStart = shouldStart;
    }

    @Override
    public ModuleType getType() {
        return ModuleType.BLIND_MOTOR_MODULE;
    }
}
