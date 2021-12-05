package com.lau.kobstonefetch;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

public class LoginActivity extends AppCompatActivity {

    EditText email, pass;
    CardView btn;
    TextView reg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        email = (EditText) findViewById(R.id.login_username);
        pass = (EditText) findViewById(R.id.login_pass);
        btn = (CardView) findViewById(R.id.login_card);
        reg = (TextView) findViewById(R.id.register_text);
    }

    public void UserLogin (View view) {

        final String emailTxt = email.getText().toString();
        final String passTxt = pass.getText().toString();

        if(emailTxt.isEmpty() || passTxt.isEmpty()) Toast.makeText(this, "Please fill in the required fields!",Toast.LENGTH_SHORT).show();
        else {

        }
    }
}
