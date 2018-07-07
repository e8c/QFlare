package com.example.emilychou.qflare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText UsernameText;
    private EditText PasswordText;
    private Button go_button;
    private Button signup_button;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("qflare-b7f19");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsernameText = (EditText) findViewById(R.id.UsernameText);
        PasswordText = (EditText) findViewById(R.id.PasswordText);
        go_button = (Button) findViewById(R.id.go_button);
        signup_button = (Button) findViewById(R.id.signup_button);
        go_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String uname = UsernameText.getText().toString();
                final String pword = PasswordText.getText().toString();
                //check if in database
                if (TextUtils.isEmpty(uname)){
                    Toast.makeText(getApplicationContext(), "Enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pword)) {
                    Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(uname, pword)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                //progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                                } else {
                                    Intent intent = new Intent(MainActivity.this, HomePage.class);
                                    Bundle b = new Bundle();
                                    b.putString("eeemail", uname);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                //else Toast.makeText(MainActivity.this, "Enter a valid username and password, Toast.LENGTH_SHORT).show();
            }
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupPage.class));
            }
        });
    }

}
