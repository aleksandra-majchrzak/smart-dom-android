package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.database.Room;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.GetRoomsSubscriberListener;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomsFragment extends MainSmartFragment implements GetRoomsSubscriberListener {

    public static final String TAG = RoomsFragment.class.getName();

    @BindView(R.id.rooms_listView)
    ListView roomsaListView;

    @BindView(R.id.rooms_placeholder_textView)
    TextView roomsPlaceholderTextView;

    @BindView(R.id.rooms_refreshLayout)
    SwipeRefreshLayout roomsRefreshLayout;

    ArrayAdapter<Room> roomsAdapter;

    private Subscription subscription;


    public RoomsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_rooms, container, false);

        ButterKnife.bind(this, fragmentView);

        initializeComponents(fragmentView);

        return fragmentView;
    }

    private void initializeComponents(View fragmentView) {
        List<Room> rooms = Room.listAll();

        roomsAdapter = new ArrayAdapter<Room>(getContext(), R.layout.room_row, R.id.room_name_textView, rooms) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        roomsaListView.setAdapter(roomsAdapter);
        roomsAdapter.notifyDataSetChanged();

        updateViews();

        roomsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                subscription = SmartDomService.getInstance().getRooms(RoomsFragment.this, getAuth());
            }
        });
    }

    private void updateViews() {
        if (roomsAdapter.getCount() == 0) {
            roomsaListView.setVisibility(View.GONE);
            roomsPlaceholderTextView.setVisibility(View.VISIBLE);
        } else {
            roomsaListView.setVisibility(View.VISIBLE);
            roomsPlaceholderTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRoomsLoaded(ArrayList<Room> rooms) {
        Snackbar.make(getView(), "Rooms loaded", Snackbar.LENGTH_SHORT).show();
        roomsAdapter.clear();
        roomsAdapter.addAll(rooms);
        roomsAdapter.notifyDataSetChanged();

        updateViews();
        roomsRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onConnectionError() {
        Snackbar.make(getView(), "Could not connect do server", Snackbar.LENGTH_SHORT).show();
        roomsRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (subscription != null)
            subscription.unsubscribe();
    }
}
