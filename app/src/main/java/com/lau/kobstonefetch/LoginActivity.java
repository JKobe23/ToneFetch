package com.lau.kobstonefetch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    EditText username, pass;
    CardView btn;
    TextView reg;

    DatabaseReference dbr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tonefetch-default-rtdb.firebaseio.com/");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        username = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.passwordd);
        btn = (CardView) findViewById(R.id.login_card);
        reg = (TextView) findViewById(R.id.register_text);
    }

    public void UserLogin (View view) {

        final String userTxt = username.getText().toString();
        final String passTxt = pass.getText().toString();

        if(userTxt.isEmpty() || passTxt.isEmpty()) Toast.makeText(this, "Please fill in the required fields!",Toast.LENGTH_SHORT).show();

        else {

            dbr.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.hasChild(userTxt)) {

                        String password_entry = snapshot.child(userTxt).child("password").getValue(String.class);
                        String hashed_entry = encryptPass(password_entry);
                        String stored_hash = encryptPass(passTxt);
                        if (hashed_entry.equals(stored_hash)) Toast.makeText(LoginActivity.this, "Login successful.",Toast.LENGTH_SHORT).show();
                        else Toast.makeText(LoginActivity.this, "Wrong password, try again.",Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(LoginActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void UserSignup (View view) {
        Intent i1 = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i1);

    }

    private String encryptPass(String original) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(original.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);


            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}


