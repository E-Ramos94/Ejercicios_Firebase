package com.enrique.listusers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText CorreoLogin, PasswordLogin;
    Button AccederLogin, RegisterLogin;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CorreoLogin = findViewById(R.id.CorreoLogin);
        PasswordLogin = findViewById(R.id.PasswordLogin);
        AccederLogin = findViewById(R.id.AccederLogin);
        RegisterLogin = findViewById(R.id.RegisterLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Ingrensando espere por favor...");
        progressDialog.setCancelable(false);

        AccederLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FUNCION LOGIN
            }
        });

        RegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterUsers.class));
            }
        });
    }
}