package pl.edu.uj.ii.smartdom.fragments;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import pl.edu.uj.ii.smartdom.Constants;
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

        final EditTextPreference serverPref = ((EditTextPreference) getPreferenceManager().findPreference(Constants.WEB_SERVER_PREFERENCE));
        serverPref.setSummary(serverPref.getText());
        serverPref.setDialogLayoutResource(R.layout.preference_dialog_edittext);
        serverPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @SuppressLint("CommitPrefEdits")
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
                getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, 0)
                        .edit()
                        .putString(Constants.WEB_SERVER_PREFERENCE, newAddress)
                        .commit();
                return true;
            }
        });

        Authentication auth = ((MainActivity) getActivity()).getAuthentication();
        if (TextUtils.isEmpty(auth.getToken())) {
            serverPref.setEnabled(true);
        } else {
            serverPref.setEnabled(false);
        }

        getPreferenceManager().findPreference("butterknife_preference")
                .setOnPreferenceClickListener(new LicenceOnPreferenceClickListener("Butter Knife",
                        "https://raw.githubusercontent.com/JakeWharton/butterknife/master/LICENSE.txt"));

        getPreferenceManager().findPreference("support_preference")
                .setOnPreferenceClickListener(new LicenceOnPreferenceClickListener("Android Support Library",
                        "https://raw.githubusercontent.com/android/platform_frameworks_support/master/LICENSE.txt"));

        getPreferenceManager().findPreference("colorpickerview_preference")
                .setOnPreferenceClickListener(new LicenceOnPreferenceClickListener("ColorPickerView",
                        "https://raw.githubusercontent.com/skydoves/ColorPickerView/master/LICENSE"));

        getPreferenceManager().findPreference("retrofit_preference")
                .setOnPreferenceClickListener(new LicenceOnPreferenceClickListener("Retrofit",
                        "https://raw.githubusercontent.com/square/retrofit/master/LICENSE.txt"));

        getPreferenceManager().findPreference("okhttp_preference")
                .setOnPreferenceClickListener(new LicenceOnPreferenceClickListener("OkHttp",
                        "https://raw.githubusercontent.com/square/okhttp/master/LICENSE.txt"));

        getPreferenceManager().findPreference("rxandroid_preference")
                .setOnPreferenceClickListener(new LicenceOnPreferenceClickListener("RxAndroid",
                        "https://raw.githubusercontent.com/ReactiveX/RxAndroid/2.x/LICENSE"));

        getPreferenceManager().findPreference("rxjava_preference")
                .setOnPreferenceClickListener(new LicenceOnPreferenceClickListener("RxJava",
                        "https://raw.githubusercontent.com/ReactiveX/RxJava/2.x/LICENSE"));

        getPreferenceManager().findPreference("sugar_preference")
                .setOnPreferenceClickListener(new LicenceOnPreferenceClickListener("Sugar",
                        "https://raw.githubusercontent.com/chennaione/sugar/master/LICENSE"));

        return view;
    }

    class LicenceOnPreferenceClickListener implements Preference.OnPreferenceClickListener {

        String libraryName;
        String URL;

        LicenceOnPreferenceClickListener(String libraryName, String URL) {
            this.libraryName = libraryName;
            this.URL = URL;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.licence_view, null);
            WebView licenceWebView = (WebView) view.findViewById(R.id.licence_webview);
            licenceWebView.loadUrl(URL);
            new AlertDialog.Builder(getContext())
                    .setTitle(libraryName)
                    .setView(view)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ;
                        }
                    }).show();
            return true;
        }
    }
}
