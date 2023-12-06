package fp.dam.pmdm.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
        String[] clause = {username};
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT datos_password FROM DatosJuego " +
                        "WHERE datos_username = ?",
                clause
        );
        cursor.moveToFirst();
        if (cursor.getCount() == 1 && (cursor.getString(0)).equals(password))
            return true;
        return false;
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