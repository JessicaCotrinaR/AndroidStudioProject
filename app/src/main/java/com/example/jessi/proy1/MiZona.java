package com.example.jessi.proy1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


public class MiZona extends Activity{

    private LocationManager locationManager;    private Context contexto;
    private Intent serviceIntent;               private WebView mapView = null;
    //Estado de GPS y Red
    boolean isGPSEnabled = false;               boolean isNetworkEnabled = false;
    String lat = "", lng = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_zona);
        contexto = this.getApplication().getApplicationContext();
        serviceIntent = new Intent(this, GPSService.class);
        mapView = (WebView) findViewById(R.id.mapView);
        mapView.getSettings().setJavaScriptEnabled(true);
        mapView.getSettings().setBuiltInZoomControls(true);
        mapView.loadUrl("file:///android_asset/index.html");

        locationManager = (LocationManager) contexto.getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        //Obtener el estado de la red
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled) {
            DialogFragment confirmGPS = new GPSSettingsDialogFragment();
            confirmGPS.show(getFragmentManager(), "gpsConfirm1");
        } else {  // Si esta habilitado el GPS obtener lat/long
            if (isGPSEnabled) {
                this.startService(serviceIntent);
            } else {
                DialogFragment confirmGPS = new GPSSettingsDialogFragment();
                confirmGPS.show(getFragmentManager(), "gpsConfirm2");
            }
        }

        Button gpsStopBtn = (Button) findViewById(R.id.stopService);
        gpsStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Detener el servicio
                getApplication().stopService(serviceIntent);
            }

        });
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                lat = bundle.getString("lat");         lng = bundle.getString("long");
                //lat = String.valueOf(Double.parseDouble(lat));
                //lng = String.valueOf(Double.parseDouble(lng));
                Time time = new Time();
                time.setToNow();

                if (mapView != null){
                    mapView.loadUrl("javascript:updateLocation("+lat+","+lng+",\""
                            +time.format("%d-%m-%Y %H:%M:%S")+"\")");
                }
                CharSequence text = "GPS Localización Actualizada";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    };

    @Override
    public void onResume() {   /* Actualiza las posiciones */
        super.onResume();
        this.registerReceiver(receiver, new IntentFilter(GPSService.COORDSNOTIFICATION));
    }
    @Override
    public void onPause() {    /* Remover locationlistener cuando la interfaz está en pausa */
        super.onPause();
        this.unregisterReceiver(receiver);
    }
    public class GPSSettingsDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
            builder.setMessage("GPS no está habilitado. Desea ir al menú para habilitar?")
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            contexto.startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            // Crea la alerta de retorno
            return builder.create();
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
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
    }*/

}
