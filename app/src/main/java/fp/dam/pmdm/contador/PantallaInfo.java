package fp.dam.pmdm.contador;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class PantallaInfo extends ListActivity implements AdapterView.OnItemClickListener {
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_info);

        b = findViewById(R.id.pcCookieClicker);

        crearInstrucciones();
    }

    public void crearInstrucciones() {
        List<zzInstrucciones> instr = Arrays.asList(
            new zzInstrucciones("PRUEBA", R.drawable.coin)
        );
        zzInstrAdapter adapter = new zzInstrAdapter(this, R.layout.zz_fila_listview, instr);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        zzInstrucciones instr = (zzInstrucciones) adapterView.getItemAtPosition(i);
        Toast.makeText(this, instr.getPalabras(), Toast.LENGTH_LONG).show();
    }

    public void webCookieClicker(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://orteil.dashnet.org/cookieclicker/"));
        startActivity(intent);
    }

    public void volver(View v) {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
        finish();
    }

}