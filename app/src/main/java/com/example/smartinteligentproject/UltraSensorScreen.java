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

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import pl.droidsonroids.gif.GifImageView;

public class UltraSensorScreen extends AppCompatActivity {

    ImageView img_ultra_back,img_fire,tower1Fire_gif,tower2Fire_gif;

    MediaPlayer player;

    FirebaseDatabase database;
    DatabaseReference myRef;
    String fireValue,fire2Value,img;

    TextView tv_fire,tv_fire2;
    ImageView gif;

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultra_sensor_screen);

        img_ultra_back=findViewById(R.id.img_ultra_back);
        img_fire=findViewById(R.id.img_fire);
        tv_fire=findViewById(R.id.tv_fire);
        btn_back=findViewById(R.id.btn_back);
        gif=findViewById(R.id.gif);
        tower1Fire_gif=findViewById(R.id.tower1Fire_gif);
        tower2Fire_gif=findViewById(R.id.tower2Fire_gif);
        tv_fire2=findViewById(R.id.tv_fire2);


        img_ultra_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(UltraSensorScreen.this,DashboardScreen.class);
                startActivity(backIntent);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent=new Intent(UltraSensorScreen.this,DashboardScreen.class);
                startActivity(backIntent);
                finish();
            }
        });


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SmartData");
        DatabaseReference callref=myRef.child("200");


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                fireValue=snapshot.child("T1UltrasonicSensor").getValue(String.class);
                fire2Value=snapshot.child("T2UltrasonicSensor").getValue(String.class);
                img=snapshot.child("img").getValue(String.class);


                if (fireValue.equals("1"))
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(img, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    img_fire.setImageBitmap(decodedImage);

                    tv_fire.setText("Light On Tower#1");
                    tower1Fire_gif.setVisibility(View.VISIBLE);
                    gif.setVisibility(View.VISIBLE);
                    Glide.with(UltraSensorScreen.this)
                            .load(R.drawable.light_gif)
                            .into(gif);
                }
                else
                {
                    tv_fire.setText("Light Off Tower#1");
                    img_fire.setImageResource(R.drawable.okimage);
                    tower1Fire_gif.setVisibility(View.GONE);
                }

                if (fire2Value.equals("1"))
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(img, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    img_fire.setImageBitmap(decodedImage);

                    tv_fire2.setText("Light On Tower#2");
                    tower2Fire_gif.setVisibility(View.VISIBLE);
                    gif.setVisibility(View.VISIBLE);
                    Glide.with(UltraSensorScreen.this)
                            .load(R.drawable.light_gif)
                            .into(gif);


                }
                else
                {
                    tv_fire2.setText("Light Off Tower#2");
                    tower2Fire_gif.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                fireValue=snapshot.child("T1UltrasonicSensor").getValue(String.class);
                fire2Value=snapshot.child("T2UltrasonicSensor").getValue(String.class);
                img=snapshot.child("img").getValue(String.class);


                if (fireValue.equals("1"))
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(img, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    img_fire.setImageBitmap(decodedImage);

                    tv_fire.setText("Light On Tower#1");
                    tower1Fire_gif.setVisibility(View.VISIBLE);

                    gif.setVisibility(View.VISIBLE);

                    Glide.with(UltraSensorScreen.this)
                            .load(R.drawable.light_gif)
                            .into(gif);

                }
                else
                {
                    tv_fire.setText("Light Off Tower#1");
                    img_fire.setImageResource(R.drawable.okimage);
                    tower1Fire_gif.setVisibility(View.GONE);
                }

                if (fire2Value.equals("1"))
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(img, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    img_fire.setImageBitmap(decodedImage);

                    tv_fire2.setText("Light On Tower#2");
                    tower2Fire_gif.setVisibility(View.VISIBLE);

                    gif.setVisibility(View.VISIBLE);
                    Glide.with(UltraSensorScreen.this)
                            .load(R.drawable.light_gif)
                            .into(gif);



                }
                else
                {
                    tv_fire2.setText("Light Off Tower#2");
                    tower2Fire_gif.setVisibility(View.GONE);
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
}