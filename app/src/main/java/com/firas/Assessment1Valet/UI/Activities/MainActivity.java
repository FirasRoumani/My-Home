package com.firas.Assessment1Valet.UI.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.firas.Assessment1Valet.Application.MyHomeApp;
import com.firas.Assessment1Valet.R;
import com.firas.Assessment1Valet.UI.Activities.Devices.DeviceListFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView nav_view;
    private boolean isDrawerOpen = false;
    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        ((MyHomeApp) getApplication()).getAppComponent().inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        drawer = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        setUpNavigationView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isDrawerOpen) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (!searchView.isIconified()) {
                searchView.setIconified(true);
                return;
            }
            super.onBackPressed();
        }

    }

    private void setUpNavigationView() {

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                drawer.closeDrawer(GravityCompat.START);
                nav_view.setCheckedItem(id);
                switch (id) {
                    case R.id.home:

                        loadFragment(getDeviceFragment(false));
                        return true;
                    case R.id.settings:
                        Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.myDevices:
                        loadFragment(getDeviceFragment(true));
                    default:
                        return true;
                }

            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, null, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpen = false;
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpen = true;
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        loadFragment(getDeviceFragment(false));
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Devices_container, fragment);
        transaction.commit();
    }

    private Fragment getDeviceFragment(boolean isFavorite) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(DeviceListFragment.BUNDLE_KEY_FAVORITE, isFavorite);

        DeviceListFragment deviceListFragment = new DeviceListFragment();
        deviceListFragment.setArguments(bundle);
        return deviceListFragment;
    }
}


