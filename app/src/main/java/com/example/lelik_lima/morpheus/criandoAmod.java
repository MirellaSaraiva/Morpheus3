package com.example.lelik_lima.morpheus;

/**
 * Created by lelik_lima on 22/07/18.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;
import android.widget.Toast;
import java.util.Locale;
import static android.widget.Toast.LENGTH_SHORT;


public class criandoAmod extends SQLiteOpenHelper {
    public static final String TABLE = "AModif";
    public static final String DIA = "dia";
    public static final String HORA = "hora";
    public static final String MINUTOS = "minutos";
    public static final String SEGUNDOS = "segundos";
    public static final String MAGNITUDE = "Magnitude";
    private final Context contexto;

    public criandoAmod(Context context){
        super(context, "AModif", null, 1);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.setLockingEnabled(true);
        String sql = "CREATE TABLE "+TABLE+" ("
                + DIA + " integer,"
                + HORA + " integer,"
                + MINUTOS + " integer,"
                + SEGUNDOS + " integer,"
                + MAGNITUDE + " float"
                +")";
        Toast.makeText(contexto, "CRIADO", LENGTH_SHORT).show();


        db.execSQL(sql);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }


}



