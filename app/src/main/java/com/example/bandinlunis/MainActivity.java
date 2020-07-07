package com.example.bandinlunis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.pm.ActivityInfo;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PolygonOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.*;

public class MainActivity<setPosition> extends AppCompatActivity implements OnMapReadyCallback {

    NaverMap myMap;
    LinearLayout linemenu;
    boolean menulist = false;
    ArrayList<Marker> listMarker = new ArrayList<>();
    int markerNumber;
    List<LatLng> coords = new ArrayList<>();
    PolygonOverlay polygon = new PolygonOverlay();


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
        polygon.setMap(null);
        coords.clear();
        markerNumber = 0;
    }
    public void TT(View v) {
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
    }

    public void text(LatLng coord) {
        Toast.makeText(this, coord.latitude + ", " + coord.longitude, Toast.LENGTH_SHORT).show();
        markerNumber+=1;
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        myMap = naverMap;
        Marker marker = new Marker();
        listMarker.add(marker);
        marker.setPosition(new LatLng(35.945282, 126.682303));
        marker.setMap(myMap);


        myMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF point, @NonNull LatLng coord) {
                text(coord);
                Marker marker = new Marker();
                listMarker.add(marker);

                InfoWindow infoWindow = new InfoWindow();
                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(MainActivity.this) {
                    @NonNull
                    @Override
                    public CharSequence getText(@NonNull InfoWindow infoWindow) {
                        return "좌표:" + coord.latitude + "," + coord.longitude;
                    }
                });
                coords.add(coord);
                marker.setPosition(coord);
                marker.setMap(myMap);
                infoWindow.open(marker);

                if (markerNumber >= 3) {
                    polygon.setCoords(coords);
                    polygon.setMap(myMap);
                    polygon.setColor(Color.BLUE);
                };
            }
        });


/*
        myMap.setOnMapClickListener((point, coord) ->
                Toast.makeText(this, coord.latitude + ", " + coord.longitude, Toast.LENGTH_SHORT).show());
*/
    }


}
