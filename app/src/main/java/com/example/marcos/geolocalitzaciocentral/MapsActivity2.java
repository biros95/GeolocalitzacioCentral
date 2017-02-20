package com.example.marcos.geolocalitzaciocentral;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity2 extends Fragment implements OnMapReadyCallback {

    int numMarkersInRainbow[] =
            {

                    R.drawable.bus1,
                    R.drawable.bus2,
                    R.drawable.bus3,
                    R.drawable.bus4,
                    R.drawable.bus5,
                    R.drawable.bus6,
                    R.drawable.bus7,
                    R.drawable.bus8,
                    R.drawable.bus9,
                    R.drawable.bus10,

            };

    private GoogleMap mMap;
    MapsActivity2.getLastPositionAllBus glpab = new MapsActivity2.getLastPositionAllBus();
    LatLng[] arrayPosiciones;
    String[][] arrayDatos;
    LatLngBounds.Builder builder;
    String matricula, data;

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        matricula = getArguments().getString("edttext");
        Log.i(matricula, " LOG");
        return inflater.inflate(R.layout.activity_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        builder = new LatLngBounds.Builder();

        glpab.execute();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


    public class getLastPositionAllBus extends AsyncTask<Void, Void, Boolean> {

        public getLastPositionAllBus() {
        }

        protected Boolean doInBackground(Void... params) {

            boolean correcto = true;
            URL url = null;
            try {
                url = new URL("http://192.168.1.133:8080/WebService2/webresources/generic/ultimasPosDeUnBus/+" +matricula);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("content-type", "application/json");
                InputStream in = new BufferedInputStream(con.getInputStream());

                con.connect();

                InputStreamReader isw = new InputStreamReader(in);

                BufferedReader br = new BufferedReader(isw);
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }


                JSONArray arrayPos = new JSONArray(sb.toString());
                arrayPosiciones = new LatLng[arrayPos.length()];
                arrayDatos = new String[arrayPos.length()][arrayPos.length()];
                for (int i = 0; i < arrayPos.length(); i++) {

                    JSONObject pos = arrayPos.getJSONObject(i);
                    arrayDatos[i][0] = pos.getString("matricula");

                    double latitud = pos.getDouble("latitud");
                    double longitud = pos.getDouble("longitud");
                    arrayDatos[i][1] = pos.getString("data");
                    arrayPosiciones[i] = new LatLng(latitud, longitud);

                }
                if (!con.equals("true")) {
                    correcto = true;
                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                correcto = false;
            }
            return correcto;
        }

        /**
         * Metodo que se realiza despues de ejecutarse el metodo onBackground para decirnos basicamente
         * Si se ha realizado o no el Insert Into
         *
         * @param result
         */
        protected void onPostExecute(Boolean result) {

            if (result) {

                Toast.makeText(getActivity(), "Posiciones obtenidas", Toast.LENGTH_SHORT).show();
                Log.i("Posiciones obtenidas", "1");
                for (int i = 0; i < arrayPosiciones.length; i++) {

                    mMap.addMarker(new MarkerOptions().position(arrayPosiciones[i]).title("Matricula:" +
                            " " + arrayDatos[i][0]).snippet("Ultima posición"
                            + arrayDatos[i][1]).icon(BitmapDescriptorFactory.fromResource(numMarkersInRainbow[i])));

                }
                zoomCamara(arrayPosiciones);
            } else {
                Toast.makeText(getActivity(), "Sense posicions", Toast.LENGTH_SHORT).show();
                Log.i("Sin", "Posiciones");
            }
        }

        private void zoomCamara(LatLng[] posiciones) {
            //the include method will calculate the min and max bound.
            for (int i = 0; i < arrayPosiciones.length; i++) {
                builder.include(arrayPosiciones[i]);


            }
            LatLngBounds bounds = builder.build();

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.10); // offset from edges of the map 12% of screen

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

            mMap.animateCamera(cu);
        }


    }
}