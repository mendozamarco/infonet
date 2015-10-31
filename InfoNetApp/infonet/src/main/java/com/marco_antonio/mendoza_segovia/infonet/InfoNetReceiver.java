package com.marco_antonio.mendoza_segovia.infonet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by marco on 17-10-15.
 */
public class InfoNetReceiver extends BroadcastReceiver {

    private InfoNet widget;
    private TimerTask tarea;
    private Thread hilo;
    public enum Tipo{
        MSG,ICON
    }
    private Tipo tipo;

    public InfoNetReceiver() {
    }

    public InfoNetReceiver(InfoNet view) {
        widget=view;
        hilo=null;
    }
    public InfoNetReceiver(InfoNetView view) {
        hilo=null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Intent: ", intent.toString());
        switch (intent.getAction()) {
            case ConnectivityManager.CONNECTIVITY_ACTION:
                informar(context);
                break;

            case Constantes.ACTION_RUN_SERVICE:
                String infonet2= intent.getStringExtra(Constantes.INFONET);
                Log.d("Receiver: ",infonet2);
                //widget.mostrarWidget(infonet2);
                break;

            case Constantes.ACTION_EXIT_SERVICE:
                Log.d("Receiver: ", "saliendo..");
                break;
        }
    }

    public void informar(Context context){
        VerifNet verificar=new VerifNet();
        String info="";
        boolean b=false;
        if(verificar.verificarConexion(context)){
            info="SI hay Conexion";
            b=true;
        }else{
            info="NO hay Conexion..";
        }
        Log.d("Receiver: ", info);

        if(widget.getTipo()== InfoNet.Tipo.INFONETVIEW){
            widget.getInfoNetView().mostrarWidget(info);
        }else{
            widget.mostrarWidget(b);
        }
    }

}
