package fp.dam.pmdm.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PantallaRegistro extends AppCompatActivity {

    public String username, password, confpassword;
    DB_Handler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro);
        username = password = confpassword = "";
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
        String[] columnas = {
                DB_Handler.datos_username
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

        if (cursor.getCount() == 0) return false; //usuario NO existe
        else return true; //usuario existe

    }

    public void crearCuenta() {
        SQLiteDatabase sqlDB = db.getWritableDatabase();

        ContentValues values = new ContentValues();

        String s = "user = " + username + " ; pass = " + password;
        Log.wtf(null, s);

        values.put(DB_Handler.datos_username, username);
        values.put(DB_Handler.datos_password, password);
        values.put(DB_Handler.datos_num, "0");
        values.put(DB_Handler.datos_mult, "1");
        values.put(DB_Handler.datos_inc, "0");
        values.put(DB_Handler.datos_cClick, "100");
        values.put(DB_Handler.datos_cAutoC, "100");

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