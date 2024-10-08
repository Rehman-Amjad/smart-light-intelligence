package com.example.smartinteligentproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import pl.droidsonroids.gif.GifImageView;

public class RainSensorScreen extends AppCompatActivity {

    MediaPlayer player;

    GifImageView gif;

    ImageView img_fire_back,img_fire;

    FirebaseDatabase database;
    DatabaseReference myRef;
    String fireValue,img;

    TextView tv_fire;

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain_sensor_screen);

        img_fire_back=findViewById(R.id.img_fire_back);
        img_fire=findViewById(R.id.img_fire);
        tv_fire=findViewById(R.id.tv_fire);
        btn_back=findViewById(R.id.btn_back);
        gif=findViewById(R.id.gif);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SmartData");
        DatabaseReference callref=myRef.child("200");

        img_fire_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(RainSensorScreen.this,DashboardScreen.class);
                startActivity(backIntent);
                stopPlayer();
                finish();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(RainSensorScreen.this,DashboardScreen.class);
                startActivity(backIntent);
                stopPlayer();
                finish();
            }
        });


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                fireValue=snapshot.child("Rain").getValue(String.class);
                img=snapshot.child("img").getValue(String.class);




                if (fireValue.equals("1"))
                {


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(img, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    img_fire.setImageBitmap(decodedImage);

                    tv_fire.setText("Rain ALert");
                    gif.setVisibility(View.VISIBLE);

                    play();

                }
                else
                {
                    tv_fire.setText("Everything is Ok");
                    gif.setVisibility(View.INVISIBLE);
                    img_fire.setImageResource(R.drawable.okimage);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                fireValue=snapshot.child("Rain").getValue(String.class);




                if (fireValue.equals("1"))
                {


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(img, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    img_fire.setImageBitmap(decodedImage);
                    tv_fire.setText("Rain ALert");
                    gif.setVisibility(View.VISIBLE);

                    play();

                }
                else
                {
                    tv_fire.setText("Everything is Ok");
                    gif.setVisibility(View.INVISIBLE);
                    img_fire.setImageResource(R.drawable.okimage);
                    stopPlayer();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void play()
    {
        if (player == null)
        {
            player = MediaPlayer.create(RainSensorScreen.this, R.raw.rain);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        player.start();
    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    public void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPlayer();
    }
}