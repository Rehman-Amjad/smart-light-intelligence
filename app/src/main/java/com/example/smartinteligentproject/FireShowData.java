package com.example.smartinteligentproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartinteligentproject.Model.User;
import com.example.smartinteligentproject.Model.UserAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FireShowData extends AppCompatActivity {


    private RecyclerView recyclerView;
    private UserAdapter mUserAdapter;
    private List<User> mDatalist;

    Button btnDeleteAll;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private DatabaseReference historyRef;

    private ChildEventListener MyChildEventListener;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRef.removeEventListener(MyChildEventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_show_data);


        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Smartlight");
        historyRef = FirebaseDatabase.getInstance().getReference("Smartlight");


        mDatalist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserAdapter=new UserAdapter(this,mDatalist);
        recyclerView.setAdapter(mUserAdapter);


        MyChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user=snapshot.getValue(User.class);
            /*Log.d(TAG,"User Name :" + user.getUserName());
            Log.d(TAG,"User Name :" + user.getUserPassword());*/
                mDatalist.add(user);
                mUserAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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

        };
        myRef.addChildEventListener(MyChildEventListener);


        btnDeleteAll.setOnClickListener(v -> {
            deleteAllHistoryData();
        });

    }

    private void deleteAllHistoryData() {
        // Show a confirmation toast before deletion
        Toast.makeText(FireShowData.this, "Deleting all history...", Toast.LENGTH_SHORT).show();

        // Delete all data under "History" in Firebase
        historyRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mDatalist.clear();
                mUserAdapter.notifyDataSetChanged();
                Toast.makeText(FireShowData.this, "All history deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FireShowData.this, "Failed to delete history", Toast.LENGTH_SHORT).show();
            }
        });
    }
}