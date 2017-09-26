package pl.edu.uj.ii.smartdom;

import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.orm.SugarApp;

import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.utils.SSLUtils;

/**
 * Created by Mohru on 19.07.2017.
 */

public class SmartDomApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        SSLUtils.initSSLConfig(this);
        setServiceAddress();
    }

    private void setServiceAddress() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SmartDomService.setServerAddress(prefs.getString(Constants.WEB_SERVER_PREFERENCE, ""));
    }
}
