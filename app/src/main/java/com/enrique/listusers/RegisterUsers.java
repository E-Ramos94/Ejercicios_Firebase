package com.enrique.listusers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterUsers extends AppCompatActivity {

    EditText Correo, Password, Nombres, Apellidos, Edad;
    Button Registrar;

    DatabaseReference DB_USERS;
    FirebaseAuth auth;

    ProgressDialog progressDialog;

    boolean modificar = false;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users);

        Correo = findViewById(R.id.Correo);
        Password = findViewById(R.id.Password);
        Nombres = findViewById(R.id.Nombres);
        Apellidos = findViewById(R.id.Apellidos);
        Edad = findViewById(R.id.Edad);

        Registrar = findViewById(R.id.Registrar);

        auth = FirebaseAuth.getInstance();

        DB_USERS = FirebaseDatabase.getInstance().getReference("USERS");

        Intent intent = getIntent();
        if (intent.hasExtra("UID")) {
            modificar = true;
            uid = intent.getStringExtra("UID");
            String nombres = intent.getStringExtra("NOMBRES");
            String apellidos = intent.getStringExtra("APELLIDOS");
            String correo = intent.getStringExtra("CORREO");
            String pass = intent.getStringExtra("PASSWORD");
            String edad = intent.getStringExtra("EDAD");

            Nombres.setText(nombres);
            Apellidos.setText(apellidos);
            Correo.setText(correo);
            Password.setText(pass);
            Edad.setText(edad);
        } else {
            modificar = false;
        }

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //convertir a string los editext
                String correo = Correo.getText().toString();
                String pass = Password.getText().toString();
                String nombre = Nombres.getText().toString();
                String apellidos = Apellidos.getText().toString();
                String edad = Edad.getText().toString();

                //Todos los campos son obligatorios
                if (correo.equals("") || pass.equals("") || nombre.equals("") || apellidos.equals("") || edad.equals("")) {
                    Toast.makeText(RegisterUsers.this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    //Validacion de correo y contrasena
                    if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                        Correo.setError("Correo invalido");
                        Correo.setFocusable(true);
                    } else if (pass.length()<6) {
                        Password.setError("Password debe ser mayor o igual a 6 caracteres");
                    } else {
                        if (modificar == true) {
                            ModificarUsuario(uid);
                        } else {
                            RegistroUsuarios(correo, pass);
                        }

                    }
                }
            }
        });

        progressDialog = new ProgressDialog(RegisterUsers.this);
        progressDialog.setMessage("Registrando, espere por favor...");
        progressDialog.setCancelable(false);
    }

    //METODO PARA CREAR USUARIOS
    private void RegistroUsuarios(String correo, String pass) {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Si el usuario fue creado correctamente
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;

                            //convertir a cadena los datos del usuario
                            String UID = user.getUid();
                            String correo = Correo.getText().toString();
                            String pass = Password.getText().toString();
                            String nombre = Nombres.getText().toString();
                            String apellidos = Apellidos.getText().toString();
                            String edad = Edad.getText().toString();
                            int EdadInt = Integer.parseInt(edad);

                            HashMap<Object, Object> Users = new HashMap<>();
                            Users.put("UID", UID);
                            Users.put("CORREO", correo);
                            Users.put("PASSWORD", pass);
                            Users.put("NOMBRES", nombre);
                            Users.put("APELLIDOS", apellidos);
                            Users.put("EDAD", EdadInt);

                            //Inicializar FireDatabase
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("USERS");
                            reference.child(UID).setValue(Users);

                            startActivity(new Intent(RegisterUsers.this, MainActivity.class));
                            Toast.makeText(RegisterUsers.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterUsers.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterUsers.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //METODO PARA ACTUALIZAR USUARIOS
    private void ModificarUsuario(String uid) {
        String nombres = Nombres.getText().toString();
        String apellidos = Apellidos.getText().toString();
        String correo = Correo.getText().toString();
        String pass = Password.getText().toString();
        String edad = Edad.getText().toString();
        int EdadInt = Integer.parseInt(edad);

        HashMap<String, Object> resultado = new HashMap<>();
        resultado.put("NOMBRES", nombres);
        resultado.put("APELLIDOS", apellidos);
        resultado.put("CORREO", correo);
        resultado.put("PASSWORD", pass);
        resultado.put("EDAD", EdadInt);

        DB_USERS.child(uid).updateChildren(resultado)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterUsers.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterUsers.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}