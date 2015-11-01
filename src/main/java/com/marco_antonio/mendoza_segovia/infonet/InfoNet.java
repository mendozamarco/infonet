package com.marco_antonio.mendoza_segovia.infonet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by marco on 14-10-15.
 * Class: InfoNet
 * version: 0.0.1
 * Copyright: Ing. Mendoza Segovia Marco Antonio
 * email: mendozamarco87@gmail.com - mendozmarco87@hotmail.com
 */
public class InfoNet {

    private InfoNetView view;
    private View widget;
    private int colorT1;
    private int colorT2;
    private String msgT1;
    private String msgT2;
    private int imageI1;
    private int imageI2;
    private int tiempo=8000;//tiempo por defecto equivalente a 8 segundos
    private InfoNetReceiver receiver;
    private Activity activity;
    public enum Tipo{
        MSG,ICON,INFONETVIEW
    }
    private Tipo tipo;

    private Object lock=new Object();
    private Handler infoHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ocultarWidget2(msg);
        }
    };

    /**
     * Constructor para crear un mensaje dinamico el cual aparecera indicando el estado de la conexion
     * @param view variable de tipo TextView la cual debera ubicar en su Layout sera el widget de informacion visual para el usuario final
     * @param color1 Variable de tipo Color=int. indica el color de background del TextView cuando el estado es: CONECTADO
     * @param color2 Variable de tipo Color=int. indica el color de background del TextView cuando el estado es: DESCONECTADO
     * @param msg1 indica el mensaje cuando el estado es: CONECTADO
     * @param msg2 indica el mensaje cuando el estado es: DESCONECTADO
     */
    public InfoNet(@NonNull TextView view, int color1, int color2,@NonNull String msg1,@NonNull String msg2){
        this.widget=view;
        this.colorT1=color1;
        this.colorT2=color2;
        this.msgT1=msg1;
        this.msgT2=msg2;
        this.tipo=Tipo.MSG;
        receiver = new InfoNetReceiver(this);
        view.setVisibility(View.INVISIBLE);
    }

    /**
     * Constructor para crear una imagen dinamica la cual aparecera indicando el estado de la conexion
     * @param view variable de tipo ImageView es el componente que debera ubicar en su layout, puede darle todos los estilos q vea necesario
     * @param image1 variable de tipo int(id de recurso del sistema) indica el id de la imagen que desea aparecer cuando el estado=CONECTADO
     * @param image2 variable de tipo int(id de recurso del sistema) indica el id de la imagen que desea aparecer cuando el estado=DESCONECTADO
     */
    public InfoNet(@NonNull ImageView view, int image1, int image2){
        this.widget=view;
        this.imageI1=image1;
        this.imageI2=image2;
        this.tipo=Tipo.ICON;
        receiver = new InfoNetReceiver(this);
        view.setVisibility(View.INVISIBLE);
    }

    public InfoNet(@NonNull InfoNetView view,Tipo tipo){
        this.view=view;
        this.tipo=tipo;
        this.view.init(this.tipo);
        receiver = new InfoNetReceiver(this);
    }


    public View getWidget() {
        return widget;
    }

    public Tipo getTipo() {
        return tipo;
    }

    /**
     * obtiene el tiempo que se muestra el widget cada vez que ocurre el evento de cambio de estado de la conexion
     * @return variable de tipo int basado en milisegundos
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * Puede cambiar el tiempo que se muestra el widget cada vez que ocurre el evento de cambio de estado de la conexion
     * @param tiempo variable de tipo int basado en milisegundos
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public InfoNetView getInfoNetView() {
        return view;
    }

    public InfoNet(InfoNetView view,TextView msg) {
        this.view = view;
        this.tipo=Tipo.MSG;
        this.view.setTipo(tipo);
        this.view.setMsg(msg);
        receiver = new InfoNetReceiver(this.view);
    }

    public InfoNet(InfoNetView view,ImageView icon) {
        this.view = view;
        this.tipo=Tipo.ICON;
        this.view.setTipo(tipo);
        this.view.setIcon(icon);
        receiver = new InfoNetReceiver(this.view);
    }

    public void iniciar(Context context){
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);
    }

    public void detener(Context context){
        context.unregisterReceiver(receiver);
    }

    public void ocultarWidget(){
        Object var3=this.lock;
        synchronized (this.lock) {
            widget.setVisibility(View.INVISIBLE);
        }
    }

    private void ocultarWidget2(Message msg) {
        Object var2=this.lock;
        synchronized (this.lock){
            if(msg.obj==Boolean.TRUE){
                ocultarWidget();
            }
        }
    }

    public void mostrarWidget(boolean info){
        Object Object1=this.lock;
        synchronized (this.lock){
            if(!info){
                noHayConexion();
            }else{
                siHayConexion();
            }
            widget.setVisibility(View.VISIBLE);
            TimerTask tarea=new TimerTask() {
                @Override
                public void run() {
                    Message msg=new Message();
                    msg.obj=Boolean.TRUE;//manda un mesaje para ocultar el widget
                    infoHandler.sendMessage(msg);
                }
            };
            Timer tiempo=new Timer();
            tiempo.schedule(tarea, this.tiempo);//despues del tiempo transcurrido mandar el mensaje

        }
    }

    private void siHayConexion() {
        if(tipo==Tipo.MSG){
            ((TextView)widget).setText(msgT1);
            ((TextView)widget).setBackgroundColor(colorT1);
        }else if(tipo==Tipo.ICON){
            ((ImageView)widget).setImageResource(imageI1);
        }
    }

    private void noHayConexion(){
        if(tipo==Tipo.MSG){
            ((TextView)widget).setText(msgT2);
            ((TextView)widget).setBackgroundColor(colorT2);
        }else if(tipo==Tipo.ICON){
            ((ImageView)widget).setImageResource(imageI2);
        }
    }
}
