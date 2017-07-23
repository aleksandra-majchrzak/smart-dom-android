package pl.edu.uj.ii.smartdom.database;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.ii.smartdom.server.entities.ModuleResponse;
import pl.edu.uj.ii.smartdom.server.entities.RoomResponse;

/**
 * Created by Mohru on 18.07.2017.
 */

public class Room extends SugarRecord {

    @Unique
    private String serverId;
    private String name;
    @Ignore
    private List<Module> modules = new ArrayList<>();

    public Room() {
    }

    public Room(RoomResponse response) {
        this.serverId = response.getId();
        this.name = response.getName();
        for (ModuleResponse moduleResponse : response.getModules()) {
            Module newModule = null;
            switch (moduleResponse.getType()) {
                case LIGHT_MODULE:
                    newModule = new LightModule(moduleResponse.getId(), moduleResponse.getName(), moduleResponse.getRoomId());
                    break;
                case METEO_MODULE:
                    newModule = new MeteoModule(moduleResponse.getId(), moduleResponse.getName(), moduleResponse.getRoomId());
                    break;
                case DOOR_MOTOR_MODULE:
                    newModule = new DoorMotorModule(moduleResponse.getId(), moduleResponse.getName(), moduleResponse.getRoomId());
                    break;
            }
            modules.add(newModule);
        }
    }

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public static List<Room> listAll() {
        List<Room> allRooms = Room.listAll(Room.class);
        for (Room room : allRooms) {
            room.modules.addAll(LightModule.find(LightModule.class, "ROOM_SERVER_ID = ?", room.serverId));
            room.modules.addAll(MeteoModule.find(MeteoModule.class, "ROOM_SERVER_ID = ?", room.serverId));
            room.modules.addAll(DoorMotorModule.find(DoorMotorModule.class, "ROOM_SERVER_ID = ?", room.serverId));
        }

        return allRooms;
    }
}
