package com.samir.TCCApp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.samir.TCCApp.DAO.ClientDAO;
import com.samir.TCCApp.R;
import com.samir.TCCApp.adapters.PlaceAutoSuggest;
import com.samir.TCCApp.classes.Addressess;
import com.samir.TCCApp.classes.Client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Locale;

import static com.samir.TCCApp.DAO.ClientDAO.client;
import static com.samir.TCCApp.utils.Helper.ARQUIVO_CLIENT;
import static com.samir.TCCApp.utils.Helper.hideKeyBoard;

public class AddressActivity extends FragmentActivity implements OnMapReadyCallback {
    private String ENTER = "\n";
    private GoogleMap mMap;
    private AutoCompleteTextView autoCompleteTextView;
    private SupportMapFragment mapFragment;
    private MarkerOptions markerOptions;
    private Marker marker;
    private ImageView clearButton, markerPin;
    private CardView btnAddress;
    private ProgressBar progressBar;
    public static Addressess addressess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        ref();

        markerPin.setVisibility(View.GONE);
        clearButton.setVisibility(View.GONE);

        autoCompleteTextView.setAdapter(new PlaceAutoSuggest(AddressActivity.this, android.R.layout.simple_list_item_1));

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0)
                    clearButton.setVisibility(View.VISIBLE);
                else
                    clearButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clearButton.setOnClickListener(s -> {
            autoCompleteTextView.setText("");
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        markerOptions = new MarkerOptions();
        markerOptions.alpha(0);

        autoCompleteTextView.setOnEditorActionListener((v, actionId, event) -> {

            if (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() || actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchOnMap();
                hideKeyBoard(getApplicationContext(), v);
            }
            return false;
        });

        btnAddress.setOnClickListener(a -> {
            String adr = autoCompleteTextView.getText().toString();
            if (!adr.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Confirmar endereço");
                builder.setMessage("Tem certeza que deseja utilizar este endereço?\n\n"
                        + adr /*+ ENTER
                        + addressess.getLogra() + ENTER
                        + addressess.getCEP() + ENTER
                        + addressess.getBairro() + ENTER
                        + addressess.getCidade()*/);
                builder.setPositiveButton("Sim", (dialog, which) -> {
//                        addressess = new Addressess();
                    addressess.setAddress(adr);
                    if (client == null) client = new Client();
                    client.setAddressess(addressess);
                    ClientDAO clientDAO = new ClientDAO(getApplicationContext());
                    clientDAO.updateClient(client, null);
//                    clientDAO.save();
                    finish();
                }).setNegativeButton("Não", (dialog, which) -> {

                });
                builder.create();
                builder.show();
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (addressess != null && addressess.getAddress() != null) {
            autoCompleteTextView.setText(addressess.getAddress());
            searchOnMap();
        } else {
            getUserLocation();
        }

        /*LatLng usuLocation = new LatLng(-23.5504604, -46.6331921);
        marker = mMap.addMarker(markerOptions.position(usuLocation).title("Sua localização"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usuLocation, 15));*/

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng pos = mMap.getCameraPosition().target;
                if (marker != null) {
                    marker.remove();
                    marker = mMap.addMarker(markerOptions.position(pos));
                    autoCompleteTextView.setText(getAddress(pos.latitude, pos.longitude));
//                    searchOnMap();
                }
            }
        });
    }

    private void getUserLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(AddressActivity.this).
                    requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            LocationServices.getFusedLocationProviderClient(AddressActivity.this)
                                    .removeLocationUpdates(this);

                            if (locationResult != null && locationResult.getLocations().size() > 0) {
                                int lastestLocationIndex = locationResult.getLocations().size() - 1;
                                double latitude = locationResult.getLocations().get(lastestLocationIndex).getLatitude();
                                double longitude = locationResult.getLocations().get(lastestLocationIndex).getLongitude();
                                LatLng usuLocation = new LatLng(latitude, longitude);
                                marker = mMap.addMarker(markerOptions.position(usuLocation).title("Sua localização"));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(usuLocation, 15));
                            }
                            progressBar.setVisibility(View.GONE);
                            markerPin.setVisibility(View.VISIBLE);
                            btnAddress.setClickable(true);
                        }
                    }, Looper.getMainLooper());
        }


    }

    private String getAddress(double latitude, double longitude) {

        String address = null;
        Geocoder geocoder;
        List<Address> addresses;

        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            //Resgatar bairro cidade etc
            setAddressObject(addresses.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    public void searchOnMap() {
        String location = autoCompleteTextView.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(AddressActivity.this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
                if (addressList.size() > 0) {
                    Address address = addressList.get(0);
//                    address.getThoroughfare(); retorna rua
                    //Resgatar bairro cidade etc
                    setAddressObject(address);

                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    if (marker != null) marker.remove();
                    marker = mMap.addMarker(markerOptions.position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    progressBar.setVisibility(View.GONE);
                    markerPin.setVisibility(View.VISIBLE);
                    btnAddress.setClickable(true);
                } else {
                    Toast.makeText(AddressActivity.this, "Nenhum resultado encontrado", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setAddressObject(Address address) {
        String cep = address.getPostalCode();
        String logra = address.getThoroughfare();
        String city = address.getLocality();
        String bairro = address.getSubLocality();

        if (cep != null) addressess.setCEP(cep.replace("-", ""));
        if (logra != null) addressess.setLogra(logra);
        if (bairro != null) addressess.setBairro(bairro);
        if (city == null) addressess.setCidade(address.getSubAdminArea());
        else addressess.setCidade(city);

        if (address.getSubThoroughfare() != null) client.setNumEdif(Integer.parseInt(address.getSubThoroughfare()));
    }

   /* private void save() {
        try {
            FileOutputStream fos = new FileOutputStream(this.getFileStreamPath(ARQUIVO_CLIENT));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(client);
            oos.close();
            fos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) finish();
    }

    public void ref() {
        btnAddress = findViewById(R.id.btnRegAddress);
        markerPin = findViewById(R.id.marker);
        autoCompleteTextView = findViewById(R.id.autocompleteTV);
        clearButton = findViewById(R.id.clearTextBtn);
        progressBar = findViewById(R.id.progressBarMap);
    }

}