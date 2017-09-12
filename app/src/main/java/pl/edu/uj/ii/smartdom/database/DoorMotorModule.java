package pl.edu.uj.ii.smartdom.database;

import pl.edu.uj.ii.smartdom.enums.ModuleType;

/**
 * Created by Mohru on 22.07.2017.
 */

public class DoorMotorModule extends Module {
    private boolean isOpen = false;

    public DoorMotorModule(){
    }

    public DoorMotorModule(String serverId, String name, Room room) {
        super(serverId, name, room);
    }

    public DoorMotorModule(String serverId, String name, Room room, boolean isOpen) {
        super(serverId, name, room);

        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public ModuleType getType() {
        return ModuleType.DOOR_MOTOR_MODULE;
    }
}
