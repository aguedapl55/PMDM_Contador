package fp.dam.pmdm.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView contador;
    BigInteger num, multiplier, increment, costClick, costAutoC;
    String username, password;
    ImageView coin_image;
    SharedPreferences sharedPreferences;

    private ArrayList<zzDataStorage> dataArrayL;
    private DB_Handler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //me he enterado hoy (viernes 10/11/2023) que no habia que hacer los datos persistentes
        //dicho esto, me he complicado demasiado la vida haciendolo como para quitarlo, asi que aqui esta
//      sharedPreferences = getSharedPreferences("datosSP", Context.MODE_PRIVATE);

        db = new DB_Handler(getBaseContext());

        try { // maybe cambiar esto al catch?
            Bundle extras = getIntent().getExtras();
            username = extras.getString("username");
            password = extras.getString("password");
            try {
                num = (BigInteger) extras.get("num");
                multiplier = (BigInteger) extras.get("multiplier");
                increment = (BigInteger) extras.get("increment");
                costClick = (BigInteger) extras.get("costClick");
                costAutoC = (BigInteger) extras.get("costAutoC");
            } catch (NullPointerException e) {
                String whereClause =  DB_Handler.datos_username + " = ?";
                String[] whereData = {
                    username
                };

                Cursor cursor = db.getReadableDatabase().query(
                        DB_Handler.TABLE_NAME,
                        null, //esto devuelve todas las columnas
                        whereClause,
                        whereData,
                        null,
                        null,
                        null
                );

                num = BigInteger.valueOf(Long.parseLong(cursor.getString(2)));
                multiplier = BigInteger.valueOf(Long.parseLong(cursor.getString(3)));
                increment = BigInteger.valueOf(Long.parseLong(cursor.getString(4)));
                costClick = BigInteger.valueOf(Long.parseLong(cursor.getString(5)));
                costAutoC = BigInteger.valueOf(Long.parseLong(cursor.getString(6)));
            }
        } catch (Exception e) {
            num = increment = BigInteger.ZERO;
            multiplier = BigInteger.ONE;
            costClick = costAutoC = BigInteger.valueOf(100);
        }

        generarHiloAC(increment);

        contador = findViewById(R.id.textoContador);
        contador.setText(BigInteger.ZERO.add(num).toString());
        coin_image = findViewById(R.id.moneda);
    }

    // FUNCIONALIDAD MONEDA ///////////////////////////////////////////////////////////////////////

    public void animMoneda(View v) {
        ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(100);
        coin_image.startAnimation(fade_in);
    }

    public void sumar(View v) {
        animMoneda(v);
        num = num.add(multiplier);
        contador.setText(numSize(num));
    }

    public static String numSize(BigInteger num) {
        String quant = "";
        BigInteger reduc = num;

        int len = num.toString().length() - 1;
        int comas = (len / 3);
        //cuenta cuantas comas hay en el numero, sirve para if de miles, millones, etc.

        if (comas == 0) {
        } else if (comas == 1) {
            reduc = reduc.divide(BigInteger.valueOf(1_000));
            quant = "K";
        } else if (comas == 2) {
            reduc = reduc.divide(BigInteger.valueOf(1_000_000));
            quant = "M";
        } else if (comas == 3) {
            reduc = reduc.divide(BigInteger.valueOf(1_000_000_000));
            quant = "B";
        } else if (comas == 4) {
            reduc = reduc.divide(BigInteger.valueOf(Long.parseLong("1000000000000"))); //1_000_000_000_000
            quant = "T";
        } else if (comas == 5) {
            reduc = reduc.divide(BigInteger.valueOf(Long.parseLong("1000000000000000"))); //1_000_000_000_000_000
            quant = "Q";
        } else {
            reduc = reduc.divide(BigInteger.valueOf(Long.parseLong("1000000000000000000"))); //1_000_000_000_000_000_000
            quant = "+";
        }
        return ""+reduc+quant;
    }

    // INTENTS Y DATOS ////////////////////////////////////////////////////////////////////////////

    public void goto_Tienda(View v) {
        Intent i = new Intent(this, Tienda.class);
        i.putExtra("username", username);
        i.putExtra("password", password);
        i.putExtra("num", num);
        i.putExtra("multiplier", multiplier);
        i.putExtra("increment", increment);
        try {
            i.putExtra("costClick", costClick);
            i.putExtra("costAutoC", costAutoC);
        } catch (NullPointerException e) {}
        startActivity(i);
        finish();
    }

    public void volver(View v) {
        if (guardarDatos() == 1) {
            Intent i = new Intent(this, PantallaInicio.class);
            startActivity(i);
            finish();
        } else {
            Toast toast = Toast.makeText(this, "Hubo un error al guardar", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private int guardarDatos() {
        BigInteger score = num
                .add((multiplier.add(BigInteger.valueOf(-1))).multiply(BigInteger.valueOf(50)))
                    //(multiplier-1)*50 //multiplier empieza en 1
                .add(increment.multiply(BigInteger.valueOf(50)));
                    //increment*50

        SQLiteDatabase sqlDB = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(DB_Handler.datos_username, username);
        //values.put(DB_Handler.datos_password, password);
        values.put(DB_Handler.datos_num, num.toString());
        values.put(DB_Handler.datos_mult, multiplier.toString());
        values.put(DB_Handler.datos_inc, increment.toString());
        values.put(DB_Handler.datos_cClick, costClick.toString());
        values.put(DB_Handler.datos_cAutoC, costAutoC.toString());
        values.put(DB_Handler.datos_score, score.toString());

        String whereClause = DB_Handler.datos_username + " LIKE ?";
        String[] whereData = {username};

        int count = sqlDB.update(
                DB_Handler.TABLE_NAME,
                values,
                whereClause,
                whereData
        );

        return count;
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

}