package pl.edu.uj.ii.smartdom.database;

import com.orm.SugarRecord;

/**
 * Created by Mohru on 26.09.2017.
 */

public class UsersRooms extends SugarRecord {

    String userName;
    String roomServerId;
    String serverAddress;

    public UsersRooms() {
    }

    public UsersRooms(String userName, String roomServerId, String serverAddress) {
        this.userName = userName;
        this.roomServerId = roomServerId;
        this.serverAddress = serverAddress;
    }
}
