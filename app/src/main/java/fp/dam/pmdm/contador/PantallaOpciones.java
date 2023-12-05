package fp.dam.pmdm.contador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PantallaOpciones extends AppCompatActivity {

    private TextView idioma;
    DB_Handler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_opciones);
        idioma = findViewById(R.id.poTxvIdioma);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rv = (RecyclerView) findViewById(R.id.poRecyclerView);
        rv.hasFixedSize();
        rv.setLayoutManager(new LinearLayoutManager(this));

        db = new DB_Handler(this);
        String[] columnas = {
                DB_Handler.datos_username,
                DB_Handler.datos_score
        };
        Cursor cursor = db.getReadableDatabase().query(
                DB_Handler.TABLE_NAME,
                columnas,
                null,
                null,
                null,
                null,
                null
        );

        List<String[]> lista = new ArrayList<>();
        String[] resul = {"usuario", "puntuación"};
        lista.add(resul);
        while (!cursor.isAfterLast()) {
            resul = new String[] {
                    cursor.getString(0),
                    cursor.getString(1)};
            lista.add(resul);
        }
        rv.setAdapter(new zzUserAdapter(lista));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.cmIngles) {
            idioma.setText("TRABAJO EN PROCESO: cambiar idioma a ingles");
        } else if (itemId == R.id.cmEspañol) {
            idioma.setText("TRABAJO EN PROCESO: cambiar idioma a español");
        }
            return super.onOptionsItemSelected(item);
    }

    public void volver(View v) {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
        finish();
    }

}