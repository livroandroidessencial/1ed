package br.com.livroandroid.location;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Date;

/**
 * Exemplo de MapFragment por XML
 *
 * @author Ricardo Lecheta
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "livroandroid";
    protected GoogleMap map;
    private SupportMapFragment mapFragment;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Configura o objeto GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Solicita as permissões
        String[] permissoes = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        PermissionUtils.validate(this, 0, permissoes);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada, agora é com você :-)
                alertAndFinish();
                return;
            }
        }
        // Se chegou aqui está OK :-)
    }

    private void alertAndFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d(TAG, "onMapReady: " + map);
        this.map = map;

        // Configura o tipo do mapa
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Conecta no Google Play Services
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        // Para o GPS
        stopLocationUpdates();
        // Desconecta
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        toast("Conectado no Google Play Services!");

        // Inicia o GPS
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        toast("Conexão interrompida.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        toast("Erro ao conectar: " + connectionResult);
    }

    private void toast(String s) {
        Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
    }

    // Inicia o monitoramento do GPS
    protected void startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates()");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Verifica permissões
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Este "if" o Android Studio pede para colocar.
            // Alguma permissão foi negada, agora é com você :-)
            alertAndFinish();
            return;
        }

        // Começa o monitoramento.
        FusedLocationProviderClient locClient = LocationServices.getFusedLocationProviderClient(this);
        locClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
    }

    // Para de monitorar o GPS
    protected void stopLocationUpdates() {
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
    }

    // Listener para monitorar o GPS
    private LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location location = locationResult.getLastLocation();
            // Atualiza o mapa
            setMapLocation(location);
        }
    };

    // Atualiza a coordenada do mapa
    private void setMapLocation(Location l) {
        if (map != null && l != null) {
            LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            map.animateCamera(update);

            Log.d(TAG, "setMapLocation: " + l);
            TextView text = (TextView) findViewById(R.id.text);
            String s = String.format("Última atualização %s", DateFormat.getTimeInstance().format(new Date()));
            s += String.format("\nLat/Lnt %f/%f, provider: %s", l.getLatitude(), l.getLongitude(), l.getProvider());
            text.setText(s);

            // Desenha uma bolinha vermelha
            CircleOptions circle = new CircleOptions().center(latLng);
            circle.fillColor(Color.RED);
            circle.radius(25); // Em metros
            map.clear();
            map.addCircle(circle);
        }
    }
}