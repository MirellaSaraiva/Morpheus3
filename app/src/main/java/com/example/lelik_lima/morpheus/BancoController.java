package com.example.lelik_lima.morpheus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lelik_lima on 22/07/18.
 */

public class BancoController {

    public SQLiteDatabase db;
    public sqlitee banco;
    public criandoAmod banco2;


    public BancoController(Context context){
        banco = new sqlitee(context);
        banco2 = new criandoAmod(context);
    }



    public String insereDado(String Di, String Ho, String Mi, String Se, String Ma){
        ContentValues valores;
        long resultado;



        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(sqlitee.DIA, Di);
        valores.put(sqlitee.HORA, Ho);
        valores.put(sqlitee.MINUTOS, Mi);
        valores.put(sqlitee.SEGUNDOS, Se);
        valores.put(sqlitee.MAGNITUDE, Ma);

        resultado = db.insert(sqlitee.TABELA, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public String insereDadoAmod(String Di, String Ho, String Mi, String Se, String Ma){
        ContentValues valores;
        long resultado;



        db = banco2.getWritableDatabase();
        valores = new ContentValues();
        valores.put(sqlitee.DIA, Di);
        valores.put(sqlitee.HORA, Ho);
        valores.put(sqlitee.MINUTOS, Mi);
        valores.put(sqlitee.SEGUNDOS, Se);
        valores.put(sqlitee.MAGNITUDE, Ma);

        resultado = db.insert(criandoAmod.TABLE, null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados1(){
        Cursor cursor;
        String[] campos =  {banco.DIA, banco.HORA ,banco.MINUTOS, banco.SEGUNDOS, banco.MAGNITUDE};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor carregaDados2(){
        Cursor cursor;
        String[] campos =  {banco2.DIA, banco2.HORA ,banco2.MINUTOS, banco2.SEGUNDOS, banco2.MAGNITUDE};

        db = banco2.getReadableDatabase();
        cursor = db.query(banco2.TABLE, campos, null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
     public void deletarregistro2(){
         db= banco2.getReadableDatabase();
         db.delete(banco2.TABLE, null, null);
         db.close();

     }


    }

