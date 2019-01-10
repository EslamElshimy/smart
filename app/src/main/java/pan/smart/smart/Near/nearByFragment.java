package pan.smart.smart.Near;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import pan.smart.smart.R;
import pan.smart.smart.Utills.Validation;
import pan.smart.smart.holder.MarkerTag;
import pan.smart.smart.holder.latLongList;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.LOCATION_SERVICE;
import static pan.smart.smart.Utills.Constant.buildDialog;


public class nearByFragment extends Fragment implements nearByInterface, OnMapReadyCallback, GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener,GoogleMap.OnMarkerDragListener
        {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    GoogleApiClient mGoogleApiClient;
    AppCompatTextView title_txt;
    AppCompatTextView distance_txt;
    LinearLayoutCompat card;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private ArrayList<latLongList> latLong_List = new ArrayList<>();
    private     ArrayList<String> title = new ArrayList<>();
    private     ArrayList<String> distance = new ArrayList<>();
    double radiusInMeters = 100.0;
    int strokeColor = 0xffff0000; //Color Code you want
    int shadeColor = 0x44ff0000; //opaque red fill

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String lat;
    String lng;

    NearByPresenter nearByPresenter;



            @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the lyout for this fragment
        View view = inflater.inflate(R.layout.near_by, container, false);

        mapFrag =  (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        title_txt                    = view.findViewById(R.id.title);
        distance_txt                 = view.findViewById(R.id.distance);
        card                         = view.findViewById(R.id.card);
        nearByPresenter              = new NearByPresenter(getActivity());
        mapFrag.getMapAsync(this);

//Full screen is set for the Window
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



       return view;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        //mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mGoogleMap.setOnMapClickListener((GoogleMap.OnMapClickListener) this);
        mGoogleMap.setOnMarkerDragListener(this);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                nearByPresenter.buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            nearByPresenter.buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
        loadMarker();


    }



    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),  android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


                    new AlertDialog.Builder(getActivity())
                            .setTitle("Location Permission Needed")
                            .setMessage("This app needs the Location permission, please accept to use location functionality")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Prompt the user once explanation has been shown
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_LOCATION );
                                }
                            })
                            .create()
                            .show();




            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            nearByPresenter.buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();


                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
    public void loadMarker() {

        mGoogleMap.clear();
        // For dropping a marker at a point on the Map
        nearByPresenter.loadMarkers(latLong_List,title,distance);
        for (int i = 0; i < latLong_List.size(); i++) {
            MarkerTag markerTag = new MarkerTag();


            try {
                LatLng sydney = new LatLng(latLong_List.get(i).lat, latLong_List.get(i).lng);


                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(sydney).title(title.get(i)).snippet(""));

                //  String name =  getMerchantAddressByCatgeoyrModel.getAddress()[i].getNameenglish();
                markerTag.setName(title.get(i));
                markerTag.setDistance(distance.get(i));

                marker.setTag(markerTag);


                mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {

                        MarkerTag yourMarkerTag = ((MarkerTag) marker.getTag());

                        title_txt.setText(marker.getTitle());
                        distance_txt.setText(yourMarkerTag.getDistance());

                        title_txt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Double lat    = marker.getPosition().latitude;
                                Double lang   = marker.getPosition().longitude;

                                // Toast.makeText(getContext(),String.valueOf(lat),Toast.LENGTH_LONG).show();
                                //  Toast.makeText(getContext(),String.valueOf(lang),Toast.LENGTH_LONG).show();
                                Uri.Builder directionsBuilder = new Uri.Builder()
                                        .scheme("https")
                                        .authority("www.google.com")
                                        .appendPath("maps")
                                        .appendPath("dir")
                                        .appendPath("")
                                        .appendQueryParameter("api", "1")
                                        .appendQueryParameter("destination", lat + "," + lang);

                                startActivity(new Intent(Intent.ACTION_VIEW, directionsBuilder.build()));


                            }
                        });

                        card.setVisibility(View.VISIBLE);


                        return false;
                    }
                });
            } catch (Exception e) {
            }

        }


    }



            public void gps() {


                LocationManager locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
//        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                Location net_loc = null, gps_loc = null, finalLoc = null;

                Toast.makeText(getActivity(),String.valueOf(gps_enabled),Toast.LENGTH_LONG).show();

                if (gps_enabled)
                {
                    gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    finalLoc = gps_loc;

                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder
                            .setMessage("GPS is disabled in your device. Enable it?")
                            .setCancelable(false)
                            .setPositiveButton("Enable GPS",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            Intent callGPSSettingIntent = new Intent(
                                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            nearByFragment.this.startActivity(callGPSSettingIntent);
                                        }
                                    });
                    alertDialogBuilder.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();

                }


            }

            @Override
            public void loadMarkers(ArrayList<latLongList> list, ArrayList<String> title, ArrayList<String> distances) {

            }


            @Override
            public void setUserVisibleHint(boolean isVisibleToUser) {
                super.setUserVisibleHint(isVisibleToUser);
                if (isVisibleToUser) {
                    // Load your data here or do network operations here
                   // gps();

                }
            }


}