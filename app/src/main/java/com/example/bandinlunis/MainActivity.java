package com.example.bandinlunis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class MainActivity<setPosition> extends AppCompatActivity implements OnMapReadyCallback {

    NaverMap myMap;
    LinearLayout linemenu;
    boolean menulist = false;
    ArrayList<Marker> listMarker = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map_fragment);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        linemenu = findViewById(R.id.menu);
        linemenu.setVisibility(View.INVISIBLE);
/*
// onClick을 만들지 않고 코드에서 만드는 방법
        Button btnMd = findViewById(R.id.MD);
        btnMd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MD();
            }
        });

*/

    }

    public void BasicB(View v) {
        myMap.setMapType(NaverMap.MapType.Basic);
    }

    public void NaviB(View v) {
        myMap.setMapType(NaverMap.MapType.Navi);
    }

    public void SatelliteB(View v) {
        myMap.setMapType(NaverMap.MapType.Satellite);
    }

    public void HybridB(View v) {
        myMap.setMapType(NaverMap.MapType.Hybrid);
    }

    public void TerrainB(View v) {
        myMap.setMapType(NaverMap.MapType.Terrain);
    }

    public void NoneB(View v) {
        myMap.setMapType(NaverMap.MapType.None);
    }

    public void ModeB(View v) {

        if (menulist) {
            menulist = !menulist;
            linemenu.setVisibility(View.INVISIBLE);
        } else {
            menulist = !menulist;
            linemenu.setVisibility(View.VISIBLE);
        }

    }
    public void MD(View v) {
        for (Marker marker : listMarker) {
            marker.setMap(null);
        }
    }

    public void test(LatLng coord) {
        Toast.makeText(this, coord.latitude + ", " + coord.longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        myMap = naverMap;


        myMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF point, @NonNull LatLng coord) {
                test(coord);
                Marker marker = new Marker();
                listMarker.add(marker);


                marker.setPosition(coord);
                marker.setMap(myMap);


            }
        });
/*
        myMap.setOnMapClickListener((point, coord) ->
                Toast.makeText(this, coord.latitude + ", " + coord.longitude, Toast.LENGTH_SHORT).show());
*/
    }


}