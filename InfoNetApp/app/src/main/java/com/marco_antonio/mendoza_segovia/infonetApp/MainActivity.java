package com.marco_antonio.mendoza_segovia.infonetApp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.marco_antonio.mendoza_segovia.infonet.InfoNet;
import com.marco_antonio.mendoza_segovia.infonet.InfoNetView;

public class MainActivity extends AppCompatActivity{

    Boolean isChecked=false;
    InfoNet info;
    InfoNetView red;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //red= (InfoNetView) findViewById(R.id.prueba);
        //red.setTipo(InfoNet.Tipo.ICON);
        TextView tex=(TextView) findViewById(R.id.prueba);
        info=new InfoNet(tex, Color.GREEN,Color.RED,"Conectado","No hay conexion a internet");
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isChecked) {
                    isChecked = false;
                } else {
                    isChecked = true;
                }
                verificarConec(view);
                /*if (!verifConex.verificarConexion(getParent())) {
                    Snackbar.make(view, "No hay conexion..", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Conectado...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        info.iniciar(this);
        // Filtro de acciones que serán alertadas
        /*IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Constantes.ACTION_RUN_SERVICE);
        filter.addAction(Constantes.ACTION_EXIT_SERVICE);

        // Crear un nuevo ResponseReceiver
        receiver = new InfoNetReceiver(red);
        // Registrar el receiver y su filtro
        registerReceiver(receiver, filter);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        info.detener(this);
        /*
        unregisterReceiver(receiver);
        if(intentInfoNetService!=null)
            stopService(intentInfoNetService);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /*
    public void verificarConec(View view){

        intentInfoNetService = new Intent(
                getApplicationContext(), InfoNetService.class);
        if (isChecked) {
            startService(intentInfoNetService); //Iniciar servicio
            Snackbar.make(view, "Se inicio el Servicio..", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            stopService(intentInfoNetService); // Detener servicio
            Snackbar.make(view, "Servicio Eliminado...", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }
    */
}
