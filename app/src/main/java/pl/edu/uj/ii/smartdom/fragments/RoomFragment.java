package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.adapters.ModulesListAdapter;
import pl.edu.uj.ii.smartdom.database.Module;
import pl.edu.uj.ii.smartdom.database.Room;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {

    public static final String TAG = RoomFragment.class.getName();

    @BindView(R.id.room_modules_listView)
    ListView roomModulesListView;
    ModulesListAdapter adapter;

    @BindView(R.id.room_placeholder_textView)
    TextView modulesPlaceholderTextView;

    private Room room;

    public RoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_room, container, false);

        ButterKnife.bind(this, fragmentView);

        initComponents();

        return fragmentView;
    }

    private void initComponents() {
        if (getArguments() != null && getArguments().containsKey("room_id")) {
            room = Room.findById((Long) getArguments().get("room_id"));
        } else
            return;

        adapter = new ModulesListAdapter(getContext(), room.getModules(), false);
        roomModulesListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        roomModulesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Module currentModule = adapter.getItem(position);
                switch (currentModule.getType()) {
                    case LIGHT_MODULE:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new ColorPickerFragment(), ColorPickerFragment.TAG)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case DOOR_MOTOR_MODULE:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new DoorMotorFragment(), DoorMotorFragment.TAG)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case METEO_MODULE:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new MeteorologicalStationFragment(), MeteorologicalStationFragment.TAG)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case BLIND_MOTOR_MODULE:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new BlindMotorFragment(), BlindMotorFragment.TAG)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });

        updateViews();
    }

    private void updateViews() {
        if (adapter.getCount() == 0) {
            roomModulesListView.setVisibility(View.GONE);
            modulesPlaceholderTextView.setVisibility(View.VISIBLE);
        } else {
            roomModulesListView.setVisibility(View.VISIBLE);
            modulesPlaceholderTextView.setVisibility(View.GONE);
        }
    }

}
