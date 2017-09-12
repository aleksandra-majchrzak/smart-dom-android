package pl.edu.uj.ii.smartdom.server.subscribers;

import java.util.ArrayList;

import pl.edu.uj.ii.smartdom.database.Room;
import pl.edu.uj.ii.smartdom.server.entities.RoomResponse;
import pl.edu.uj.ii.smartdom.server.listeners.GetRoomsSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 18.07.2017.
 */

public class GetRoomsSubscriber extends Subscriber<ArrayList<RoomResponse>> {

    private GetRoomsSubscriberListener listener;

    public GetRoomsSubscriber(GetRoomsSubscriberListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        listener.onConnectionError();
    }

    @Override
    public void onNext(ArrayList<RoomResponse> rooms) {
        ArrayList<Room> roomList = new ArrayList<>();
        Room.deleteAll(Room.class);

        for (RoomResponse r : rooms) {
            Room newRoom = new Room(r);
            newRoom.save();
            roomList.add(newRoom);
        }

        listener.onRoomsLoaded(roomList);
    }
}
