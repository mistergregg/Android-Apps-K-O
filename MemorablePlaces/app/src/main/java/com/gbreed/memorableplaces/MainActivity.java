package com.gbreed.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView placesListView;
    static ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> places;
    static ArrayList<LatLng> placesLocation;
    static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets up Lists
        places = new ArrayList<>();
        placesLocation = new ArrayList<>();

        //Loads files if there is any
        Gson gson = new Gson();
        sharedPreferences = this.getSharedPreferences("com.gbreed.memorableplaces", Context.MODE_PRIVATE);

        Type stringType = new TypeToken<ArrayList<String>>(){}.getType();
        Type latType = new TypeToken<ArrayList<LatLng>>(){}.getType();

        places = gson.fromJson(sharedPreferences.getString("places", gson.toJson(new ArrayList<String>())), stringType);
        placesLocation = gson.fromJson(sharedPreferences.getString("placesLocation", gson.toJson(new ArrayList<LatLng>())), latType);

        if(places.isEmpty())
            places.add("Add a new place...");

        if(placesLocation.isEmpty())
            placesLocation.add(new LatLng(0,0));

        //Sets up adapters
        placesListView = findViewById(R.id.placesListView);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);


        placesListView.setAdapter(arrayAdapter);

        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Gson gson = new Gson();
                String jsonPlaces = gson.toJson(placesLocation);
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("index", position);
                intent.putStringArrayListExtra("placeList", places);
                intent.putExtra("listAsString", jsonPlaces);
                startActivity(intent);
            }
        });
    }
}