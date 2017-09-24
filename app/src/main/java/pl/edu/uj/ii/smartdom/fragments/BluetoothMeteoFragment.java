package pl.edu.uj.ii.smartdom.fragments;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.database.MeteoModule;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class BluetoothMeteoFragment extends MainSmartFragment {

    public static final String TAG = MeteorologicalStationFragment.class.getName();

    @BindView(R.id.temperature_textView)
    TextView temperatureTextView;

    @BindView(R.id.humidity_textView)
    TextView humidityTextView;

    @BindView(R.id.lpg_textView)
    TextView lpgTextView;

    @BindView(R.id.co_textView)
    TextView coTextView;

    @BindView(R.id.smoke_textView)
    TextView smokeTextView;

    BluetoothDevice device;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;

    public static final int BLE_REQUEST = 1;
    public final static UUID UUID_HM_RX_TX = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");

    private BluetoothGattCharacteristic characteristicTX;
    private BluetoothGattCharacteristic characteristicRX;

    private MeteoModule meteoModule;
    private Timer timer = new Timer("meteoTimer");
    private BleTimerTask timerTask;

    public BluetoothMeteoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_meteorological_station, container, false);
        ButterKnife.bind(this, fragmentView);
        initComponents();
        setActionBarName();

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initBluetooth();
    }

    private void initComponents() {
        if (getArguments() != null && getArguments().containsKey("moduleId")) {
            meteoModule = MeteoModule.findById(MeteoModule.class, getArguments().getLong("moduleId"));
        }
    }

    private void initBluetooth() {
        mBluetoothAdapter = ((BluetoothManager) getMainActvity().getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        device = mBluetoothAdapter.getRemoteDevice(meteoModule.getAddress());

        if (!mBluetoothAdapter.isEnabled()) {
            Intent turnBLETon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBLETon, BLE_REQUEST);
        } else {
            mBluetoothGatt = device.connectGatt(getContext(), false, mGattCallback);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BLE_REQUEST:
                if (resultCode == RESULT_OK) {
                    mBluetoothGatt = device.connectGatt(getContext(), false, mGattCallback);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onConnectionError() {
        if (getView() != null)
            Snackbar.make(getView(), R.string.module_connection_error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected String getActionBarTitle() {
        return meteoModule.getName();
    }

    @OnClick(R.id.reconnect_ble_button)
    public void reconnect() {
        disconnectBLE();
        initBluetooth();
    }

    public void onRefreshMeteoButtonClick() {
        if (characteristicTX != null) {
            characteristicTX.setValue(new byte[]{0});
            mBluetoothGatt.writeCharacteristic(characteristicTX);
            mBluetoothGatt.setCharacteristicNotification(characteristicRX, true);
        }
        if (characteristicTX != null) {
            characteristicTX.setValue(new byte[]{1});
            mBluetoothGatt.writeCharacteristic(characteristicTX);
            mBluetoothGatt.setCharacteristicNotification(characteristicRX, true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        disconnectBLE();
    }

    private void disconnectBLE() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        characteristicTX = null;
        characteristicRX = null;

        if (timerTask != null)
            timerTask.cancel();
        timerTask = null;
    }

    private final BluetoothGattCallback mGattCallback =
            new BluetoothGattCallback() {
                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        Log.i(TAG, "Connected to GATT server.");
                        Log.i(TAG, "Attempting to start service discovery:" + mBluetoothGatt.discoverServices());

                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        disconnectBLE();
                        onConnectionError();
                        Log.i(TAG, "Disconnected from GATT server.");
                    }
                }

                @Override
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        for (BluetoothGattService gattService : gatt.getServices()) {
                            if (gattService.getCharacteristic(UUID_HM_RX_TX) != null) {
                                Log.d(TAG, "found characteristic: RX/TX");
                                characteristicTX = gattService.getCharacteristic(UUID_HM_RX_TX);
                                characteristicRX = gattService.getCharacteristic(UUID_HM_RX_TX);

                                timerTask = new BleTimerTask();
                                timer.schedule(timerTask, Calendar.getInstance().getTime(), 2000);
                            }
                        }
                    } else {
                        Log.w(TAG, "onServicesDiscovered received: " + status);
                    }
                }

                @Override
                public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                }

                @Override
                public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                }

                @Override
                public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
                    super.onCharacteristicChanged(gatt, characteristic);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String value = new String(characteristic.getValue(), "UTF_8");
                                Log.d(TAG, "characteristic changed value:" + value);
                                updateView(value);
                            } catch (UnsupportedEncodingException | NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };

    private void updateView(String data) {
        if (!TextUtils.isEmpty(data)) {
            String[] msg = data.split(":");
            if (msg.length == 2) {
                switch (msg[0]) {
                    case "temp":
                        temperatureTextView.setText(msg[1]);
                        break;
                    case "humi":
                        humidityTextView.setText(msg[1]);
                        break;
                    case "lpg":
                        lpgTextView.setText(msg[1]);
                        break;
                    case "CO":
                        if (Double.valueOf(msg[1]) >= 50)
                            coTextView.setTextColor(Color.RED);
                        else
                            coTextView.setTextColor(getResources().getColor(R.color.dark_gray));
                        coTextView.setText(msg[1]);
                        break;
                    case "smoke":
                        smokeTextView.setText(msg[1]);
                        break;
                    default:
                }
            }
        }
    }

    class BleTimerTask extends TimerTask {
        @Override
        public void run() {
            onRefreshMeteoButtonClick();
        }
    }
}
