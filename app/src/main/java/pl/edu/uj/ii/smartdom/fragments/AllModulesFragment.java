package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.adapters.ModulesListAdapter;
import pl.edu.uj.ii.smartdom.database.Module;
import pl.edu.uj.ii.smartdom.listeners.OnModuleItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllModulesFragment extends MainSmartFragment {

    public static final String TAG = AllModulesFragment.class.getName();

    @BindView(R.id.all_modules_listView)
    ListView modulesListView;

    @BindView(R.id.room_placeholder_textView)
    TextView modulesPlaceholderTextView;

    private ModulesListAdapter adapter;

    public AllModulesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View framgentView = inflater.inflate(R.layout.fragment_all_modules, container, false);
        ButterKnife.bind(this, framgentView);
        initComponents();
        ;
        setActionBarName();
        return framgentView;
    }

    private void initComponents() {
        adapter = new ModulesListAdapter(getContext(), Module.listAll(), true);
        modulesListView.setAdapter(adapter);
        modulesListView.setOnItemClickListener(new OnModuleItemClickListener(getMainActvity(), adapter));
        adapter.notifyDataSetChanged();

        updateViews();
    }

    private void updateViews() {
        if (adapter.getCount() == 0) {
            modulesListView.setVisibility(View.GONE);
            modulesPlaceholderTextView.setVisibility(View.VISIBLE);
        } else {
            modulesListView.setVisibility(View.VISIBLE);
            modulesPlaceholderTextView.setVisibility(View.GONE);
        }
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.all_modules);
    }
}
