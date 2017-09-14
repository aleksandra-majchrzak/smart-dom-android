package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomesFragment extends MainSmartFragment {

    public final static String TAG = HomesFragment.class.getName();

    public HomesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_homes, container, false);
        ButterKnife.bind(this, fragmentView);
        setActionBarName();
        return fragmentView;
    }


    @OnClick(R.id.change_home_button)
    public void onChangeHomeClick() {
        Toast.makeText(getContext(), "ple", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.add_new_home_button)
    public void addNewHomeClick() {
        Toast.makeText(getContext(), "pleple", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String getActionBarTitle() {
        return "Homes";
    }
}
