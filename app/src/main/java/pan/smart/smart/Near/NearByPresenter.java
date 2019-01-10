package pan.smart.smart.Near;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;


import java.text.DecimalFormat;
import java.util.ArrayList;

import pan.smart.smart.Login.loginInterface;
import pan.smart.smart.Utills.Constant;
import pan.smart.smart.Utills.Validation;
import pan.smart.smart.holder.latLongList;

import static android.content.Context.MODE_PRIVATE;

public class NearByPresenter implements nearByInterface,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    Context context;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Double lt;
    Double ln;
    public SharedPreferences mSharedPreferences;

    boolean gps_enabled = false;
    boolean network_enabled = false;

    public NearByPresenter(Context context)

    {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences("tokenDetail", MODE_PRIVATE);



    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }

    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;



        lt = location.getLatitude();
        ln = location.getLongitude();

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constant.lat, String.valueOf(lt));
        editor.apply();
        SharedPreferences.Editor editor2 = mSharedPreferences.edit();
        editor2.putString(Constant.lng, String.valueOf(ln));
        editor2.apply();
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    @Override
    public void loadMarkers(ArrayList<latLongList> list,ArrayList<String> title,ArrayList<String> distances) {
        list.add(new latLongList(30.062010,31.344999));
        list.add(new latLongList(30.064301,31.377069));
        list.add(new latLongList(30.118771,31.341780));
        title.add("title : Makram ebid");
        title.add("title : Rabaa Eladwya");
        title.add("El mokawllon al arab");
        Double lat;
        Double lang;

        if(mSharedPreferences.getString(Constant.lat,"").equals(""))
        {
            lat = 30.0632303;
            lang = 31.3548877;
        }
        else
            {
             lat = Double.valueOf(mSharedPreferences.getString(Constant.lat, ""));
             lang = Double.valueOf(mSharedPreferences.getString(Constant.lng, ""));

            }
        LatLng currentPosition = new LatLng(lat, lang);
        for (int i = 0 ; i < list.size(); i++)
        {
            LatLng targetPosition = new LatLng(list.get(i).lat, list.get(i).lng);
            double d = CalculationByDistance(currentPosition,targetPosition);
            distances.add(String.valueOf("Distance:"+d+"km"));
        }




    }
}
