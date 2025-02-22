package com.movil.appmovil1527245;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registar_clientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registar_clientes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText dnicliente = findViewById(R.id.txtDniCliente);
        EditText apellidocliente = findViewById(R.id.txtApellidoCLiente);
        EditText nombrecliente = findViewById(R.id.txtNombreCliente);
        EditText telefonocliente = findViewById(R.id.txtTelefonoCliente);
        EditText correocliente = findViewById(R.id.txtCorreoCliente);
        EditText direccioncliente = findViewById(R.id.txtDireccionCliente);

        Button btnregistarcliente = findViewById(R.id.btnregistarcliente);

        String idLogeado = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btnregistarcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = dnicliente.getText().toString();
                String apellido = apellidocliente.getText().toString();
                String nombre = nombrecliente.getText().toString();
                String telefono = telefonocliente.getText().toString();
                String correo = correocliente.getText().toString();
                String direccion = direccioncliente.getText().toString();

                if (TextUtils.isEmpty(dni)||TextUtils.isEmpty(apellido)||TextUtils.isEmpty(nombre)||TextUtils.isEmpty(telefono)||TextUtils.isEmpty(correo)||TextUtils.isEmpty(direccion)){
                    Toast.makeText(registar_clientes.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
                    String idCliente = dr.child("Cientes").push().getKey();

                    Map<String, String > client = new HashMap<>();
                    client.put("dni",dni);
                    client.put("apellido", apellido);
                    client.put("nombre", nombre);
                    client.put("telefono",telefono);
                    client.put("correo", correo);
                    client.put("direccion", direccion);

                    dr.child("clientes").child(idLogeado).child(idCliente).setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(registar_clientes.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                Intent con2 = new Intent(registar_clientes.this, vClientes.class);
                                startActivity(con2);
                            } else {
                                Toast.makeText(registar_clientes.this, "Error de Registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }
}