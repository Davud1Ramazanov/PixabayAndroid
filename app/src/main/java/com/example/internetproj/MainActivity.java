package com.example.internetproj;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        adapter = new ImageAdapter(this, arrayList);
        listView.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL imagesApi = new URL("https://pixabay.com/api/?key=39248706-606331503991e08b85fd43010&q=yellow+flowers&image_type=photo&pretty=true");
                    HttpsURLConnection connectionUrl = (HttpsURLConnection) imagesApi.openConnection();
                    connectionUrl.setRequestMethod("GET");

                    InputStream responseBody = connectionUrl.getInputStream();
                    BufferedReader inputStream = new BufferedReader(new InputStreamReader(responseBody));
                    StringBuilder result = new StringBuilder();
                    String line;

                    try {
                        while ((line = inputStream.readLine()) != null) {
                            result.append(line);
                        }

                        JSONObject jsonResponse = new JSONObject(result.toString());
                        JSONArray jsonArray = jsonResponse.getJSONArray("hits");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String largeImageURL = jsonObject.getString("largeImageURL");
                            arrayList.add(largeImageURL);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    ImageView imageView = findViewById(R.id.imageView);
                                    Picasso.get().load(largeImageURL).into(imageView);
                                }
                            });
                        }

                    } finally {
                        inputStream.close();
                    }

                    connectionUrl.disconnect();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
