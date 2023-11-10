package fp.dam.pmdm.contador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class PantallaOpciones extends AppCompatActivity {

    private TextView idioma;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_opciones);
        idioma = findViewById(R.id.poTxvIdioma);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getApplicationContext().getSharedPreferences("datosSP", Context.MODE_PRIVATE);

        RecyclerView rv = (RecyclerView) findViewById(R.id.poRecyclerView);
        rv.hasFixedSize();
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<String> l = Arrays.asList(
                "Número de monedas: " + sharedPreferences.getString("num", ""),
                "Billetes (monedas/click): " + sharedPreferences.getString("multiplier", ""),
                "AutoClickers (clicks/segundo): " + sharedPreferences.getString("increment", ""),
                "",
                "Coste de los billetes: " + sharedPreferences.getString("costClick", ""),
                "Coste de los Autoclickers: " + sharedPreferences.getString("costAutoC", "")
        );
        rv.setAdapter(new zzUserAdapter(l));
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