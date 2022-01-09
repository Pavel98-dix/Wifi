package com.example.wifi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    WifiManager wifi;
    Button on,off, buscar,bd,guardar;
    TextView status,redes;

    boolean tarea= false;
    int contador=0;
    int encendidoApagado=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        widgets();
        wifi=(WifiManager) getSystemService(Context.WIFI_SERVICE);
        verificarStatus();

    }



    private void widgets()
    {
        on=(Button)findViewById(R.id.btnPrender);
        on.setOnClickListener(this);
        off=(Button)findViewById(R.id.btnApagar);
        off.setOnClickListener(this);
        buscar=(Button)findViewById(R.id.btnBuscar);
        buscar.setOnClickListener(this);
        bd=(Button)findViewById(R.id.btnMostrar);
        bd.setOnClickListener(this);
        guardar=(Button)findViewById(R.id.btnGuardar);
        guardar.setOnClickListener(this);
        status=(TextView)findViewById(R.id.tvStatus);
        redes=(TextView)findViewById(R.id.tvRedes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPrender:
                    wifion();
                break;
            case R.id.btnApagar:
                wifioff();
                break;
            case R.id.btnBuscar:
                if (wifi.isWifiEnabled()){
                    if (tarea == false){
                        tarea = true;
                        msg("Iniciando busqueda");
                        new MyAsyncTask().execute();
                    }else {
                        tarea = false;
                        msg("Busqueda detenida");
                    }
                }else {
                    msg("Debe encender el Wifi");
                }
                break;
            case R.id.btnMostrar:
                Intent intentmain = new Intent(this,MainActivity2.class);
                startActivity(intentmain);
                break;
            case R.id.btnGuardar:
                if (wifi.isWifiEnabled()){
                    ArrayList<Red> reds = buscar();
                    guardarRedes(reds);

                }else {
                    msg("Debes encender el Wifi");
                }
                break;
        }
    }
    public void guardarRedes(ArrayList<Red> reds){
        Sqlite sqlite = new Sqlite(getApplicationContext(),"wifi",null,1);
        SQLiteDatabase db = sqlite.getWritableDatabase();
        for (Red red: reds){
            ContentValues values = new ContentValues();
            values.put("idnetwork",String.valueOf(red.getIdNetworkId()));
            values.put("ssid",red.getSSID());
            values.put("bssid",red.getBSSID());
            try {
                db.insert("Red",null,values);
            }catch (Exception e){
                msg("Error: "+e.getMessage());
            }
        }
        msg("Redes guardadas");
    }

    private ArrayList<Red> buscar()
    {
        ArrayList<Red> reds = new ArrayList<>();
        if(wifi.isWifiEnabled()){
            @SuppressLint
                    ("MissingPermission") List<WifiConfiguration> lista = wifi.getConfiguredNetworks();
            for (WifiConfiguration w: lista){
//redes.append(w.SSID+" "+w.BSSID+" "+w.networkId+"\n");
                reds.add(new Red(w.networkId,w.SSID,w.BSSID));
            }
        }else{
            msg("Debes enceder en Wifi");
        }
        return reds;
    }



    private void wifioff()
    {
        if (wifi.isWifiEnabled()){
            msg("Apagando Wifi");
            wifi.setWifiEnabled(false);
            status.setText("Apagado");
            encendidoApagado = 2;
        }else {
            msg("Debes encender el wifi");
        }
    }

    private void wifion() {
        if (!wifi.isWifiEnabled()){
            msg("Encendiendo Wifi");
            wifi.setWifiEnabled(true);
            status.setText("Encendido");
            encendidoApagado = 1;
        }else {
            msg("Debes apagar el Wifi");
        }
    }

    private void verificarStatus()
    {
        SharedPreferences preferences = getSharedPreferences("wifiStatus",
                Context.MODE_PRIVATE);

        Integer wifistatus = preferences.getInt("status",0);
        if (wifistatus == 1){//Encendido
            if (!wifi.isWifiEnabled()){
                msg("Encendiendo Wifi");
                wifi.setWifiEnabled(true);
                status.setText("Encendido");
                encendidoApagado = 1;
            }else{
                status.setText("Encendido");
                encendidoApagado = 1;
            }}else if (wifistatus == 2){//Apagado
            if (wifi.isWifiEnabled()){
                msg("Apagando Wifi");
                wifi.setWifiEnabled(false);
                status.setText("Apagado");
                encendidoApagado = 2;
            }else {
                status.setText("Apagado");
                encendidoApagado = 2;
            }
        }

    }


    public void msg(String mensaje)
    {
        Toast.makeText(getApplicationContext()," "+mensaje,Toast.LENGTH_SHORT).show();
    }


    private class MyAsyncTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            while (tarea) {
                @SuppressLint("MissingPermission") List<WifiConfiguration> DataNetwork =
                        wifi.getConfiguredNetworks();
                int net = 0;
                while (tarea && net < DataNetwork.size()) {
                    WifiConfiguration w = DataNetwork.get(net);
                    String red = "Network: "+w.networkId + " SSID: " + w.SSID + " BSSIS: " + w.BSSID + "\n";
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(red);
                    net++;
                    contador++;
                }
                contador = 0;

            }
            return "Red";
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (contador == 0){
                redes.setText("");
            }
            String red = values[0];
            redes.append(red);
        }
    }


}
