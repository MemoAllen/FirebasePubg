package mx.edu.utng.firebasepubg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import modelo.Juego;

public class MainActivity extends AppCompatActivity {

private List<Juego> listJuego = new ArrayList<Juego>();
ArrayAdapter<Juego> arrayAdapterJuego;


    EditText nomJ, armaJ, vehiculoj;
    ListView listV_Listado;

FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;

Juego juegoSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            nomJ =findViewById(R.id.txtNombre);
            armaJ = findViewById(R.id.txtArma);
            vehiculoj = findViewById(R.id.txtVehiculo);
            listV_Listado = findViewById(R.id.lvListado);

            //Metodo principal a Firebase
        //Nota: todos los demas metodos que sean relacionados a Firebase deben ser declarados abajo
            iniciarFirebase();
            listarDatos();

            listV_Listado.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 juegoSelected = (Juego) parent.getItemAtPosition(position);
                 nomJ.setText(juegoSelected.getNombre());
                    armaJ.setText(juegoSelected.getArmas());
                    vehiculoj.setText(juegoSelected.getVehiculo());
                }
            });


    }

    private void listarDatos() {
    databaseReference.child("Juegos").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            listJuego.clear();
            for (DataSnapshot objSnaptshot: dataSnapshot.getChildren()){
                Juego j =objSnaptshot.getValue(Juego.class);
                listJuego.add(j);

                arrayAdapterJuego = new ArrayAdapter<Juego>(MainActivity.this, android.R.layout.simple_list_item_1, listJuego);
                listV_Listado.setAdapter(arrayAdapterJuego);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }

    private void iniciarFirebase() {

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

       // firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        String nombre = nomJ.getText().toString();
        String arma = armaJ.getText().toString();
        String vehiculo = vehiculoj.getText().toString();

        switch (item.getItemId()){
            case R.id.icon_add:{
                if(nombre.equals("")||arma.equals("")||vehiculo.equals("")){
                    validacion();

                }else{
                    Juego j = new Juego();
                    j.setUid(UUID.randomUUID().toString());
                    j.setNombre(nombre);
                    j.setArmas(arma);
                    j.setVehiculo(vehiculo);
                    databaseReference.child("Juegos").child(j.getUid()).setValue(j);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_save:{

                Juego j = new Juego();
                j.setUid(juegoSelected.getUid());
                j.setNombre(nomJ.getText().toString().trim());
                j.setArmas(armaJ.getText().toString().trim());
                j.setVehiculo(vehiculoj.getText().toString().trim());

                databaseReference.child("Juegos").child(j.getUid()).setValue(j);

                Toast.makeText(this, "Actualizo", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete:{
                Juego j = new Juego();
                j.setUid(juegoSelected.getUid());
                databaseReference.child("Juegos").child(j.getUid()).removeValue();
                Toast.makeText(this, "Elimino", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                break;
            }
            default:break;
        }

        return true;
    }

    private void limpiarCajas() {

        nomJ.setText("");
        armaJ.setText("");
        vehiculoj.setText("");
    }

    private void validacion() {
        String nombre = nomJ.getText().toString();
        String arma = armaJ.getText().toString();
        String vehiculo = vehiculoj.getText().toString();


        if(nombre.equals("")){
            nomJ.setError("Required");
        }else if(arma.equals("")){
            armaJ.setError("Required");
        }else if(vehiculo.equals("")){
            vehiculoj.setError("Required");
        }
    }
}