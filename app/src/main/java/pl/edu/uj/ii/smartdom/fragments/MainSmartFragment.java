package pl.edu.uj.ii.smartdom.fragments;


import android.support.v4.app.Fragment;

import pl.edu.uj.ii.smartdom.activities.MainActivity;
import pl.edu.uj.ii.smartdom.server.entities.Authentication;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MainSmartFragment extends Fragment {

    protected Authentication getAuth() {
        return ((MainActivity) getActivity()).getAuthentication();
    }

    protected MainActivity getMainActvity() {
        return (MainActivity) getActivity();
    }
}
