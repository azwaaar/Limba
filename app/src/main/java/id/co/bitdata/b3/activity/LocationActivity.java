package id.co.bitdata.b3.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.haerul.bottomfluxdialog.BottomFluxDialog;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.databinding.ActivityLocationBinding;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    ActivityLocationBinding binding;
    ActionBar actionBar;

    double latitude, longtitude;
    Geocoder geocoder;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationManager lm;
    LatLng Latlng;
    Marker mCurrLocationMarker;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int UPDATE_INTERVAL = 10000;
    private static final int FATEST_INTERVAL = 5000;
    private static final int DISPLACEMENT = 10;
    private final boolean mRequestLocationUpdates = true;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;

    List<Address> addresses;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location);

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Lokasi Pengangkutan");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(this, Locale.getDefault());
        
        checkLocationPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.currentLocation);
        mapFragment.getMapAsync(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        // CHECK IF USER IS USING FAKE GPS OR NOT
                        if (location.isFromMockProvider()) {
                            Toast.makeText(this, "Device Anda terdeteksi menggunakan FAKE GPS!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.e("TERJADI MASALAH TERHADAP GPS: " , ex.getMessage());
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.e("TERJADI MASALAH TERHADAP NETWORK: " , ex.getMessage());
        }

        if (!gps_enabled && !network_enabled) {
            BottomFluxDialog.alertDialog(this)
                    .setTextTitle("AKTIFKAN GPS")
                    .setTextMessage("Aktifkan GPS pada perangkat Anda!")
                    .setImageDialog(R.drawable.danger)
                    .setAlertButtonText("OK")
                    .setAlertListener(() -> {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                        finish();
                    })
                    .show();
        }

        geocoder = new Geocoder(LocationActivity.this, Locale.getDefault());

    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                Log.e("PERMISSION LOKASI TIDAK DI AKTIFKAN", "PERMISSION LOKASI TIDAK DI AKTIFKAN");
//                FancyToast.makeText(this, "AKTIFKAN LOKASI ANDA TERLEBIH DAHULU", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                CommonsUtil.showDialog(this, "hmm", "error");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                buildAlertMessageNoGps();
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted. Do the
                // contacts-related task you need to do.
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

//                    if (mGoogleApiClient == null) {
//                        buildGoogleApiClient();
//                    }
                    mMap.setMyLocationEnabled(true);
                }

            } else {
                Log.e("PERMISSION LOCATION DENIED" , " PERMISSION LOCATION DENIED");

                // Permission denied, Disable the functionality that depends on this permission.
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GPS Anda terdeteksi tidak aktif, silahkan aktifkan terlebih dahulu sebelum menggunakan maps ini")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

//    private String displayLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return null;
//        }
//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (mLastLocation != null) {
//            latitude = mLastLocation.getLatitude();
//            longtitude = mLastLocation.getLongitude();
//            try {
//                addresses = geocoder.getFromLocation(latitude, longtitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                return addresses.get(0).getAddressLine(0);
//            } catch (IOException e) {
//                Toast.makeText(this, "Google Play Service Tidak Tersedia , Silahkan Buka Google Maps Terlebih Dahulu Setelah Itu Buka Kembali Aplikasi", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//
//
//        }
//        return null;
//    }
//
//    private void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(UPDATE_INTERVAL);
//        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
//    }
//
//    private void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }
//
//    private boolean checkPlayServices() {
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            }
//            return false;
//        }
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        } else {
//            Log.e("PERMISSION LOKASI TIDAK DI AKTIFKAN", "PERMISSION LOKASI TIDAK DI AKTIFKAN");
//        }
//    }

//    protected void startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
////        mLastLocation = location;
//        Latlng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }
////
//        String addr = displayLocation();
//        mCurrLocationMarker = mMap.addMarker(new MarkerOptions()
//                        .position(Latlng)
//                        .title(addr));
////
//////        mCurrLocationMarker.showInfoWindow();
////        latlngAwal = location.getLatitude() + "," + location.getLongitude();
////
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(Latlng));
////        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
//////        distanceWithMe = DistanceCalculator.distance(location.getLatitude(), location.getLongitude(), depotLat, depotLon);
////        if (addresses.isEmpty()) {
////            Log.e("Address tidak terdeteksi", "Address tidak terdeteksi");
////        } else {
////            binding.wasteTransportationET.setText(addresses.get(0).getAddressLine(0));
////        }
//    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setTrafficEnabled(true);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
//                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
        } else {
            Log.e("PERMISSIONS LOKASI TIDAK DI AKTIFKAN", "PERMISSIONS LOKASI TIDAK DI AKTIFKAN");
//            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                mMap.addMarker(markerOptions);
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    assert addresses != null;
                    binding.wasteTransportationET.setText(addresses.get(0).getAddressLine(0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }

//        if (fusedLocationClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }

//        if (fusedLocationClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }
    }
}