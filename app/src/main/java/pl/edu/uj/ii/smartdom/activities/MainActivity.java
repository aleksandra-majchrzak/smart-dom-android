package pl.edu.uj.ii.smartdom.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.uj.ii.smartdom.Constants;
import pl.edu.uj.ii.smartdom.R;
import pl.edu.uj.ii.smartdom.enums.SmartMenuItem;
import pl.edu.uj.ii.smartdom.fragments.AllModulesFragment;
import pl.edu.uj.ii.smartdom.fragments.MainFragment;
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
    private boolean isBackFirstPressed = true;

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

        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (TextUtils.isEmpty(getAuthentication().getToken())) {
                    navigationView.getMenu().setGroupVisible(R.id.private_menu, false);
                    navigationView.getMenu().getItem(SmartMenuItem.LogOut).setVisible(false);
                    drawerView.findViewById(R.id.username_textView).setVisibility(View.INVISIBLE);
                } else {
                    navigationView.getMenu().setGroupVisible(R.id.private_menu, true);
                    navigationView.getMenu().getItem(SmartMenuItem.LogOut).setVisible(true);
                    drawerView.findViewById(R.id.username_textView).setVisibility(View.VISIBLE);
                    ((TextView) drawerView.findViewById(R.id.username_textView)).setText(getAuthentication().getUsername());
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        toggle.syncState();

        navigationView.getHeaderView(0)
                .findViewById(R.id.app_imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MainFragment(), MainFragment.TAG)
                        .commit();

                if (navigationView.getMenu().findItem(CURRENT_NAV_ITEM_ID) != null)
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

            if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
                if (isBackFirstPressed) {
                    isBackFirstPressed = false;
                    Snackbar.make(findViewById(android.R.id.content), R.string.click_to_exit, Snackbar.LENGTH_LONG)
                            .show();

                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isBackFirstPressed = true;
                        }
                    }, 2000);
                } else {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @SuppressLint("CommitPrefEdits")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        CURRENT_NAV_ITEM_ID = id;

        if (id == R.id.nav_logout) {
            SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, 0);
            prefs.edit()
                    .remove(Constants.login)
                    .remove(Constants.token)
                    .commit();
            authentication = new Authentication("", "", "");
            navigationView.getMenu().setGroupVisible(R.id.private_menu, false);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment(), MainFragment.TAG)
                    .commit();

            drawer.closeDrawer(GravityCompat.START);
            return false;
        } else if (id == R.id.nav_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SettingsFragment(), SettingsFragment.TAG)
                    .commit();
        } else if (id == R.id.nav_rooms) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RoomsFragment(), RoomsFragment.TAG)
                    .commit();
        } else if (id == R.id.nav_modules) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AllModulesFragment(), AllModulesFragment.TAG)
                    .commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Authentication getAuthentication() {
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, 0);
        if (authentication == null)
            authentication = new Authentication(prefs.getString(Constants.login, ""),
                    prefs.getString(Constants.token, ""),
                    prefs.getString(Constants.WEB_SERVER_PREFERENCE, ""));

        return authentication;
    }

    public void saveAuthentication(String login, String token) {
        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFERENCES, 0);
        prefs.edit().putString(Constants.login, login).putString(Constants.token, token).apply();

        authentication = new Authentication(login, token, prefs.getString(Constants.WEB_SERVER_PREFERENCE, ""));
    }
}
