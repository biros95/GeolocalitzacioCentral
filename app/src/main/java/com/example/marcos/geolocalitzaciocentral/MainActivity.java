package com.example.marcos.geolocalitzaciocentral;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class MainActivity extends AppCompatActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (savedInstanceState == null) {

        }
        /*
        //Eventos del Drawer Layout
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

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
        */

        navView = (NavigationView) findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new MapsActivity();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_1:

                                bundle.putString("edttext", getResources().getString(R.string.opcion_1));
                                // set Fragmentclass Arguments
                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);

                                Log.i("NavigationView", "Pulsada opción 1");
                                break;
                            case R.id.menu_opcion_2:
                                bundle.putString("edttext", getResources().getString(R.string.opcion_2));
                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);
                                break;
                            case R.id.menu_opcion_3:
                                bundle.putString("edttext", getResources().getString(R.string.opcion_3));
                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);
                                break;
                            case R.id.menu_opcion_4:
                                bundle.putString("edttext", getResources().getString(R.string.opcion_4));
                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);
                                break;
                            case R.id.menu_opcion_5:
                                bundle.putString("edttext", getResources().getString(R.string.opcion_5));
                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);
                                break;
                            case R.id.menu_opcion_6:
                                bundle.putString("edttext", getResources().getString(R.string.opcion_6));
                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);
                                break;
                            case R.id.menu_opcion_7:
                                bundle.putString("edttext", getResources().getString(R.string.opcion_7));
                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);
                                break;
                            case R.id.menu_opcion_8:
                                bundle.putString("edttext", getResources().getString(R.string.opcion_8));
                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);
                                break;
                            case R.id.menu_opcion_9:
                                bundle.putString("edttext", getResources().getString(R.string.opcion_9));

                                fragment = new MapsActivity2();
                                fragmentTransaction = true;
                                fragment.setArguments(bundle);
                                break;

                        }

                        if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}