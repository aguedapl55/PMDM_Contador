package fp.dam.pmdm.contador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView contador;
    BigInteger num, multiplier, increment, costClick, costAutoC;
    ImageView coin_image;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("datosSP", Context.MODE_PRIVATE);

        try {
            Bundle extras = getIntent().getExtras();
            num = (BigInteger) extras.get("num");
            multiplier = (BigInteger) extras.get("multiplier");
            increment = (BigInteger) extras.get("increment");
            costClick = (BigInteger) extras.get("costClick");
            costAutoC = (BigInteger) extras.get("costAutoC");
        } catch (NullPointerException e) { //este try es porque, la primera vez que se entre al juego, va a ser null si o si
            num = BigInteger.ZERO;
            multiplier = BigInteger.ONE;
            increment = BigInteger.ZERO;
            costClick = costAutoC = BigInteger.valueOf(100); //la unica razon por la que se pasa es para que no se pierda el numero sos
        }

        if (num.compareTo(BigInteger.valueOf(Long.parseLong(sharedPreferences.getString("num", "")))) < 0) {
            num = BigInteger.valueOf(Long.parseLong(sharedPreferences.getString("num", "")));
            multiplier = BigInteger.valueOf(Long.parseLong(sharedPreferences.getString("multiplier", "")));
            increment = BigInteger.valueOf(Long.parseLong(sharedPreferences.getString("increment", "")));
            costClick = BigInteger.valueOf(Long.parseLong(sharedPreferences.getString("costClick", "")));
            costAutoC = BigInteger.valueOf(Long.parseLong(sharedPreferences.getString("costAutoC", "")));
        }

        if (increment.compareTo(BigInteger.ZERO) > 0)
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
        int comas = (len / 3); //cuenta cuantas comas hay en el numero, sirve para switch de miles, millones, etc.

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
        guardarDatos();
        Intent i = new Intent(this, PantallaInicio.class);
        startActivity(i);
        finish();
    }

    private void guardarDatos() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //los meto en string para evitar que sea demasiado grande para guardar (aunque el unico que tiene riesgo de llegar a eso es el num tbh)
        editor.putString("num", num.toString());
        editor.putString("multiplier", multiplier.toString());
        editor.putString("increment", increment.toString());
        editor.putString("costClick", costClick.toString());
        editor.putString("costAutoC", costAutoC.toString());

        editor.commit();
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