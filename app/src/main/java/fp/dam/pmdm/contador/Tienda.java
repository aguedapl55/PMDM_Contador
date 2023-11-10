package fp.dam.pmdm.contador;

import static fp.dam.pmdm.contador.MainActivity.numSize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    TextView contador;
    Button botonClick, botonAutoC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        botonClick = findViewById(R.id.dollarUGButton);
        botonAutoC = findViewById(R.id.autoclickUGButton);

        Bundle extras = getIntent().getExtras();
        num = (BigInteger) extras.get("num");
        multiplier = (BigInteger) extras.get("multiplier");
        increment = (BigInteger) extras.get("increment");
        costClick = (BigInteger) extras.get("costClick");
        costAutoC = (BigInteger) extras.get("costAutoC");

        if (increment.compareTo(BigInteger.ZERO) > 0)
            generarHiloAC(increment);

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

    // INTENTS ////////////////////////////////////////////////////////////////////////////////////

    public void volver(View v) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("num", num);
        i.putExtra("multiplier", multiplier);
        i.putExtra("increment", increment);
        i.putExtra("costClick", costClick);
        i.putExtra("costAutoC", costAutoC);
        startActivity(i);
        finish();
    }
}