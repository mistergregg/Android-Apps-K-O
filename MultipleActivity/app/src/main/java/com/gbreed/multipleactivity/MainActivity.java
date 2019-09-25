package com.gbreed.multipleactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ListView mylistView;

    public void goToNext(View view)
    {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

        intent.putExtra("age", 27);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mylistView = findViewById(R.id.myListView);

        final ArrayList<String> friends = new ArrayList<>();

        friends.add("Joe V");
        friends.add("Joe Fox");
        friends.add("Yasmina");
        friends.add("Marcus");
        friends.add("Ross");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friends);

        mylistView.setAdapter(arrayAdapter);

        mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("name", friends.get(position));
                startActivity(intent);
            }
        });
    }
}
