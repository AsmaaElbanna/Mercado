package com.iti.mercado.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.mercado.R;
import com.iti.mercado.model.Address;


public class DeliveryActivity extends AppCompatActivity {

    //AutoCompleteTextView searchEditText;
    //Button getAddressButton;
    //FusedLocationProviderClient mFusedLocationClient;
    private ImageView backArrow;
    private TextInputLayout countryInputLayout;
    private Spinner governorateSpinner;
    private TextInputLayout areaInputLayout;
    private TextInputLayout streetInputLayout;
    private TextInputLayout nearestLandmarkInputLayout;
    private TextInputLayout mobileNumberInputLayout;
    private TextInputLayout buildingInputLayout;
    private TextInputLayout floorInputLayout;
    private TextInputLayout apartmentInputLayout;
    private Button saveAddressButton;
    private ProgressDialog progressDialog;
    private Address address;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        backArrow = findViewById(R.id.back_button);

        //searchEditText = findViewById(R.id.search_place);
        //getAddressButton = findViewById(R.id.get_delivery_address);

        countryInputLayout = findViewById(R.id.country);
        governorateSpinner = findViewById(R.id.governorate);
        areaInputLayout = findViewById(R.id.area);
        streetInputLayout = findViewById(R.id.street);
        nearestLandmarkInputLayout = findViewById(R.id.nearest_landmark);
        mobileNumberInputLayout = findViewById(R.id.mobile_number);
        buildingInputLayout = findViewById(R.id.building);
        floorInputLayout = findViewById(R.id.floor);
        apartmentInputLayout = findViewById(R.id.apartment);
        saveAddressButton = findViewById(R.id.save_address_bt);

        address = new Address();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.governorate_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        governorateSpinner.setAdapter(adapter);
        governorateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String t = parent.getItemAtPosition(position).toString();
                address.setGovernorate(t);
                //Toast.makeText(DeliveryActivity.this, t, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveAddressButton.setOnClickListener(v -> {
            countryInputLayout.setError(null);
            areaInputLayout.setError(null);
            streetInputLayout.setError(null);
            buildingInputLayout.setError(null);
            mobileNumberInputLayout.setError(null);
            getAddress(this);
        });
        //String[] countries = getResources().getStringArray(R.array.countries_array);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        //searchEditText.setAdapter(adapter);

        backArrow.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if (checkPermissions()) {getLastLocation();}
    }

    private void getAddress(Context context) {

        if (countryInputLayout.getEditText().getText().toString().isEmpty()) {
            countryInputLayout.setError("Enter your country");
            countryInputLayout.requestFocus();
        } else if (areaInputLayout.getEditText().getText().toString().isEmpty()) {
            areaInputLayout.setError("Enter your area");
            areaInputLayout.requestFocus();
        } else if (streetInputLayout.getEditText().getText().toString().isEmpty()) {
            streetInputLayout.setError("Enter your street");
            streetInputLayout.requestFocus();
        } else if (buildingInputLayout.getEditText().getText().toString().isEmpty()) {
            buildingInputLayout.setError("Enter your building number");
            buildingInputLayout.requestFocus();
        } else if (mobileNumberInputLayout.getEditText().getText().toString().isEmpty()) {
            mobileNumberInputLayout.setError("Enter your mobile number");
            mobileNumberInputLayout.requestFocus();
        } else {
            getAddressDataAndPushToFirebase(context);
        }

    }

    private void getAddressDataAndPushToFirebase(Context context) {
        address.setCountry(countryInputLayout.getEditText().getText().toString());
        address.setArea(areaInputLayout.getEditText().getText().toString());
        address.setStreet(streetInputLayout.getEditText().getText().toString());
        address.setNearestLandmark(nearestLandmarkInputLayout.getEditText().getText().toString());
        address.setMobileNumber(mobileNumberInputLayout.getEditText().getText().toString());
        address.setBuilding(buildingInputLayout.getEditText().getText().toString());
        address.setFloor(floorInputLayout.getEditText().getText().toString());
        address.setApartment(apartmentInputLayout.getEditText().getText().toString());

        DatabaseReference addressReference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        addressReference.child("address").setValue(address);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Wait few seconds...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(() -> {
            progressDialog.dismiss();
            finish();
        }, 1000);

    }

/*

    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocationData();
            } else {
                Toast.makeText(this, "Turn on Permissions", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                Constants.MAP_PERMISSION_ID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.MAP_PERMISSION_ID) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(0);
        //request.setFastestInterval(0);
        request.setNumUpdates(1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(request, callback, Looper.myLooper());
    }

    private final LocationCallback callback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();
            Toast.makeText(DeliveryActivity.this, location.getLatitude() + "  " + location.getLongitude(), Toast.LENGTH_LONG).show();
            searchEditText.setText((location.getLatitude() + "  " + location.getLongitude()));
            getStreetName(location);
        }
    };

    private void getStreetName(Location location) {
        Geocoder geocoder = new Geocoder(DeliveryActivity.this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addressList.get(0).getAddressLine(0);
            Toast.makeText(DeliveryActivity.this, address, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

*/
}