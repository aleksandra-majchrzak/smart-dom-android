package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.database.BlindMotorModule;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.CloseBlindSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.OpenBlindSubscriberListener;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlindMotorFragment extends MainSmartFragment implements OpenBlindSubscriberListener, CloseBlindSubscriberListener {

    public static final String TAG = BlindMotorFragment.class.getName();

    @BindView(R.id.open_blind_button)
    Button openBlindButton;
    @BindView(R.id.close_blind_button)
    Button closeBlindButton;

    Subscription openSubscription;
    Subscription closeSubscription;

    BlindMotorModule blindModule;

    public BlindMotorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_blind_motor, container, false);
        ButterKnife.bind(this, fragmentView);

        if (getArguments() != null && getArguments().containsKey("moduleId")) {
            blindModule = BlindMotorModule.findById(BlindMotorModule.class, getArguments().getLong("moduleId"));
        }

        return fragmentView;
    }

    @OnClick(R.id.open_blind_button)
    public void onOpenBlindButtonClick() {
        if (openBlindButton.getText().equals(getString(R.string.open_blind))) {
            blindModule.setShouldStart(true);
            SmartDomService.getInstance().openBlind(blindModule, this, getAuth());
        } else {
            blindModule.setShouldStart(false);
            SmartDomService.getInstance().openBlind(blindModule, this, getAuth());
        }
    }

    @OnClick(R.id.close_blind_button)
    public void onCloseBlindButtonClick() {
        if (closeBlindButton.getText().equals(getString(R.string.close_blind))) {
            blindModule.setShouldStart(true);
            SmartDomService.getInstance().closeBlind(blindModule, this, getAuth());
        } else {
            blindModule.setShouldStart(false);
            SmartDomService.getInstance().closeBlind(blindModule, this, getAuth());
        }
    }

    @Override
    public void onCloseBlindResult() {
        if (closeBlindButton.getText().equals(getString(R.string.close_blind))) {
            openBlindButton.setText(R.string.open_blind);
            closeBlindButton.setText(R.string.stop_closing);
        } else {
            closeBlindButton.setText(R.string.close_blind);
        }
    }

    @Override
    public void onOpenBlindResult() {
        if (openBlindButton.getText().equals(getString(R.string.open_blind))) {
            openBlindButton.setText(R.string.stop_open);
            closeBlindButton.setText(R.string.close_blind);
        } else {
            openBlindButton.setText(R.string.open_blind);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (openSubscription != null)
            openSubscription.unsubscribe();
        if (closeSubscription != null)
            closeSubscription.unsubscribe();
    }
}
