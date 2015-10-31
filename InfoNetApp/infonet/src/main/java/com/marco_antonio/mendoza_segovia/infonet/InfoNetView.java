package com.marco_antonio.mendoza_segovia.infonet;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

/**
 * Created by marco on 18-10-15.
 */
public class InfoNetView extends RelativeLayout {
    private TextView msg;
    private ImageView icon;
    private InfoNet.Tipo tipo= InfoNet.Tipo.MSG;
    private Object lock=new Object();
    private Handler infoHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ocultarWidget2(msg);
        }
    };

    public InfoNetView(Context context) {
        super(context);
        initialize(context);
    }

    public InfoNetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context){
        inflate(context, R.layout.layaut_infonetview, this);
        init(this.tipo);
    }

    public void init(InfoNet.Tipo t){
        msg=(TextView)findViewById(R.id.msg);
        icon=(ImageView)findViewById(R.id.icon);
        if(tipo== InfoNet.Tipo.MSG){
            msg.setVisibility(VISIBLE);
            icon.setVisibility(INVISIBLE);
        }else if (tipo== InfoNet.Tipo.ICON){
            msg.setVisibility(INVISIBLE);
            icon.setVisibility(VISIBLE);
        }
        this.tipo=t;
    }

    public InfoNet.Tipo getTipo() {
        return tipo;
    }

    public void setTipo(InfoNet.Tipo tipo) {
        this.tipo = tipo;
    }

    public TextView getMsg() {
        return msg;
    }

    public void setMsg(TextView msg) {
        int id=this.msg.getId();
        this.msg=msg;
        this.msg.setId(R.id.msg);
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        int id=this.getId();
        this.icon = icon;
        this.icon.setId(id);
    }

    public void ocultarWidget(){
        Object var3=this.lock;
        synchronized (this.lock) {
            msg.setVisibility(INVISIBLE);
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

    public void mostrarWidget(String infonet){
        Object Object1=this.lock;
        synchronized (this.lock){
            msg.setText(infonet);
            msg.setVisibility(VISIBLE);
            TimerTask tarea=new TimerTask() {
                @Override
                public void run() {
                    Message msg=new Message();
                    msg.obj=Boolean.TRUE;
                    infoHandler.sendMessage(msg);
                }
            };
            Timer tiempo=new Timer();
            tiempo.schedule(tarea, 8000);

        }
    }
}
