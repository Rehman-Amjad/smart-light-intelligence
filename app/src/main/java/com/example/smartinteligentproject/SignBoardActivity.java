package com.example.smartinteligentproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class SignBoardActivity extends AppCompatActivity {

    ImageView img_fire_back;

    EditText edCityInformation,edCityInfo2;
    Button btn_back,btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_board);


        img_fire_back = findViewById(R.id.img_fire_back);
        btn_back = findViewById(R.id.btn_back);
        edCityInformation = findViewById(R.id.edCityInformation);
        edCityInfo2 = findViewById(R.id.edCityInfo2);
        btnSave = findViewById(R.id.btnSave);


        loadValues();

        img_fire_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValue();
            }
        });

    }


    void loadValues() {
        FirebaseDatabase.getInstance().getReference("cityInformation")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        String value = task.getResult().getValue(String.class);
                        if (value != null) {
                            edCityInformation.setText(value);
                        }
                    } else {
                        edCityInformation.setHint("Enter City Information 1");
                    }
                });

        FirebaseDatabase.getInstance().getReference("cityInformation2")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        String value = task.getResult().getValue(String.class);
                        if (value != null) {
                            edCityInfo2.setText(value);
                        }
                    } else {
                        edCityInfo2.setHint("Enter City Information 2");
                    }
                });
    }


    void saveValue() {
        String cityInfo1 = edCityInformation.getText().toString();
        String cityInfo2 = edCityInfo2.getText().toString();

        if (!cityInfo1.isEmpty()) {
            FirebaseDatabase.getInstance().getReference("cityInformation")
                    .setValue(cityInfo1)
                    .addOnCompleteListener(task ->
                            Toast.makeText(this, "City info 1 saved", Toast.LENGTH_SHORT).show()
                    );
        }

        if (!cityInfo2.isEmpty()) {
            FirebaseDatabase.getInstance().getReference("cityInformation2")
                    .setValue(cityInfo2)
                    .addOnCompleteListener(task ->
                            Toast.makeText(this, "City info 2 saved", Toast.LENGTH_SHORT).show()
                    );
        }
    }



    void goBack(){
        Intent backIntent=new Intent(SignBoardActivity.this,DashboardScreen.class);
        startActivity(backIntent);
        finish();
    }
}