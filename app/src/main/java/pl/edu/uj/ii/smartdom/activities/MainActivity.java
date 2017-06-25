package pl.edu.uj.ii.smartdom.activities;

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
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.enums.SmartMenuItem;
import pl.edu.uj.ii.smartdom.fragments.HomesFragment;
import pl.edu.uj.ii.smartdom.fragments.MainFragment;
import pl.edu.uj.ii.smartdom.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private int CURRENT_NAV_ITEM_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initComponents();
    }

    private void initComponents() {

        setSupportActionBar(toolbar);

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
        addCurrentRooms();
    }

    private void addCurrentRooms() {
        // dynamically add rooms to drawer
        navigationView.getMenu().getItem(SmartMenuItem.Rooms).getSubMenu().add(Menu.NONE, Menu.NONE, 0, "new room1");
        navigationView.getMenu().getItem(4).getSubMenu().add(Menu.NONE, Menu.NONE, 0, "new room2");
        navigationView.getMenu().getItem(4).getSubMenu().add(Menu.NONE, Menu.NONE, 0, "new room3");
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
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
