package com.example.michael.mapsmarkers;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends Activity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener{

    private GoogleMap map;
    private int markerId;

    private int tipo;

    private float[] colors = {
            BitmapDescriptorFactory.HUE_BLUE,
            BitmapDescriptorFactory.HUE_GREEN,
            BitmapDescriptorFactory.HUE_RED,
            BitmapDescriptorFactory.HUE_ORANGE,
    };

    private int colorIndex;


    private List<Polyline> polylines = new ArrayList<>();
    private List<Polygon> polygons = new ArrayList<>();
    private List<Circle> circles = new ArrayList<>();

    private int[] colors2 = {
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.YELLOW
    };

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnMarkerDragListener(this);
        map.setOnMapLongClickListener(this);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        MarkerOptions optionsm = new MarkerOptions();
//        markerId++;
//        optionsm.position(latLng).title("Marcador " + markerId).snippet("Detalhes do marcador " + markerId)
//                //.icon(BitmapDescriptorFactory.fromResource(android.R.drawable.star_on));
//                .icon(BitmapDescriptorFactory.defaultMarker(colors[colorIndex]))
//                .draggable(true);
//
//        colorIndex = (colorIndex + 1) % colors.length;
//
//        Marker m = map.addMarker(optionsm);

        //Desenhando
        if(tipo == 0){
            // Linha poligonal
            PolylineOptions options = new PolylineOptions();
            options.add(latLng);
            options.add(new LatLng(latLng.latitude + 2, latLng.longitude));
            options.add(new LatLng(latLng.latitude, latLng.longitude + 2));
            Polyline polyline = map.addPolyline(options);
            polylines.add(polyline);
            tipo = 1;
        }else if(tipo == 1){
            // Circulos
            CircleOptions options = new CircleOptions();
            options.center(latLng);
            options.radius(100000);
            Circle circle = map.addCircle(options);
            circles.add(circle);
            tipo = 2;
        }else  if(tipo == 2) {
            // Poligono
            PolygonOptions options = new PolygonOptions();
            options.add(latLng);
            options.add(new LatLng(latLng.latitude + 2, latLng.longitude));
            options.add(new LatLng(latLng.latitude, latLng.longitude + 2));
            Polygon polygon = map.addPolygon(options);
            polygons.add(polygon);
            tipo = 0;
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        int index = random.nextInt(colors2.length);
        int width = random.nextInt(20);

        for(Polyline polyline : polylines){
            polyline.setColor(colors2[index]);
            polyline.setWidth(width);
        }

        index = random.nextInt(colors2.length);
        width = random.nextInt(20);

        for(Polygon polygon : polygons){
            polygon.setStrokeColor(colors2[index]);
            polygon.setStrokeWidth(width);
        }

        index = random.nextInt(colors2.length);
        width = random.nextInt(20);

        for(Circle circle : circles){
            circle.setStrokeColor(colors2[index]);
            circle.setStrokeWidth(width);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "Voce clicou no " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(this, marker.getTitle() + " -> " + marker.getPosition(), Toast.LENGTH_SHORT).show();
    }


}
