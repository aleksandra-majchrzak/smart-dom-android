package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.skydoves.colorpickerview.ColorPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.OnErrorListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorPickerFragment extends Fragment implements OnErrorListener {

    public static final String TAG = ColorPickerFragment.class.getName();

    private static final int TIME_INTERVAL = 500;
    private static long lastTime;

    @BindView(R.id.colorPickerView)
    ColorPickerView colorPicker;
    @BindView(R.id.turn_on_button)
    Button turnOnButton;
    @BindView(R.id.turn_off_button)
    Button turnOffButton;

    public ColorPickerFragment() {
        lastTime = System.nanoTime();
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
            @Override
            public void onColorSelected(int color) {
                int[] rgb = colorPicker.getColorRGB();

                if (System.nanoTime() - lastTime > TIME_INTERVAL) {
                    Log.d("setStripeColor", "red: " + rgb[0] + " green: " + rgb[1] + " blue: " + rgb[2]);
                    SmartDomService.getInstance().setStripColor(rgb[0], rgb[1], rgb[2], ColorPickerFragment.this);
                    lastTime = System.nanoTime();
                }
            }
        });
    }

    @OnClick(R.id.turn_on_button)
    public void onTurnOnButtonClick() {
        SmartDomService.getInstance().turnOnLight(this);
    }

    @OnClick(R.id.turn_off_button)
    public void onTurnOffButtonClick() {
        SmartDomService.getInstance().turnOffLight(this);
    }

    @Override
    public void onConnectionError() {
        Snackbar.make(getView(), "Could not connect with module", Snackbar.LENGTH_SHORT).show();
    }
}
