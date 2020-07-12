package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getLoc = (Button) findViewById(R.id.getLocation);
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},122);
                GPStacker g = new GPStacker(getApplicationContext());
                Location l=g.getLocation();
                if(l!=null){
                    double lat=l.getLatitude();
                    double lon = l.getLongitude();
                    String str="geo:"+lat+","+lon;
                    Toast.makeText(getApplicationContext(),"开启GPS",Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(),"lat:"+lat+"\nlon:"+lon,
//                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"l null",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}

class GPStacker implements LocationListener {
    Context context;
    public GPStacker(Context c){
        context=c;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    public Location getLocation(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context,"GPS未开启", Toast.LENGTH_SHORT).show();
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnable = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnable){
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,6000,10,this);
            Location l=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            double lat=l.getLatitude();
//            double lon = l.getLongitude();
//            String str="geo:"+lat+","+lon;
            Toast.makeText(context,"GPS",Toast.LENGTH_LONG).show();
            return l;
        }else {
            Toast.makeText(context,"开启GPS",Toast.LENGTH_LONG).show();
        }
        return null;

    }
}