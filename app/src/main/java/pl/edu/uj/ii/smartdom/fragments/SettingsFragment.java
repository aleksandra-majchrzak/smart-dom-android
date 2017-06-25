package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.uj.ii.smartdom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String TAG = SettingsFragment.class.getName();
    private ListPreference mListPreference;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    /*@Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundResource(R.color.background);

        ((EditTextPreference) getPreferenceManager().findPreference("edittext_preference")).setDialogLayoutResource(R.layout.preference_dialog_edittext);

        return view;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mListPreference = (ListPreference) getPreferenceManager().findPreference("preference_key");
        mListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                // your code here
                return true;
            }
        });

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }*/

}
