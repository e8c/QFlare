package com.example.emilychou.qflare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by emilychou on 7/7/18.
 */

public class HomePage extends AppCompatActivity {
    private AutoCompleteTextView search;
    private Button signout;
    private Button findPeople;
    private TextView assignedStaff;
    static ArrayAdapter<String> list;
    static ArrayList<String> people;
    static ArrayList<String> assignedStaffList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        search = (AutoCompleteTextView) findViewById(R.id.search);
        signout = (Button) findViewById(R.id.signOut);
        findPeople = (Button) findViewById(R.id.findPerson);
        assignedStaff = (TextView) findViewById(R.id.assignedStaff);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        people = new ArrayList<>();
        assignedStaffList = new ArrayList<>();
        final Bundle b = getIntent().getExtras();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String currRoom = "";
                String myName = "";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String room = ds.child("room").getValue(String.class);
                    if (name.charAt(name.length() - 1) != '1') {
                        people.add(name);
                        if (ds.child("email").getValue(String.class).equals(b.getString("eeemail"))){
                            myName = name;
                            for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                                String name1 = ds1.child("name").getValue(String.class);
                                if (name1.charAt(name1.length() - 1) == '1' && myName.equals(name1.substring(0, name1.length()-1))) {
                                    String room1 = ds1.child("room").getValue(String.class);
                                    currRoom = room1;
                                    break;
                                }
                            }
                        }
                    }
                }
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue(String.class);
                    String room = ds.child("room").getValue(String.class);
                    Log.d("NAME LOOKING AT: ", ""+name + room + currRoom);
                    if (name.charAt(name.length() - 1) != '1'){
                        if(room.equals(currRoom)){
                            assignedStaffList.add(name);
                            Log.d("NAME ADDING: ", ""+assignedStaffList.size());
                        }
                    }
                }
                String text = "";
                for (int i = 0; i < assignedStaffList.size(); i++){
                    Log.d("ADDING: ", assignedStaffList.get(i));
                    text += (assignedStaffList.get(i) + "\n");
                }
                Log.d("HEREEEEE: ", ""+assignedStaffList.size());
                Log.d("TEXT TO ADD: ", text);
                assignedStaff.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        mDatabase.child("users").addListenerForSingleValueEvent(eventListener);

        list = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, people);
        search.setThreshold(1);
        search.setAdapter(list);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this, MainActivity.class));
            }
        });

        findPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passname = search.getText().toString();
                Intent intent = new Intent(HomePage.this, PersonInfo.class);
                Bundle b = new Bundle();
                b.putString("passname", passname);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
