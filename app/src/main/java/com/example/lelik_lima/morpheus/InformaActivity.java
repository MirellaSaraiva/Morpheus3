package com.example.lelik_lima.morpheus;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class InformaActivity extends AppCompatActivity {

    TextView teste1, teste2, teste3,analitext;
    Cursor cursor, cursor2;
    BancoController crud;
    double media, soma, desvio, ultimaposicao, lsup, linf;
    double[] vetor;
    int [] State;
    int [] diavetor;
    String[] nomeCampos;
    int tamanho, pos1,pos2,pos3, lenght_ofnormalizedata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise);

        crud = new BancoController(getBaseContext());
        cursor= crud.carregaDados1();
        teste1 = (TextView) findViewById(R.id.textView3);
        teste2 = (TextView) findViewById(R.id.testeID);

        soma=0;
        lenght_ofnormalizedata=0;


        cursor.moveToLast();
        tamanho = cursor.getPosition();

        if (tamanho<2){
            tamanho=2;
        }


        vetor = new double[tamanho];

        pos1= (tamanho-1)/4;
        pos2= (tamanho-1)/2;
        pos3= (3*(tamanho-1)/4);



        selectionSort(tamanho);
        outliers();
        crud.deletarregistro2();;
        funcaoAmodificado();

        cursor2=crud.carregaDados2();
        desviopadraoemedia();
        analise();



    }

    public void funcaoAmodificado () {
        int hora_int, minuto_int, dia_int;
        double mag_double_semnormalizar, mag_double;
        String magnetude_stri, hora_stri, minuto_stri, segundo_stri, dia_stri;
        int aux_min1, aux_min2, aux_min3, aux_min4, aux_min5, aux_min6, aux_min7, aux_min8, aux_min9;
        double aux_mag1, aux_mag2, aux_mag3, aux_mag4, aux_mag5, aux_mag6, aux_mag7, aux_mag8, aux_mag9;
        String finalString ="";


        aux_min1 = 0;
        aux_min2 = 0;
        aux_min3 = 0;
        aux_min4 = 0;
        aux_min5 = 0;
        aux_min6 = 0;
        aux_min7 = 0;
        aux_min8 = 0;
        aux_min9 = 0;

        aux_mag1 = 0;
        aux_mag2 = 0;
        aux_mag3 = 0;
        aux_mag4 = 0;
        aux_mag5 = 0;
        aux_mag6 = 0;
        aux_mag7 = 0;
        aux_mag8 = 0;
        aux_mag9 = 0;


        int ghj=0;
        cursor.moveToFirst();
        while(cursor.moveToNext()){



            magnetude_stri = cursor.getString(4);
            dia_stri = cursor.getString(0);
            hora_stri = cursor.getString(1);
            minuto_stri = cursor.getString(2);

            dia_int = Integer.parseInt(dia_stri);
            hora_int = Integer.parseInt(hora_stri);
            minuto_int = Integer.parseInt(minuto_stri);
            mag_double_semnormalizar = Double.parseDouble(magnetude_stri);




            if(mag_double_semnormalizar<lsup && mag_double_semnormalizar>linf){

            lenght_ofnormalizedata=lenght_ofnormalizedata+1;
            mag_double=normalize(mag_double_semnormalizar);

            aux_min9 = aux_min8;
            aux_min8 = aux_min7;
            aux_min7 = aux_min6;
            aux_min6 = aux_min5;
            aux_min5 = aux_min4;
            aux_min4 = aux_min3;
            aux_min3 = aux_min2;
            aux_min2 = aux_min1;
            aux_min1 = minuto_int;

            aux_mag9 = aux_mag8;
            aux_mag8 = aux_mag7;
            aux_mag7 = aux_mag6;
            aux_mag6 = aux_mag5;
            aux_mag5 = aux_mag4;
            aux_mag4 = aux_mag3;
            aux_mag3 = aux_mag2;
            aux_mag2 = aux_mag1;
            aux_mag1 = mag_double;




                if (aux_min9 != 0) {

                    double Amodificado;
                    String Amod_Strig;


                    Amodificado = aux_mag9 * 0.04 + aux_mag8 * 0.04 + aux_mag7 * 0.2 + aux_mag6 * 0.2 + aux_mag5 * 2 + aux_mag4 * 0.2 + aux_mag3 * 0.02 + aux_mag2 * 0.4 + aux_mag1 * 0.04;

                    Amod_Strig = Double.toString(Amodificado);
                    crud.insereDadoAmod(dia_stri, hora_stri, minuto_stri, cursor.getString(3), Amod_Strig);
                    finalString += "No Dia "+dia_int+" mag: "+ Amod_Strig+"% \n";

                }
            }


        }          //  teste2.setText(finalString);



    }

    public void desviopadraoemedia(){

        double aux,i, mag_double;
        String magnetude_stri;
        cursor.moveToFirst();
        i=0;
        ultimaposicao=0;
        soma=0;


        while (cursor.moveToNext()) {

            ultimaposicao=ultimaposicao+1;
            i=i+1;
            magnetude_stri = cursor.getString(4);
            mag_double = Double.parseDouble(magnetude_stri);
            Double j;


            if(mag_double<lsup && mag_double>linf) {
                j=normalize(mag_double);
                desvio = desvio + Math.sqrt((j - media) * (j - media)) / ultimaposicao;
                soma= soma+j;

            }media = soma/ultimaposicao;
        }       // teste1.setText("media"+media+" soma"+soma+" desvio"+desvio);


    }

    public void selectionSort(int tam) {



        int p=0;
        cursor.moveToFirst();
        while(cursor.moveToNext()) {


            String magnetude_stri = cursor.getString(4);
            Double mag_double = Double.parseDouble(magnetude_stri);

            vetor[p] = mag_double;
            p++;

        }

        int i, j, min;
        double aux;

        for (i=0; i<tam-1; i++) {
            min = i;
            for (j=i+1; j<tam; j++) {
                if (vetor[j] < vetor[min])
                {min = j;}
            }
            aux = vetor[i];
            vetor[i] = vetor[min];
            vetor[min] = aux;
        }


    }

    public void outliers(){
        double quartil1, quartil2, quartil3;
        double quar1, quar2, quar3, aux1, aux2, aux3, valorfinal1, valorfinal2, valorfinal3, IQR,tam;
        String magnetude_stri;
        selectionSort(tamanho);


        tam= tamanho;


        quartil1= Math.floor((tam-1)/4);
        quar1= (tam-1)/4;
        quar1= quar1-quartil1;

        quartil2= Math.floor((tam-1)/2);
        quar2= (tam-1)/2;
        quar2= quar2- quartil2;

        quartil3= Math.floor(3*(tam-1)/4);
        quar3= 3*(tam-1)/4;
        quar3= quar3-quartil3;


        if (quar1!=0){


            aux1 = vetor[pos1];
            aux2 = vetor[pos1+1];
            valorfinal1=aux1+ (aux2-aux1)*quar1;


        }else{

            aux1=vetor[pos1];
            valorfinal1 = aux1;


        }

                if (quar2!=0){
                    aux1= vetor[pos2] ;
                    aux2=vetor[pos2+1];
                    valorfinal2=aux1+ (aux2-aux1)*quar1;

                }else{
                    aux1=vetor[pos2];
                    valorfinal2 = aux1;
                }

                if (quar3!=0){
                    aux1= vetor[pos3] ;
                    aux2=vetor[pos3+1];
                    valorfinal3=aux1+ (aux2-aux1)*quar1;

                }else{
                    aux1=vetor[pos3];
                    valorfinal3 = aux1;
                }

        IQR = valorfinal3-valorfinal1;
        lsup = media+1.5*IQR;
        linf = media-1.5*IQR;

    }

    public double normalize(double x ) {
        double z;
        z=(x-linf)/(lsup-linf);
        return z;
    }

    public void analise(){

        int dia_int, dia_aux, rem, nonrem, primeiro_dia;
        double treshold, acurr;
        String magnetude_stri, dia_stri;
        String finalString ="";
        int conta_tam=0;
        rem=0;
        nonrem=0;
        double conclusao;


        int j=0;
        int tam;
        int i=0;
        int k=0;
        int count =0;

        tam= lenght_ofnormalizedata/4;
        State= new int[tam+1];
        diavetor = new int[tam+1];

        cursor2.moveToFirst();
        dia_stri = cursor2.getString(0);
        primeiro_dia = Integer.parseInt(dia_stri);
        dia_aux=primeiro_dia;

        while(cursor2.moveToNext()){

            treshold= (media+desvio)/2;

            dia_stri = cursor2.getString(0);
            dia_int = Integer.parseInt(dia_stri);

            magnetude_stri = cursor2.getString(4);
            acurr = Double.parseDouble(magnetude_stri);


                while (i<=3){
                    if(acurr<treshold){
                        count=count+1;
                    }
                    i=i+1;
                }


            if (count<0.4*4){
                rem=rem+1;

            }else{
                nonrem=nonrem+1;
            }

            conta_tam=conta_tam+1;


           // teste3.setText("rem"+rem+" nonrem"+nonrem+"count"+conta_tam);

             i=0;
             j=j+4;
             k=k+1;



           if (dia_int != dia_aux){

                conclusao=((rem*0.25+nonrem*0.75)*100);    //Baseado no http://healthysleep.med.harvard.edu/healthy/science/what/sleep-patterns-rem-nrem
                finalString += "No Dia "+ dia_int +" a qualidade do sono foi de: "+ conclusao+"% \n" ;

                rem=0;
                nonrem=0;
               conta_tam=0;

            }
            dia_aux=dia_int;

        }
       teste1.setText(finalString);




    }


}


