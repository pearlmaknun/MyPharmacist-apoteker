package io.pearlmaknun.mypharmacist_apoteker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.pearlmaknun.mypharmacist_apoteker.data.Session;
import io.pearlmaknun.mypharmacist_apoteker.fragment.DashboardFragment;
import io.pearlmaknun.mypharmacist_apoteker.fragment.ProfileFragment;
import io.pearlmaknun.mypharmacist_apoteker.model.RegisterResponse;

import static io.pearlmaknun.mypharmacist_apoteker.data.Constan.LOKASI_UPDATE;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    BottomNavigationView bottomNavigationView;

    Fragment fragment;

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        session = new Session(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        requestLocationUpdates();

        if (savedInstanceState == null) {
            fragment = new DashboardFragment();
            callFragment(fragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                callFragment(fragment);
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                callFragment(fragment);
                break;
        }
        return true;
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();
        //Specify how often your app should request the device’s location
        request.setInterval(5000);
        //Get the most accurate location data available//
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        //If the app currently has access to the location permission...//
        if (permission == PackageManager.PERMISSION_GRANTED) {
            //...then request location updates//
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    Log.e("location realtime", ""+location.getLatitude() +", "+location.getLongitude());
                    AndroidNetworking.put(LOKASI_UPDATE + session.getUser().getApotekerId())
                            .addHeaders("Content-Type", "application/json")
                            .addHeaders("Authorization", "Bearer " + session.getToken())
                            .addHeaders("device_id", session.getDeviceId())
                            .addQueryParameter("latitude", String.valueOf(location.getLatitude()))
                            .addQueryParameter("longitude", String.valueOf(location.getLongitude()))
                            .build()
                            .getAsObject(RegisterResponse.class, new ParsedRequestListener() {
                                @Override
                                public void onResponse(Object response) {
                                    if (response instanceof RegisterResponse) {
                                        RegisterResponse response1 = (RegisterResponse) response;
                                        if (!response1.getStatus()) {
                                            Log.e("error", response1.getMessage());
                                        }
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Log.e("anerror lokasi", anError.getErrorDetail());
                                }

                            });
                    //Get a reference to the database, so your app can perform read and write operations//
                    /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        //Save the location data to the database//
                        ref.setValue(location);
                    }*/
                }
            }, null);
        }
    }
}