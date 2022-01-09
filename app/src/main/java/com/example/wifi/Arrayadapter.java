package com.example.wifi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Arrayadapter extends ArrayAdapter<Red> {
    Context context;
    int layoutResource;
    ArrayList<Red> lista= null;

    public Arrayadapter(Context context, int layoutResource, ArrayList<Red> lista)
    {
        super(context,layoutResource,lista);
        this.context=context;
        this.layoutResource=layoutResource;
        this.lista=lista;
    }

    public View getView(int posicion, View convertView, ViewGroup
            parent){
        View row = convertView;
        HolderRed holder = null;
        if (row == null){
            LayoutInflater inflater =
                    ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResource,parent,false);
            holder = new HolderRed();
//holder.idnetwork = (TextView)
            row.findViewById(R.id.textView3);
            holder.ssid = (TextView) row.findViewById(R.id.textView4);
            holder.bssid = (TextView) row.findViewById(R.id.textView5);
            row.setTag(holder);
        }else{
            holder = (HolderRed) row.getTag();
        }
        Red red = lista.get(posicion);
//holder.idnetwork.setText(String.valueOf(red.getIdNetworkId()));
        holder.ssid.setText(red.getSSID());
        holder.bssid.setText(red.getBSSID());
        return row;
    }
    static class HolderRed{
        //TextView idnetwork;
        TextView ssid;
        TextView bssid;
    }


}
