package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    ListView roomsListView;

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
        setActionBarName();
        return fragmentView;
    }

    private void initializeComponents(View fragmentView) {
        final List<Room> rooms = Room.listAll();

        roomsAdapter = new ArrayAdapter<Room>(getContext(), R.layout.room_row, R.id.room_name_textView, rooms) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return super.getView(position, convertView, parent);
            }
        };
        roomsListView.setAdapter(roomsAdapter);
        roomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putLong("room_id", rooms.get(position).getId());
                RoomFragment fragment = new RoomFragment();
                fragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, RoomFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });
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
            roomsListView.setVisibility(View.GONE);
            roomsPlaceholderTextView.setVisibility(View.VISIBLE);
        } else {
            roomsListView.setVisibility(View.VISIBLE);
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
        super.onConnectionError();
        roomsRefreshLayout.setRefreshing(false);
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.rooms);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (subscription != null)
            subscription.unsubscribe();
    }
}
