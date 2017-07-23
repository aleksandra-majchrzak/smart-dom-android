package pl.edu.uj.ii.smartdom.server.subscribers;

import android.util.Log;

import java.util.ArrayList;

import pl.edu.uj.ii.smartdom.database.DoorMotorModule;
import pl.edu.uj.ii.smartdom.database.LightModule;
import pl.edu.uj.ii.smartdom.database.MeteoModule;
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

        // todo - zastanow sie czy to na pewno jest dobre rozwiazanie - moze by jakos oznaczac te usuniete? tak zeby nie trzeba bylo za jazdym razem usuwac wszystkich
        Log.d("rooms resp", "deleted rooms: " + Room.deleteAll(Room.class));
        Log.d("rooms resp", "deleted light modules: " + LightModule.deleteAll(LightModule.class));
        DoorMotorModule.deleteAll(DoorMotorModule.class);
        MeteoModule.deleteAll(MeteoModule.class);

        for (RoomResponse r : rooms) {
            Room newRoom = new Room(r);
            // todo - czy to mi tez zapisze do bazy wszystki moduly?
            newRoom.save();
            roomList.add(newRoom);
        }

        listener.onRoomsLoaded(roomList);
    }
}
