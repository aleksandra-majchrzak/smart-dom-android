package pl.edu.uj.ii.smartdom.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.GetCO2SubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetCOSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetGasSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetHumiditySubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetTempSubscriberListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeteorologicalStationFragment extends Fragment implements GetCO2SubscriberListener,
        GetCOSubscriberListener, GetGasSubscriberListener, GetHumiditySubscriberListener, GetTempSubscriberListener {

    public static final String TAG = MeteorologicalStationFragment.class.getName();

    @BindView(R.id.temperature_textView)
    TextView temperatureTextView;

    @BindView(R.id.humidity_textView)
    TextView humidityTextView;

    @BindView(R.id.co2_textView)
    TextView co2TextView;

    @BindView(R.id.co_textView)
    TextView coTextView;

    @BindView(R.id.gas_textView)
    TextView gasTextView;


    public MeteorologicalStationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_meteorological_station, container, false);

        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }


    @Override
    public void onConnectionError() {
        Snackbar.make(getView(), "Could not connect with module", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCO2Received(double co2) {
        co2TextView.setText(String.valueOf(co2));
    }

    @Override
    public void onCOReceived(double co) {
        coTextView.setText(String.valueOf(co));
    }

    @Override
    public void onGasReceived(double gas) {
        gasTextView.setText(String.valueOf(gas));
    }

    @Override
    public void onHumidityReceived(double humidity) {
        humidityTextView.setText(String.valueOf(humidity));
    }

    @Override
    public void onTemperatureReceived(double temperature) {
        temperatureTextView.setText(String.valueOf(temperature));
    }

    @OnClick(R.id.refresh_meteo_button)
    public void onRefreshMeteoButtonClick() {
        SmartDomService.getInstance().getCO(this);
        SmartDomService.getInstance().getCO2(this);
        SmartDomService.getInstance().getGas(this);
        SmartDomService.getInstance().getHumidity(this);
        SmartDomService.getInstance().getTemperature(this);
    }
}