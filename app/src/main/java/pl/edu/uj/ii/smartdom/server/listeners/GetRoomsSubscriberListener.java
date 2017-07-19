package pl.edu.uj.ii.smartdom.server.listeners;

import java.util.ArrayList;

import pl.edu.uj.ii.smartdom.database.Room;

/**
 * Created by Mohru on 18.07.2017.
 */

public interface GetRoomsSubscriberListener extends OnErrorListener {

    void onRoomsLoaded(ArrayList<Room> rooms);
}
