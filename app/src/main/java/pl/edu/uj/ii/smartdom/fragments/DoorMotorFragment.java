package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.IsDoorOpenSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.OpenDoorSubscriberListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoorMotorFragment extends Fragment implements OpenDoorSubscriberListener, IsDoorOpenSubscriberListener {

    public static final String TAG = MeteorologicalStationFragment.class.getName();

    @BindView(R.id.door_imageView)
    ImageView doorImageView;

    @BindView(R.id.door_open_textView)
    TextView doorOpenTextView;

    @BindView(R.id.progressBar)
    ProgressBar progress;

    @BindView(R.id.open_door_button)
    Button openDoorButton;

    private boolean isDoorOpen = true;

    public DoorMotorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_door_motor, container, false);

        ButterKnife.bind(this, fragmentView);

        progress.setVisibility(View.GONE);

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

        SmartDomService.getInstance().isDoorOpen(this);
    }

    @OnClick(R.id.open_door_button)
    public void onOpenDoorButtonClick() {

        progress.setVisibility(View.GONE);
        SmartDomService.getInstance().openDoor(!isDoorOpen, this);
    }

    @Override
    public void onOpenDoorResult(boolean _isDoorOpen) {
        if (_isDoorOpen) {
            doorImageView.setImageResource(R.drawable.ic_lock_open);
            openDoorButton.setText(R.string.close_door);
            Snackbar.make(getView(), "Door opened", Snackbar.LENGTH_SHORT).show();
        } else {
            doorImageView.setImageResource(R.drawable.ic_lock);
            openDoorButton.setText(R.string.open_door);
            Snackbar.make(getView(), "Door closed", Snackbar.LENGTH_SHORT).show();
        }

        this.isDoorOpen = _isDoorOpen;
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onConnectionError() {
        progress.setVisibility(View.GONE);
        Snackbar.make(getView(), "Could not connect with module", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onIsDoorOpenReceived(boolean isDoorOpen) {
        if (isDoorOpen) {
            doorImageView.setImageResource(R.drawable.ic_lock_open);
            openDoorButton.setText(R.string.close_door);
        } else {
            doorImageView.setImageResource(R.drawable.ic_lock);
            openDoorButton.setText(R.string.open_door);
        }

        progress.setVisibility(View.GONE);
        this.isDoorOpen = isDoorOpen;
    }
}
