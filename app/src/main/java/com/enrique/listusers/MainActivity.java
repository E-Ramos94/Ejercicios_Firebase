package com.enrique.listusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                //comvertir a string los editext de correo y password
                String correo = CorreoLogin.getText().toString();
                String pass = PasswordLogin.getText().toString();

                LogeoUsuarios(correo, pass);
            }
        });

        RegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterUsers.class));
            }
        });
    }

    //METODO DE LOGIN
    private void LogeoUsuarios(String correo, String pass) {
        progressDialog.show();
        progressDialog.setCancelable(false);
        firebaseAuth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Si el inicio de sesion fue exitoso
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this, UserList.class));
                            Toast.makeText(MainActivity.this, "Bienvenido/a " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        } else {
                            progressDialog.dismiss();
                            UsuarioInvalido();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        UsuarioInvalido();
                    }
                });
    }

    private void UsuarioInvalido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setTitle("¡HA OCURRIDO UN ERROR!");
        builder.setMessage("Verifique si el correo o password sea los correctos")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}