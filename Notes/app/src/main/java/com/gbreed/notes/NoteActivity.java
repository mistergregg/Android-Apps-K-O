package com.gbreed.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.Gson;

public class NoteActivity extends AppCompatActivity
{
    EditText noteEditText;
    int editPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteEditText = findViewById(R.id.editNoteText);

        Intent intent = getIntent();

        noteEditText.setText(intent.getStringExtra("noteText"));
        editPosition = intent.getIntExtra("position", 0);
    }

    @Override
    public void onBackPressed()
    {
        Gson gson = new Gson();
        MainActivity.stringNotesArray.set(editPosition, noteEditText.getText().toString());
        MainActivity.arrayAdapter.notifyDataSetChanged();
        MainActivity.sharedPreferences.edit().putString("notes", gson.toJson(MainActivity.stringNotesArray)).apply();
        super.onBackPressed();
    }
}
