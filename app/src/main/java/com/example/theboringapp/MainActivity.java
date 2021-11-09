package com.example.theboringapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        meme();

    }
    public String share;

    public void meme(){
        // ...
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.unsplash.com/photos/?client_id=Gq9Gnlgyi5hDIqdQLR_7yOD8sqZRBS2S5kAbrhOCBnw";

        // Request a string response from the provided URL.
        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            String url = response.getString("thumb");
                            String url = "https://images.unsplash.com/photo-1599420186946-7b6fb4e297f0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwyNjIzMzh8MXwxfGFsbHwxfHx8fHx8Mnx8MTYzMjI4ODcwMw&ixlib=rb-1.2.1&q=80&w=400";
                            String info = response.getString("alt_description");
                            share = response.getString("small");
                            textView.setText(info);
                            Glide.with(MainActivity.this).load(url).into(imageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        queue.add(JsonObjectRequest);

    }

    public void next(View view) {
        meme();
//        Toast.makeText(MainActivity.this, "Next Meme is Coming", Toast.LENGTH_SHORT).show();
    }

    public void share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, share);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

}