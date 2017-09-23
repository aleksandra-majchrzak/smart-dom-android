package pl.edu.uj.ii.smartdom.listeners;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.adapters.ModulesListAdapter;
import pl.edu.uj.ii.smartdom.database.Module;
import pl.edu.uj.ii.smartdom.fragments.BlindMotorFragment;
import pl.edu.uj.ii.smartdom.fragments.ColorPickerFragment;
import pl.edu.uj.ii.smartdom.fragments.DoorMotorFragment;
import pl.edu.uj.ii.smartdom.fragments.MeteorologicalStationFragment;

/**
 * Created by Mohru on 23.09.2017.
 */

public class OnModuleItemClickListener implements AdapterView.OnItemClickListener {

    private AppCompatActivity activity;
    private ModulesListAdapter adapter;

    public OnModuleItemClickListener(AppCompatActivity activity, ModulesListAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Module currentModule = adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putLong("moduleId", currentModule.getId());
        switch (currentModule.getType()) {
            case LIGHT_MODULE: {
                ColorPickerFragment fragment = new ColorPickerFragment();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, ColorPickerFragment.TAG)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case DOOR_MOTOR_MODULE: {
                DoorMotorFragment fragment = new DoorMotorFragment();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, DoorMotorFragment.TAG)
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case METEO_MODULE: {
                MeteorologicalStationFragment fragment = new MeteorologicalStationFragment();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, MeteorologicalStationFragment.TAG)
                        .addToBackStack(null)
                        .commit();
            }
            break;
            case BLIND_MOTOR_MODULE: {
                BlindMotorFragment fragment = new BlindMotorFragment();
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment, BlindMotorFragment.TAG)
                        .addToBackStack(null)
                        .commit();
                break;
            }
        }
    }
}
