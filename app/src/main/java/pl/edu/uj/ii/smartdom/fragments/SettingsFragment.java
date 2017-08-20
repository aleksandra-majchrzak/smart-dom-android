package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.activities.MainActivity;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.entities.Authentication;
import pl.edu.uj.ii.smartdom.utils.StringPatterns;

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

        final EditTextPreference serverPref = ((EditTextPreference) getPreferenceManager().findPreference("web_server_preference"));
        serverPref.setSummary(serverPref.getText());
        serverPref.setDialogLayoutResource(R.layout.preference_dialog_edittext);
        serverPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String newAddress = (String) newValue;
                if (!StringPatterns.IP_ADDRESS_WITH_PORT.matcher(newAddress).matches()) {
                    Toast.makeText(getContext(), R.string.wrong_ip_format, Toast.LENGTH_LONG).show();
                    return false;
                }

                serverPref.setSummary(newAddress);
                SmartDomService.resetService();
                SmartDomService.setServerAddress(newAddress);
                return true;
            }
        });

        Authentication auth = ((MainActivity) getActivity()).getAuthentication();
        if (TextUtils.isEmpty(auth.getToken())) {
            getPreferenceManager().getPreferenceScreen().removePreference(getPreferenceManager().findPreference("second_section_preference"));
            serverPref.setEnabled(true);
        } else {
            getPreferenceManager().getPreferenceScreen().addPreference(getPreferenceManager().findPreference("second_section_preference"));
            serverPref.setEnabled(false);
        }

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
