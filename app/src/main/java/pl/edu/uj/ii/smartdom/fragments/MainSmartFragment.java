package pl.edu.uj.ii.smartdom.fragments;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.activities.MainActivity;
import pl.edu.uj.ii.smartdom.server.entities.Authentication;
import pl.edu.uj.ii.smartdom.server.listeners.OnErrorListener;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class MainSmartFragment extends Fragment implements OnErrorListener {

    protected Authentication getAuth() {
        return ((MainActivity) getActivity()).getAuthentication();
    }

    protected MainActivity getMainActvity() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onConnectionError() {
        if (getView() != null)
            Snackbar.make(getView(), R.string.could_not_connect_with_server, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onServerNotSet() {
        if (getView() != null)
            Snackbar.make(getView(), R.string.server_address_error, Snackbar.LENGTH_SHORT).show();
    }

    public void setActionBarName() {
        android.support.v7.app.ActionBar actionBar = getMainActvity().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getActionBarTitle());
        }
    }

    protected abstract String getActionBarTitle();
}
