package pl.edu.uj.ii.smartdom;

import com.orm.SugarApp;

import pl.edu.uj.ii.smartdom.utils.SSLUtils;

/**
 * Created by Mohru on 19.07.2017.
 */

public class SmartDomApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        SSLUtils.initSSLConfig(this);
    }
}
