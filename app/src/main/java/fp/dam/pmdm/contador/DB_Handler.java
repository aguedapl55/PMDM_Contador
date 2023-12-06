package fp.dam.pmdm.contador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB_Handler extends SQLiteOpenHelper{

    public static final String DB_NAME = "Datos.db";
    public static final String TABLE_NAME = "DatosJuego";
    public static final int DB_VERSION = 1;
    public static final String datos_username = "datos_username";
    public static final String datos_password = "datos_password";
    public static final String datos_num = "datos_num";
    public static final String datos_mult = "datos_mult";
    public static final String datos_inc = "datos_inc";
    public static final String datos_cClick = "datos_cClick";
    public static final String datos_cAutoC = "datos_cAutoC";
    public static final String datos_score = "datos_score";

    public DB_Handler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String entries =
            "CREATE TABLE " + TABLE_NAME + " (" +
                datos_username + " VARCHAR2(16) NOT NULL PRIMARY KEY, " +
                datos_password + " VARCHAR2(16) NOT NULL, " +
                datos_num + " VARCHAR2 NOT NULL," +
                datos_mult + " VARCHAR2 NOT NULL," +
                datos_inc + " VARCHAR2 NOT NULL," +
                datos_cClick + " VARCHAR2 NOT NULL, " +
                datos_cAutoC + " VARCHAR2 NOT NULL, " +
                datos_score + " VARCHAR2" +
                ");";
        db.execSQL(entries);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ESTE METODO SE ESTA USANDO PARA BORRAR DATOS
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
