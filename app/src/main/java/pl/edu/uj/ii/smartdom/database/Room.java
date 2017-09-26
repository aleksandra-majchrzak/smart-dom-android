package pl.edu.uj.ii.smartdom.database;

import com.orm.SugarRecord;
import com.orm.SugarTransactionHelper;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

import java.util.ArrayList;
import java.util.Iterator;
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
                    newModule = new LightModule(moduleResponse.getId(), moduleResponse.getName(),
                            this, moduleResponse.getConnectionType(), moduleResponse.getAddress());
                    break;
                case METEO_MODULE:
                    newModule = new MeteoModule(moduleResponse.getId(), moduleResponse.getName(),
                            this, moduleResponse.getConnectionType(), moduleResponse.getAddress());
                    break;
                case BLIND_MOTOR_MODULE:
                    newModule = new BlindMotorModule(moduleResponse.getId(), moduleResponse.getName(),
                            this, moduleResponse.getConnectionType(), moduleResponse.getAddress());
                    break;
            }
            newModule.setRoom(this);
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

    public String getServerId() {
        return serverId;
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

    public static List<Room> getRoomsForUser(String username, String serverAddress) {
        List<Room> allRooms = Room.findWithQuery(Room.class,
                "SELECT r.* FROM USERS_ROOMS ur " +
                        "JOIN ROOM r ON r.SERVER_ID = ur.ROOM_SERVER_ID " +
                        "WHERE ur.USER_NAME = ? AND ur.SERVER_ADDRESS = ? ",
                username, serverAddress);

        for (Room room : allRooms) {
            room.modules = Module.getAllModulesForRoom(room.getServerId());
        }

        return allRooms;
    }

    @Override
    public long save() {
        final long[] saveResult = new long[1];
        SugarTransactionHelper.doInTransaction(new SugarTransactionHelper.Callback() {
            @Override
            public void manipulateInTransaction() {
                List<Module> oldModules = Module.getAllModulesForRoom(serverId);

                for (Module module : modules) {
                    module.setRoom(Room.this);
                    module.save();
                    Iterator<Module> oldModuleIterator = oldModules.iterator();
                    while (oldModuleIterator.hasNext()) {
                        Module oldModule = oldModuleIterator.next();
                        if (oldModule.getServerId().equals(module.getServerId()))
                            oldModuleIterator.remove();
                    }
                }

                Module.deleteInTx(oldModules);
                saveResult[0] = Room.super.save();
            }
        });

        return saveResult[0];
    }

    public static Room findById(Long id) {
        Room room = Room.findById(Room.class, id);
        room.modules.addAll(Module.getAllModulesForRoom(room.serverId));
        return room;
    }
}
