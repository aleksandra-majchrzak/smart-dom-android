package pl.edu.uj.ii.smartdom.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.skydoves.colorpickerview.ColorPickerView;

/**
 * Created by Mohru on 20.08.2017.
 */

public class MyColorPickerView extends ColorPickerView {

    private ImageView selector;

    public MyColorPickerView(Context context) {
        super(context);
    }

    public MyColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyColorPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void resetPicker() {
        dispatchTouchEvent(MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, getWidth() / 2, getHeight() / 2, 0, 0, 0, 0, 0, 0, 0));
    }
}
