package com.gbreed.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    static ArrayList<String> stringNotesArray;
    static SharedPreferences sharedPreferences;
    static ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.addnote:
                stringNotesArray.add("");
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("noteText", stringNotesArray.get(stringNotesArray.size() - 1));
                intent.putExtra("position", stringNotesArray.size() - 1);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.gbreed.notes", Context.MODE_PRIVATE);
        String stringNotes = sharedPreferences.getString("notes", "");
        final Gson gson = new Gson();

        if(stringNotes.equals(""))
        {
            stringNotesArray = new ArrayList<>();
            stringNotesArray.add("Example Note");
        }
        else
        {
            Type type = new TypeToken<ArrayList<String>>(){}.getType();

            stringNotesArray = gson.fromJson(stringNotes,type);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringNotesArray);
        final ListView notesListView = findViewById(R.id.notesListView);
        notesListView.setAdapter(arrayAdapter);
        notesListView.setLongClickable(true);

        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                new AlertDialog.Builder(notesListView.getContext())
                        .setIcon(android.R.drawable.picture_frame)
                        .setTitle("Delete Note?")
                        .setMessage("Do you really want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stringNotesArray.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                sharedPreferences.edit().putString("notes", gson.toJson(stringNotesArray)).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("noteText", stringNotesArray.get(position));
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

}
