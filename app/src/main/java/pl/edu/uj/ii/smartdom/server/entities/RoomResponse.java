package pl.edu.uj.ii.smartdom.server.entities;

import java.util.List;

/**
 * Created by Mohru on 17.07.2017.
 */

public class RoomResponse {

    private String id;
    private String name;
    private List<ModuleResponse> modules;

    public RoomResponse(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ModuleResponse> getModules() {
        return modules;
    }
}


