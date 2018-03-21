package com.example.jessi.proy1;

/**
 * Created by Jessi on 04/06/2015.
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.os.Process;


public class GPSService extends Service {

    private Looper mServiceLooper;  private ServiceHandler mServiceHandler;
    private Location location;	  private LocationManager locationManager;
    public static final String COORDSNOTIFICATION =
            "com.android4.adminitrador.clase_05_1.service.receiver";

    // Envio de notification.
    int mNotificationId = 001;
    // Obtener el NotificationManager service
    NotificationManager mNotifyMgr;
    // La mínima distancia para cambio en metros
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 metros
    // El mínimo tiempo entre actualizacion en milisegundos
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minuto

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler implements LocationListener {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1==0){
                locationManager.removeUpdates((LocationListener) this);
            }else {

                if (location == null) {
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        // Initialize the location fields
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                }
            }
        }
        @Override
        public void onLocationChanged(Location location) {
            double lat = (double) (location.getLatitude());
            double lng = (double) (location.getLongitude());
            try {
                FileWriter fstream = new FileWriter(
                        Environment.getExternalStorageDirectory().getPath()
                                +"/coOrds.txt",true);
                BufferedWriter fbw = new BufferedWriter(fstream);
                fbw.write(String.valueOf(lng)+","+String.valueOf(lat)+",0");
                fbw.newLine();      fbw.close();
            }
            catch (IOException e) {
                Log.e("Exception", "Escritura con Error: " + e.toString());
            }
            Intent i = new Intent(COORDSNOTIFICATION);
            i.putExtra("lat", String.valueOf(lat)); i.putExtra("long", String.valueOf(lng));
            sendBroadcast(i);
        }
        @Override
        public void onProviderDisabled(String provider) {
            Log.e("Hilo de Trabajo", "Proveedor Deshabilitado " + provider);
        }
        @Override
        public void onProviderEnabled(String provider) {
            Log.e("Hilo de Trabajo", "Proveedor Habilitado " + provider);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    @Override
    public void onCreate() {
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Muestra la notificacion de inicio
        showNotification();

        // Inicia el hilo del servicio
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Obtener HandlerThread's Looper y usar.
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "GPS service Iniciando", Toast.LENGTH_SHORT).show();
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = 1;
        mServiceHandler.sendMessage(msg);
        // Si el servicio esta finalizado lo que se realiza es la restauracion
        return START_STICKY;
    }

    public class LocalBinder extends Binder {
        GPSService getService() {
            return GPSService.this;
        }
    }
    @Override
    public void onDestroy() {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = 0;		      mServiceHandler.sendMessage(msg);
        // Cancela la notificacion de persistencia.
        mNotifyMgr.cancel(mNotificationId);
        // Mostrar el usuario a detenido el servicio.
        Toast.makeText(this, "GPS service finalizada", Toast.LENGTH_SHORT).show();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void showNotification() {
        //Mostrar la notificacion de que el servicio esta activo
        Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("GPS Service")
                        .setContentText("GPS Service Running");
        Intent resultIntent = new Intent(this, MiZona.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this,0,resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
