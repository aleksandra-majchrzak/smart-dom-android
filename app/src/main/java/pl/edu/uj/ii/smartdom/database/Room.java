package pl.edu.uj.ii.smartdom.database;

import com.orm.SugarRecord;

/**
 * Created by Mohru on 18.07.2017.
 */

public class Room extends SugarRecord {

    private String name;

    public Room() {
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
}
