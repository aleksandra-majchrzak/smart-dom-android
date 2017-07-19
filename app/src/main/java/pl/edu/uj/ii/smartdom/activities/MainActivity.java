package pl.edu.uj.ii.smartdom.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.uj.ii.smartdom.Constants;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.fragments.ColorPickerFragment;
import pl.edu.uj.ii.smartdom.fragments.DoorMotorFragment;
import pl.edu.uj.ii.smartdom.fragments.HomesFragment;
import pl.edu.uj.ii.smartdom.fragments.MainFragment;
import pl.edu.uj.ii.smartdom.fragments.MeteorologicalStationFragment;
import pl.edu.uj.ii.smartdom.fragments.RoomsFragment;
import pl.edu.uj.ii.smartdom.fragments.SettingsFragment;
import pl.edu.uj.ii.smartdom.server.entities.Authentication;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private int CURRENT_NAV_ITEM_ID = 0;

    private Authentication authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initComponents();
    }

    private void initComponents() {

        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MainFragment(), MainFragment.TAG)
                .commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.getHeaderView(0)
                .findViewById(R.id.app_imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MainFragment(), MainFragment.TAG)
                        .commit();

                navigationView.getMenu().findItem(CURRENT_NAV_ITEM_ID).setChecked(false);
                CURRENT_NAV_ITEM_ID = 0;
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        CURRENT_NAV_ITEM_ID = id;

        if (id == R.id.nav_change_home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomesFragment(), HomesFragment.TAG)
                    .commit();

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment(), SettingsFragment.TAG)
                    .commit();
        } else if (id == R.id.nav_family) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ColorPickerFragment(), ColorPickerFragment.TAG)
                    .commit();
        } else if (id == R.id.nav_meteo) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MeteorologicalStationFragment(), MeteorologicalStationFragment.TAG)
                    .commit();
        } else if (id == R.id.nav_motor) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DoorMotorFragment(), DoorMotorFragment.TAG)
                    .commit();
        } else if (id == R.id.nav_rooms) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RoomsFragment(), RoomsFragment.TAG)
                    .commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Authentication getAuthentication() {
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, 0);
        if (authentication == null)
            authentication = new Authentication(prefs.getString(Constants.login, ""), prefs.getString(Constants.token, ""));

        return authentication;
    }

    public void saveAuthentication(String login, String token) {
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, 0);
        prefs.edit().putString(Constants.login, login).putString(Constants.token, token).apply();

        authentication = new Authentication(login, token);
    }
}
