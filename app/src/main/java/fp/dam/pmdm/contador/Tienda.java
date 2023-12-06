package fp.dam.pmdm.contador;

import static fp.dam.pmdm.contador.MainActivity.numSize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tienda extends AppCompatActivity {

    BigInteger num, multiplier, increment, costClick, costAutoC;
    String username, password;
    TextView contador;
    Button botonClick, botonAutoC;
    DB_Handler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        botonClick = findViewById(R.id.dollarUGButton);
        botonAutoC = findViewById(R.id.autoclickUGButton);

        db = new DB_Handler(getBaseContext());

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        password = extras.getString("password");

        try {
            String[] clause = {username};
            Cursor cursor = db.getReadableDatabase().rawQuery(
                    "SELECT * FROM DatosJuego " +
                            "WHERE datos_username = ?",
                    clause
            );
            if (cursor!= null && cursor.getCount() > 0 && cursor.moveToFirst()){
                num = BigInteger.valueOf(Long.parseLong(cursor.getString(cursor.getColumnIndex(DB_Handler.datos_num))));
                multiplier = BigInteger.valueOf(Long.parseLong(cursor.getString(3)));
                increment = BigInteger.valueOf(Long.parseLong(cursor.getString(4)));
                costClick = BigInteger.valueOf(Long.parseLong(cursor.getString(5)));
                costAutoC = BigInteger.valueOf(Long.parseLong(cursor.getString(6)));
            }
        } catch (Exception e) {
            num = (BigInteger) extras.get("num");
            multiplier = (BigInteger) extras.get("multiplier");
            increment = (BigInteger) extras.get("increment");
            costClick = (BigInteger) extras.get("costClick");

        }

        contador = findViewById(R.id.textoContador);
        contador.setText(BigInteger.ZERO.add(num).toString());
    }

    // METODOS MEJORA /////////////////////////////////////////////////////////////////////////////

    public void mejorarClick(View v) {
        if (num.compareTo(costClick) >= 0) {
            multiplier = multiplier.add(BigInteger.ONE);
            num = num.subtract(costClick);
            costClick = costClick.add(BigInteger.valueOf(50));
            botonClick.setText(numSize(costClick).toString() + "c");
            contador.setText(numSize(num));
        }
    }

    public void mejorarAutoC(View v) {
        if (num.compareTo(costAutoC) >= 0) {
            increment = increment.add(BigInteger.ONE);
            num = num.subtract(costAutoC);
            costAutoC = costAutoC.add(BigInteger.valueOf(50));
            botonClick.setText(numSize(costAutoC).toString() + "c");
            contador.setText(numSize(num)); //equivalente a sumar 1??
            generarHiloAC(BigInteger.ONE);
        }
    }

    public void generarHiloAC(BigInteger i) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    num = num.add(i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contador.setText(numSize(num));
                        }
                    });
                }
            }
        });
    }

    // INTENTS Y DATOS ////////////////////////////////////////////////////////////////////////////

    public void volver(View v) {
        guardarDatos();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("num", num);
        i.putExtra("multiplier", multiplier);
        i.putExtra("increment", increment);
        i.putExtra("costClick", costClick);
        i.putExtra("costAutoC", costAutoC);
        startActivity(i);
        finish();
    }

    public void guardarDatos() {
        BigInteger score = num
                .add((multiplier.add(BigInteger.valueOf(-1))).multiply(BigInteger.valueOf(50)))
                //(multiplier-1)*50 //multiplier empieza en 1
                .add(increment.multiply(BigInteger.valueOf(50)));
        //increment*50

        String[] datos = {
                username,
                password,
                "" + num,
                "" + multiplier,
                "" + increment,
                "" + costClick,
                "" + costAutoC,
                "" + score,
                username
        };

        Cursor cursor = db.getReadableDatabase().rawQuery(
                "UPDATE DatosJuego " +
                        "SET datos_username = ?, " +
                        "datos_password = ?, " +
                        "datos_num = ?, " +
                        "datos_mult = ?, " +
                        "datos_inc = ?, " +
                        "datos_cClick = ?, " +
                        "datos_cAutoC = ?, " +
                        "datos_score = ?" +
                        "WHERE datos_username = ?",
                datos
        );

        cursor.moveToFirst();
        cursor.close();
    }
}