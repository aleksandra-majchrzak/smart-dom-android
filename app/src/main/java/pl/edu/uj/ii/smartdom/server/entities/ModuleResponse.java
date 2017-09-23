package pl.edu.uj.ii.smartdom.server.entities;

import pl.edu.uj.ii.smartdom.enums.ConnectionType;
import pl.edu.uj.ii.smartdom.enums.ModuleType;

/**
 * Created by Mohru on 22.07.2017.
 */

public class ModuleResponse {
    private String id;
    private String name;
    private String roomId;
    private ModuleType type;
    private ConnectionType connectionType;
    private String address;

    private boolean isOn;
    private boolean isOpen;

    public ModuleType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRoomId() {
        return roomId;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public String getAddress() {
        return address;
    }
}