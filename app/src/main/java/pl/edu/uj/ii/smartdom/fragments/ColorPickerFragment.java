package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skydoves.colorpickerview.ColorPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.uj.ii.smartdom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorPickerFragment extends Fragment {

    public static final String TAG = ColorPickerFragment.class.getName();

    @BindView(R.id.colorPickerView)
    ColorPickerView colorPicker;

    public ColorPickerFragment() {
        // Required empty public constructor
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

                Toast.makeText(getContext(), "red: " + rgb[0] + " green: " + rgb[1] + " blue: " + rgb[2], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
