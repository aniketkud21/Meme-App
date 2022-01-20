package com.example.memeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button shareButton, nextButton;
    ImageView memeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareButton = findViewById(R.id.shareButton);
        nextButton = findViewById(R.id.nextButton);
        memeImageView = findViewById(R.id.memeImageView);

       loadMeme();

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // By Sharing URL
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Check this out : " + currenturl);
                intent.setType("text/plain");

                Intent shareintent= Intent.createChooser(intent, null);
                startActivity(shareintent);



//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
//                shareIntent.setType("image/jpeg");
//                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));



//                View content = findViewById(R.id.memeImageView);
//                content.setDrawingCacheEnabled(true);
//
//                Bitmap bitmap = content.getDrawingCache();
//                File root = Environment.getExternalStorageDirectory();
//                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
//                try {
//                    cachePath.createNewFile();
//                    FileOutputStream ostream = new FileOutputStream(cachePath);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
//                    ostream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("image/*");
//                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
//                startActivity(Intent.createChooser(share,"Share via"));

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMeme();
            }
        });
    }

    String currenturl;
    private void loadMeme() {

        // Instantiate the RequestQueue.
        String url ="https://meme-api.herokuapp.com/gimme";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //String url = null;
                        try {
                            currenturl = response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //instead of getApplication() you can add MainActivity.this
                        Glide.with(getApplication()).load(currenturl).into(memeImageView);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        //queue.add(jsonObjectRequest);
    }
}