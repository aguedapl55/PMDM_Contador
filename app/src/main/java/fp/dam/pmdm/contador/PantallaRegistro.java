package fp.dam.pmdm.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaRegistro extends AppCompatActivity {

    public String username, password, confpassword;
    DB_Handler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);
        db = new DB_Handler(getBaseContext());
    }

    public void volver(View v) {
        Intent i = new Intent(this, PantallaLogin.class);
        startActivity(i);
        finish();
    }

    public void registrarse(View view) {
        if (usuarioExiste()) {
            Toast toast = Toast.makeText(this, "El usuario introducido ya existe", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (!passwordBien()) {
            Toast toast = Toast.makeText(this, "Contraseña no introducida correctamente", Toast.LENGTH_SHORT);
            toast.show();
        } else { //si el usuario no existe && contraseña correcta
            Toast toast = Toast.makeText(this, "else registrarse", Toast.LENGTH_SHORT);
            toast.show();
            crearCuenta();
            goto_MainActivity(view);
        }
    }

    private boolean passwordBien() {
        EditText editText = findViewById(R.id.prEdtContraseña);
        password = editText.getText().toString();
        editText = findViewById(R.id.prEdtConfContr);
        confpassword = editText.getText().toString();
        return password.equals(confpassword);
    }

    private boolean usuarioExiste() {
        TextView textview = findViewById(R.id.prEdtUsuario);
        username = textview.getText().toString();
        String[] clause = {username};
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT " + DB_Handler.datos_username + " FROM " + DB_Handler.TABLE_NAME +
                        " WHERE " + DB_Handler.datos_username + " = ?",
                clause
        );

        cursor.moveToFirst();

        if (cursor.getCount() == 1 && cursor.getString(0).equals(username))
            return true; //usuario SI existe
        return false; //usuario NO existe

    }

    public void crearCuenta() {
        TextView textview = findViewById(R.id.prEdtUsuario);
        username = textview.getText().toString();
        textview = findViewById(R.id.prEdtContraseña);
        password = textview.getText().toString();

        SQLiteDatabase sqlDB = db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DB_Handler.datos_username, username);
        values.put(DB_Handler.datos_password, password);
        values.put(DB_Handler.datos_num, "0");
        values.put(DB_Handler.datos_mult, "1");
        values.put(DB_Handler.datos_inc, "0");
        values.put(DB_Handler.datos_cClick, "100");
        values.put(DB_Handler.datos_cAutoC, "100");
        values.put(DB_Handler.datos_score, "0");

        sqlDB.insert(DB_Handler.TABLE_NAME, null, values);

        sqlDB.close();
    }

    public void goto_MainActivity(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("username", username);
        i.putExtra("password", password);
        startActivity(i);
        finish();
    }
}