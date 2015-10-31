
/**
 * Created by marco on 14-10-15.
 * Class: InfoNet
 * version: 0.0.1
 * Copyright: Ing. Mendoza Segovia Marco Antonio
 * email: mendozamarco87@gmail.com - mendozmarco87@hotmail.com
 */
package com.marco_antonio.mendoza_segovia.infonet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * La clase VerifNet es la encargada de la verificacion de la
 * conexion a alguna red, asi tambien informa sobre la intensidad
 * de la senial de internet.
 */
public class VerifNet {

    /**
     * El Metodo verificarConexion se encarga de informar si el telefono esta o no
     * conectado a alguna red, ya sea esta: WiFi, Datos, Vpn....
     * @param ctx: variable de tipo Context. indica desde que lugar se obteniendo el servicio
     * @return boolean: False = No hay conexion; True = Si existe una conexion
     */
    public static boolean verificarConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {   /* recorre todas la conexiones posibles que devuelve getAllNetwork() */
            NetworkInfo.State estadoRed= redes[i].getState();
            if ( estadoRed == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }
}
