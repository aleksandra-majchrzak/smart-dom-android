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
import pl.edu.uj.ii.smartdom.listeners.OnModuleItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends MainSmartFragment {

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
        setActionBarName();
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

        roomModulesListView.setOnItemClickListener(new OnModuleItemClickListener(getMainActvity(), adapter));

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

    @Override
    protected String getActionBarTitle() {
        return room.getName();
    }
}
