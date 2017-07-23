package pl.edu.uj.ii.smartdom.database;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Mohru on 22.07.2017.
 */

public abstract class Module extends SugarRecord {

    @Unique
    private String serverId;
    private String name;
    private String roomServerId;

    public Module() {
    }

    public Module(String serverId, String name, String roomServerId) {
        this.serverId = serverId;
        this.name = name;
        this.roomServerId = roomServerId;
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

    public String getRoomServerId() {
        return roomServerId;
    }

    public void setRoomServerId(String roomServerId) {
        this.roomServerId = roomServerId;
    }
}
