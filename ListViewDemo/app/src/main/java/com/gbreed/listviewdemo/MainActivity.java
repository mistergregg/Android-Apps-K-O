package com.gbreed.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listFriends = findViewById(R.id.listView);

        final ArrayList<String> friends = new ArrayList<>(asList("Joe", "John", "Joey", "Ross", "Marcus"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friends);

        listFriends.setAdapter(adapter);

        listFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Name: ", friends.get(position));
                Toast.makeText(MainActivity.this, "" + friends.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
