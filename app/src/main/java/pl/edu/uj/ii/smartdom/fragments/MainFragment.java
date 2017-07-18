package pl.edu.uj.ii.smartdom.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.LoginSubscriberListener;

public class MainFragment extends MainSmartFragment implements LoginSubscriberListener {

    public final static String TAG = MainFragment.class.getName();

    @BindView(R.id.login_editText)
    EditText loginEditText;

    @BindView(R.id.password_editText)
    EditText passwordEditText;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick() {
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        SmartDomService.getInstance().login(login, password, this);
    }

    @Override
    public void onLoginError() {
        Snackbar.make(getView(), "Login error", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess(String login, String token) {
        // todo obsługa ekranu po logowaniu
        Snackbar.make(getView(), "Login success", Snackbar.LENGTH_SHORT).show();
        getMainActvity().saveAuthentication(login, token);
    }

    @Override
    public void onConnectionError() {
        Snackbar.make(getView(), "Could not connect to server", Snackbar.LENGTH_SHORT).show();
    }
}
