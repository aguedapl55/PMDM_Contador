package fp.dam.pmdm.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
    }

    public void irMainActivity(View v) {
        Intent i = new Intent(this, PantallaLogin.class);
        startActivity(i);
        finish();
    }

    public void irPantallaOpciones(View v) {
        Intent i = new Intent(this, PantallaOpciones.class);
        startActivity(i);
        finish();
    }

    public void irPantallaInfo(View v) {
        Intent i = new Intent(this, PantallaInfo.class);
        startActivity(i);
        finish();
    }

    public void cerrarApp(View v) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setMessage("¿Cerrar la aplicación?")
                .setTitle("Salir")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialogo = constructor.create();
        dialogo.show();
    }


}