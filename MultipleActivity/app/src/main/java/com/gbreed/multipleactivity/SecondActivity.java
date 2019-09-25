package com.gbreed.multipleactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity
{

    public void goBack(View view)
    {
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//
//        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String name = "";
        name = name + intent.getStringExtra("name");

        Toast.makeText(this, "Name: " + name, Toast.LENGTH_SHORT).show();
    }
}
