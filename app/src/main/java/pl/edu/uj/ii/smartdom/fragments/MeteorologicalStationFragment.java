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
import pl.edu.uj.ii.smartdom.database.MeteoModule;
import pl.edu.uj.ii.smartdom.enums.ConnectionType;
import pl.edu.uj.ii.smartdom.server.SmartDomService;
import pl.edu.uj.ii.smartdom.server.listeners.GetCO2SubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetCOSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetGasSubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetHumiditySubscriberListener;
import pl.edu.uj.ii.smartdom.server.listeners.GetTempSubscriberListener;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeteorologicalStationFragment extends MainSmartFragment implements GetCO2SubscriberListener,
        GetCOSubscriberListener, GetGasSubscriberListener, GetHumiditySubscriberListener, GetTempSubscriberListener {

    public static final String TAG = MeteorologicalStationFragment.class.getName();

    @BindView(R.id.temperature_textView)
    TextView temperatureTextView;

    @BindView(R.id.humidity_textView)
    TextView humidityTextView;

    @BindView(R.id.lpg_textView)
    TextView co2TextView;

    @BindView(R.id.co_textView)
    TextView coTextView;

    @BindView(R.id.smoke_textView)
    TextView gasTextView;

    Subscription tempSubscription, humiditySubscription, coSubscription, co2Subscription, gasSubscription;

    private MeteoModule meteoModule = new MeteoModule("1234", "", null, ConnectionType.BLE, "123456789");


    public MeteorologicalStationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_meteorological_station, container, false);

        ButterKnife.bind(this, fragmentView);
        setActionBarName();
        return fragmentView;
    }


    @Override
    public void onConnectionError() {
        Snackbar.make(getView(), R.string.module_connection_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected String getActionBarTitle() {
        return meteoModule.getName();
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

    @OnClick(R.id.reconnect_ble_button)
    public void onRefreshMeteoButtonClick() {
        coSubscription = SmartDomService.getInstance().getCO(this, getAuth(), meteoModule);
        co2Subscription = SmartDomService.getInstance().getCO2(this, getAuth(), meteoModule);
        gasSubscription = SmartDomService.getInstance().getGas(this, getAuth(), meteoModule);
        humiditySubscription = SmartDomService.getInstance().getHumidity(this, getAuth(), meteoModule);
        tempSubscription = SmartDomService.getInstance().getTemperature(this, getAuth(), meteoModule);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (coSubscription != null)
            coSubscription.unsubscribe();
        if (co2Subscription != null)
            co2Subscription.unsubscribe();
        if (gasSubscription != null)
            gasSubscription.unsubscribe();
        if (humiditySubscription != null)
            humiditySubscription.unsubscribe();
        if (tempSubscription != null)
            tempSubscription.unsubscribe();
    }
}
