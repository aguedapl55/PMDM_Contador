package fp.dam.pmdm.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PantallaLogin extends AppCompatActivity {

    String username, password;
    DB_Handler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login);
        db = new DB_Handler(getBaseContext());
        username = password = "";
    }

    public void iniciarSesion(View view) {
        EditText editText = findViewById(R.id.plEdtUsuario);
        username = editText.getText().toString();
        editText = findViewById(R.id.plEdtContraseña);
        password = editText.getText().toString();

        if (datosCorrectos()) {
            goto_MainActivity(view);
        } else {
            Toast toast = Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private boolean datosCorrectos() {
        String[] columnas = {
                DB_Handler.datos_password
        };
        String whereClause =  DB_Handler.datos_username + " = ?";
        String[] whereData = {
                username
        };

        Cursor cursor = db.getReadableDatabase().query(
                DB_Handler.TABLE_NAME,
                columnas,
                whereClause,
                whereData,
                null,
                null,
                null
        );
        String s = "" + cursor.getCount();
        Log.wtf(null, "" + s);
        //if (cursor.getCount() == 1 && cursor.getString(1).equals(password)) {
        //    return true; //los datos son correctos
        //}
        return false; //los datos NO son correctos
    }

    public void volver(View v) {
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
        finish();
    }

    public void goto_MainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("username", username);
        i.putExtra("password", password);
        startActivity(i);
        finish();
    }

    public void goto_Registrarse(View v) {
        Intent i = new Intent(this, PantallaRegistro.class);
        startActivity(i);
        finish();
    }
}