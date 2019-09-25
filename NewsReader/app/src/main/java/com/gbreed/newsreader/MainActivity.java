package com.gbreed.newsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<String> topStories;
    private ArrayList<String> topStoriesLinks;
    private ArrayAdapter<String> arrayAdapter;
    private ListView linksListView;
    private int i = 0;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topStories = new ArrayList<>();
        topStoriesLinks = new ArrayList<>();

        linksListView = findViewById(R.id.linksListView);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topStories);
        linksListView.setAdapter(arrayAdapter);

        database = this.openOrCreateDatabase("links", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS links (name VARCHAR, link VARCHAR, idNum INT(8), id INTEGER PRIMARY KEY)");

        DownloadTask task = new DownloadTask();

        try {
            String result = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json").get();

            JSONArray topStoriesIds = new JSONArray(result);

            Cursor c = database.rawQuery("SELECT * FROM links WHERE idNum = '" + topStoriesIds.getInt(0) + "' LIMIT 1", null);

            if (c.moveToFirst())
            {
                c = database.rawQuery("SELECT * FROM links ORDER BY id LIMIT 20", null);

                int nameIndex = c.getColumnIndex("name");
                int linkIndex = c.getColumnIndex("link");

                c.moveToFirst();
                while(!c.isAfterLast())
                {
                    topStories.add(c.getString(nameIndex));
                    topStoriesLinks.add(c.getString(linkIndex));
                    c.moveToNext();
                }
            } else {
                for (i = 0; i < 15; i++) {
                    try {
                        DownloadTask task2 = new DownloadTask();
                        String tmpJson = task2.execute("https://hacker-news.firebaseio.com/v0/item/" + Integer.toString(topStoriesIds.getInt(i)) + ".json").get();

                        JSONObject jsonObject = new JSONObject(tmpJson);
                        String title = jsonObject.getString("title");

                        JSONObject jsonObject2 = new JSONObject(tmpJson);
                        String url = jsonObject2.getString("url");

                        topStories.add(title);
                        Log.i("title", title);

                        topStoriesLinks.add(url);
                        Log.i("url", url);

                        database.execSQL("INSERT INTO links (name, link, idNum) VALUES('" + title + "', '" + url + "', " + topStoriesIds.getInt(i) + ")");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        arrayAdapter.notifyDataSetChanged();

        linksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), webViewActivity.class);
                intent.putExtra("url", topStoriesLinks.get(position));
                startActivity(intent);
            }
        });
    }

    class DownloadTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            StringBuilder result = new StringBuilder();
            URL url;

            try{
                url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1)
                {
                    char current = (char) data;
                    result.append(current);
                    data = reader.read();
                }

                return result.toString();
            }catch(Exception e)
            {
                e.printStackTrace();
                return "Failed";
            }
        }
    }
}
