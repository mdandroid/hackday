package auspost.com.au.hackday;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import auspost.com.au.hackday.model.User;
import auspost.com.au.hackday.persistence.DatabaseManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewAddressActivity extends ActionBarActivity implements LocationListener {
    private Spinner spinner;
    private EditText add1, add2, suburb, postcode;
    private MapFragment map;
    private GoogleMap gMap;
    private int zoomLevel = 13;
    private LatLng defaultLatLng = new LatLng(-37.824044, 144.950479);
    private LatLng newLatLng = new LatLng(-37.812345, 144.969715);
    private TextView locationTv;
    private User user;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_full);
        locationTv = (TextView) findViewById(R.id.address);
        user = getIntent().getParcelableExtra("user");
        databaseManager = new DatabaseManager(this);
        /*spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        add1 = (EditText) findViewById(R.id.add_line1);
        add2 = (EditText) findViewById(R.id.add_line2);
        suburb = (EditText) findViewById(R.id.suburb);
        postcode = (EditText) findViewById(R.id.postcode);*/

        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        gMap = map.getMap();

        if (gMap!=null){
            gMap.getUiSettings().setCompassEnabled(true);
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setZoomControlsEnabled(true);
            gMap.getUiSettings().setAllGesturesEnabled(true);

            // Move the camera instantly to defaultLatLng.
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, zoomLevel));
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        }
        /*postcode.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, zoomLevel));
                gMap.addMarker(new MarkerOptions()
                        .position(newLatLng)
                        .title("Your New Location")
                        .snippet(getAddress())
                        .draggable(true))
                        .showInfoWindow();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_address, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getAddress(){
        return add1.getText() + " " + add2.getText() + " \n" + suburb.getText() + " " + postcode.getText() + " " + spinner.getSelectedItem().toString();
    }

    public void changeAddress(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Address Change");
        builder.setMessage("Are you happy to change your address to\n\n35 South Wharf Promenade\n" +
                "South Wharf VIC 3006" );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                gotoHistory();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }

    private void gotoHistory(){
        Intent intent = new Intent(this, HistoryActivity.class);
        Map<String, String> user1 = new HashMap<>();
        user1.put(DatabaseManager.VER, "30");
        user1.put(DatabaseManager.CHADD, "true");
        databaseManager.save(user.email, user1);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        gMap.clear();
        locationTv.setVisibility(View.VISIBLE);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        gMap.addMarker(new MarkerOptions().position(latLng)
                .title("Your New Location")
                .snippet("35 South Wharf Promenade\nSouth Wharf VIC 3006")
                .draggable(true)).showInfoWindow();
        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationTv.setText("35 South Wharf Promenade\nSouth Wharf VIC 3006");
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}
