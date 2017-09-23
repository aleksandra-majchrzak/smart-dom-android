package pl.edu.uj.ii.smartdom.database;

import android.text.TextUtils;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.ii.smartdom.enums.ModuleType;

/**
 * Created by Mohru on 22.07.2017.
 */

public abstract class Module extends SugarRecord {

    @Unique
    protected String serverId;
    protected String name;
    protected String roomServerId;
    @Ignore
    protected Room room;
    protected String address;

    public Module() {
    }

    public Module(String serverId, String name, Room room) {
        this.serverId = serverId;
        this.name = name;
        this.room = room;
    }

    public Module(String serverId, String name, Room room, String address) {
        this(serverId, name, room);
        this.address = address;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        this.roomServerId = room.getServerId();
    }

    public String getAddress() {
        return address;
    }

    public static List<Module> getAllModulesForRoom(String roomServerId) {
        List<Module> modules = new ArrayList<>();
        modules.addAll(LightModule.find(LightModule.class, "ROOM_SERVER_ID = ?", roomServerId));
        modules.addAll(MeteoModule.find(MeteoModule.class, "ROOM_SERVER_ID = ?", roomServerId));
        modules.addAll(DoorMotorModule.find(DoorMotorModule.class, "ROOM_SERVER_ID = ?", roomServerId));
        modules.addAll(BlindMotorModule.find(BlindMotorModule.class, "ROOM_SERVER_ID = ?", roomServerId));

        return modules;
    }

    public static List<Module> listAll() {
        List<Module> modules = new ArrayList<>();
        modules.addAll(LightModule.listAll(LightModule.class));
        modules.addAll(MeteoModule.listAll(MeteoModule.class));
        modules.addAll(DoorMotorModule.listAll(DoorMotorModule.class));
        modules.addAll(BlindMotorModule.listAll(BlindMotorModule.class));

        for (Module module : modules) {
            if (!TextUtils.isEmpty(module.roomServerId)) {
                module.setRoom(Room.find(Room.class, "SERVER_ID = ?", module.roomServerId).get(0));
            }
        }

        return modules;
    }

    public abstract ModuleType getType();
}
