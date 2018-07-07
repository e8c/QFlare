package com.example.emilychou.qflare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by emilychou on 7/7/18.
 */

public class SignupPage extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private Button signup;
    private EditText room;
    private EditText phoneNumber;
    private EditText position;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabase;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.passwordEntry);
        room = (EditText) findViewById(R.id.roomEntry);
        signup = (Button) findViewById(R.id.signup);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        position = (EditText) findViewById(R.id.position);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //progressDialog.dismiss();
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser(); //You Firebase user
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            User newUser = new User(name.getText().toString(),  room.getText().toString(), phoneNumber.getText().toString(), position.getText().toString(), email.getText().toString());
                            mDatabase.child("users").child(name.getText().toString()).setValue(newUser);
                            // user registered, start profile activity
                            Toast.makeText(SignupPage.this,"Account Created",Toast.LENGTH_LONG).show();

                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(SignupPage.this,"Could not create account. Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
