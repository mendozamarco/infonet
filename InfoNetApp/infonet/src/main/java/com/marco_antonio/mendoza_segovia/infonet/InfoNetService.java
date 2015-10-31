
/**
 * Created by marco on 14-10-15.
 * Class: InfoNet
 * version: 0.0.1
 * Copyright: Ing. Mendoza Segovia Marco Antonio
 * email: mendozamarco87@gmail.com - mendozamarco87@hotmail.com
 */
package com.marco_antonio.mendoza_segovia.infonet;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class InfoNetService extends Service {
    private static final String TAG = InfoNetService.class.getSimpleName();
    private final VerifNet verificar= new VerifNet();
    private Thread hilo=null;
    private TimerTask tarea;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado...");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        if(hilo==null||!hilo.isAlive()){
            hilo= new Thread(new Runnable() {
                @Override
                public void run() {

                    Log.d(TAG, "Servicio iniciado...");

                    Timer tiempo=new Timer();

                    tarea=new TimerTask() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Comprobando Conexion......");
                            String msj="";
                            if(verificar.verificarConexion(getApplicationContext())){
                                msj="SI hay Conexion";
                            }else{
                                msj="NO hay Conexion..";
                            }

                            Intent localIntent = new Intent(Constantes.ACTION_RUN_SERVICE)
                                    .putExtra(Constantes.INFONET, msj);

                            sendBroadcast(localIntent);

                        }
                    };


                    tiempo.scheduleAtFixedRate(tarea, 0, 15000);

                }
            });
            hilo.start();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        tarea.cancel();
        Intent localIntent = new Intent(Constantes.ACTION_EXIT_SERVICE);

        // Emitir el intent a la actividad
        LocalBroadcastManager.
                getInstance(InfoNetService.this).sendBroadcast(localIntent);
        Log.d(TAG, "Servicio destruido...");
    }


}
