package pl.edu.uj.ii.smartdom.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.database.BlindMotorModule;
import pl.edu.uj.ii.smartdom.database.LightModule;
import pl.edu.uj.ii.smartdom.database.MeteoModule;
import pl.edu.uj.ii.smartdom.database.Module;
import pl.edu.uj.ii.smartdom.database.Room;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.LoginSubscriberListener;

public class MainFragment extends MainSmartFragment implements LoginSubscriberListener {

    public final static String TAG = MainFragment.class.getName();

    @BindView(R.id.login_editText)
    EditText loginEditText;

    @BindView(R.id.password_editText)
    EditText passwordEditText;

    @BindView(R.id.login_ll)
    LinearLayout loginLinearLayout;

    @BindView(R.id.hello_tl)
    TableLayout helloTableLayout;

    @BindView(R.id.rooms_number_textView)
    TextView roomsNumberTextView;

    @BindView(R.id.modules_number_textView)
    TextView modulesNumberTextView;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, fragmentView);
        initComponents();
        setActionBarName();
        return fragmentView;
    }

    private void initComponents() {
        if (getAuth() != null && !TextUtils.isEmpty(getAuth().getToken())) {
            loginLinearLayout.setVisibility(View.GONE);
            helloTableLayout.setVisibility(View.VISIBLE);
            modulesNumberTextView.setText((Module.count(LightModule.class) + Module.count(BlindMotorModule.class) + Module.count(MeteoModule.class)) + "");
            roomsNumberTextView.setText(Room.count(Room.class) + "");
        } else {
            loginLinearLayout.setVisibility(View.VISIBLE);
            helloTableLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick() {
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        SmartDomService.getInstance().login(login, password, this);
    }

    @Override
    public void onLoginError() {
        Snackbar.make(getView(), R.string.login_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess(String login, String token) {
        Snackbar.make(getView(), "Login success", Snackbar.LENGTH_SHORT).show();
        getMainActvity().saveAuthentication(login, token);

        loginLinearLayout.setVisibility(View.GONE);
        helloTableLayout.setVisibility(View.VISIBLE);
        modulesNumberTextView.setText(Module.getModulesForUser(getAuth().getUsername(), getAuth().getServerAddress()).size() + "");
        roomsNumberTextView.setText(Room.getRoomsForUser(getAuth().getUsername(), getAuth().getServerAddress()).size() + "");
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.app_name);
    }
}
