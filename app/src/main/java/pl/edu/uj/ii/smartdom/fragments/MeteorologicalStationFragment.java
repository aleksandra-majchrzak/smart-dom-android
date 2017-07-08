package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.uj.ii.smartdom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeteorologicalStationFragment extends Fragment {

    public static final String TAG = MeteorologicalStationFragment.class.getName();

    public MeteorologicalStationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meteorological_station, container, false);
    }

}
