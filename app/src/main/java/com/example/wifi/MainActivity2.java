 package com.example.wifi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ListView view = (ListView) findViewById(R.id.lvRedes);
        TextView txtTotal = (TextView) findViewById(R.id.tvTotal);

        Sqlite sqlite = new Sqlite(getApplicationContext(),"wifi",null,1);
        SQLiteDatabase db = sqlite.getWritableDatabase();

        ArrayList<Red> reds = new ArrayList<>();
        String[]columns = {"idnetwork","ssid","bssid"};

        Cursor c = db.query("Red",columns,null,null,null,null,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            reds.add(new
                    Red(Integer.valueOf(c.getString(0)),c.getString(1),c.getString(2)));
        }

        int total = reds.size();
        txtTotal.setText("Total de registros: "+total);
        Arrayadapter adapterRed = new Arrayadapter(this,R.layout.red,reds);
        view.setAdapter(adapterRed);

    }
}