package pl.edu.uj.ii.smartdom.server.subscribers;

import com.orm.SugarTransactionHelper;

import java.util.ArrayList;

import pl.edu.uj.ii.smartdom.database.Room;
import pl.edu.uj.ii.smartdom.database.UsersRooms;
import pl.edu.uj.ii.smartdom.server.entities.RoomResponse;
import pl.edu.uj.ii.smartdom.server.listeners.GetRoomsSubscriberListener;
import rx.Subscriber;

/**
 * Created by Mohru on 18.07.2017.
 */

public class GetRoomsSubscriber extends Subscriber<ArrayList<RoomResponse>> {

    private GetRoomsSubscriberListener listener;
    private String username;
    private String serverAddress;

    public GetRoomsSubscriber(GetRoomsSubscriberListener listener, String username, String serverAddress) {
        this.listener = listener;
        this.username = username;
        this.serverAddress = serverAddress;
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
        final ArrayList<Room> roomList = new ArrayList<>();
        UsersRooms.deleteAll(UsersRooms.class, "USER_NAME = ?", username);

        for (RoomResponse r : rooms) {
            final Room newRoom = new Room(r);
            SugarTransactionHelper.doInTransaction(new SugarTransactionHelper.Callback() {
                @Override
                public void manipulateInTransaction() {
                    newRoom.save();
                    UsersRooms usersRooms = new UsersRooms(username, newRoom.getServerId(), serverAddress);
                    usersRooms.save();
                    roomList.add(newRoom);
                }
            });
        }

        listener.onRoomsLoaded(roomList);
    }
}
