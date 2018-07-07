package com.example.emilychou.qflare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by emilychou on 7/7/18.
 */

public class PersonInfo extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView personName;
    private TextView roomName;
    private TextView positionName;
    private TextView phoneName;
    private TextView currentRoom;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        personName = (TextView) findViewById(R.id.personName);
        roomName = (TextView) findViewById(R.id.room);
        phoneName = (TextView) findViewById(R.id.phone);
        positionName = (TextView) findViewById(R.id.position);
        currentRoom = (TextView) findViewById(R.id.currRoom);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Bundle b = getIntent().getExtras();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String room = ds.child("room").getValue(String.class);
                    String phone = ds.child("phoneNumber").getValue(String.class);
                    String position = ds.child("position").getValue(String.class);

                    if(name.equals(b.getString("passname"))) {
                        personName.setText(name);
                        roomName.setText(room);
                        phoneName.setText(phone);
                        positionName.setText(position);
                    }
                    else if(name.equals(b.getString("passname") + "1")){
                        currentRoom.setText(room);
                        personName.setText(name);
                        roomName.setText(room);
                        phoneName.setText(phone);
                        positionName.setText(position);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabase.child("users").addListenerForSingleValueEvent(eventListener);
    }
}
