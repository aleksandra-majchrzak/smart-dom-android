package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.skydoves.colorpickerview.ColorPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.database.LightModule;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.TurnOffLightSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.TurnOnSubscriberListener;
import pl.edu.uj.ii.smartdom.views.MyColorPickerView;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorPickerFragment extends MainSmartFragment implements TurnOnSubscriberListener, TurnOffLightSubscriberListener {

    public static final String TAG = ColorPickerFragment.class.getName();

    private static final int TIME_INTERVAL = 200;
    private static long lastTimeColor;
    private static long lastTimeBrightness;

    @BindView(R.id.colorPickerView)
    MyColorPickerView colorPicker;
    @BindView(R.id.turn_on_button)
    Button turnOnButton;
    @BindView(R.id.turn_off_button)
    Button turnOffButton;
    @BindView(R.id.brightness_seekBar)
    SeekBar brightnessSeekBar;

    Subscription turnOnSubscrition;
    Subscription turnOffSubscrition;
    Subscription setStripeSubscrition;
    Subscription setBrightnessSubscrition;

    private LightModule lightModule = new LightModule("1234", "", "");

    public ColorPickerFragment() {
        lastTimeColor = System.currentTimeMillis();
        lastTimeBrightness = System.currentTimeMillis();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_color_picker, container, false);

        ButterKnife.bind(this, fragmentView);

        initComponents();

        return fragmentView;
    }


    private void initComponents() {
        colorPicker.setColorListener(new ColorPickerView.ColorListener() {
            private boolean isUpdating = false;

            @Override
            public void onColorSelected(int color) {
                if (!isUpdating) {
                    isUpdating = true;
                    if (turnOffButton.isEnabled()) {
                        int[] rgb = colorPicker.getColorRGB();

                        if (System.currentTimeMillis() - lastTimeColor > TIME_INTERVAL) {
                            Log.d("setStripeColor", "red: " + rgb[0] + " green: " + rgb[1] + " blue: " + rgb[2]);
                            setStripeSubscrition = SmartDomService.getInstance().setStripColor(rgb[0], rgb[1], rgb[2], ColorPickerFragment.this, getAuth(), lightModule);
                            lastTimeColor = System.currentTimeMillis();
                        }
                    } else {
                        colorPicker.resetPicker();
                    }
                    isUpdating = false;
                }
            }
        });
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && System.currentTimeMillis() - lastTimeBrightness > TIME_INTERVAL) {
                    setBrightnessSubscrition = SmartDomService.getInstance().setStripBrightness(progress, ColorPickerFragment.this, getAuth(), lightModule);
                    lastTimeBrightness = System.currentTimeMillis();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        brightnessSeekBar.setEnabled(false);
        colorPicker.setClickable(false);
    }

    @OnClick(R.id.turn_on_button)
    public void onTurnOnButtonClick() {
        turnOnSubscrition = SmartDomService.getInstance().turnOnLight(this, getAuth(), lightModule);
    }

    @OnClick(R.id.turn_off_button)
    public void onTurnOffButtonClick() {
        turnOffSubscrition = SmartDomService.getInstance().turnOffLight(this, getAuth(), lightModule);
    }

    @Override
    public void onConnectionError() {
        Snackbar.make(getView(), R.string.module_connection_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (turnOnSubscrition != null)
            turnOnSubscrition.unsubscribe();

        if (turnOffSubscrition != null)
            turnOffSubscrition.unsubscribe();

        if (setStripeSubscrition != null)
            setStripeSubscrition.unsubscribe();

        if (setBrightnessSubscrition != null)
            setBrightnessSubscrition.unsubscribe();
    }

    @Override
    public void onLightOff() {
        turnOffButton.setEnabled(false);
        turnOnButton.setEnabled(true);
        brightnessSeekBar.setProgress(100);
        brightnessSeekBar.setEnabled(false);
        colorPicker.resetPicker();
        colorPicker.setClickable(false);
    }

    @Override
    public void onLightOn() {
        turnOffButton.setEnabled(true);
        turnOnButton.setEnabled(false);
        brightnessSeekBar.setEnabled(true);
        colorPicker.setClickable(true);
    }
}
