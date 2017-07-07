package com.altice.manuel.tupesoideal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{


    LinearLayout resultados;
    Button botonResultados;
    boolean masaEnLibras; //False significa kgs y true significa libras
    boolean estaturaEnPies; //False significa kms y True significa pies, pulgs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        masaEnLibras = false;
        estaturaEnPies = false;
        //Se obtiene el LinearLayout que mostrará los resultados
        resultados = (LinearLayout) findViewById(R.id.resultados);
        //Dichos resultados se hacen invisibles
        resultados.setVisibility(View.GONE);
        //Se obtiene el obteto del boton  que se presionara para obtener los resultados
        botonResultados = (Button) findViewById(R.id.boton_calcular);
        //Se le agrega on OnClickListener para presentar los resultados en pantalla
        Spinner spinnerMasa = (Spinner) findViewById(R.id.spinner_masa);
        Spinner spinnerEstatura = (Spinner) findViewById(R.id.spinner_estatura);
        spinnerMasa.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                masaEnLibras = position == 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEstatura.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    findViewById(R.id.estatura_pies_entry).setVisibility(View.VISIBLE);
                    findViewById(R.id.estatura_pulgs_entry).setVisibility(View.VISIBLE);
                    findViewById(R.id.estatura_mts_entry).setVisibility(View.GONE);
                    estaturaEnPies=true;
                }else{
                    findViewById(R.id.estatura_pies_entry).setVisibility(View.GONE);
                    findViewById(R.id.estatura_pulgs_entry).setVisibility(View.GONE);
                    findViewById(R.id.estatura_mts_entry).setVisibility(View.VISIBLE);
                    estaturaEnPies=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botonResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se esconde el teclado una clickeado el botón
                hideKeyboard(MainActivity.this);
                resultados.setVisibility(View.GONE);
                double estatura;
                //Se toman los datos de estatura ingresados por el usuario
                try {
                    if(estaturaEnPies){
                        EditText estaturaPiesEntry = (EditText) findViewById(R.id.estatura_pies_entry);
                        EditText estaturaPulgsEntry = (EditText) findViewById(R.id.estatura_pulgs_entry);
                        int pies = Integer.parseInt(estaturaPiesEntry.getText().toString());
                        int pulgs = Integer.parseInt(estaturaPulgsEntry.getText().toString());

                        estatura = (pies*12)+pulgs;
                        estatura = estatura*0.0254;

                    }else{
                        EditText estaturaEntry = (EditText) findViewById(R.id.estatura_mts_entry);
                        estatura = Double.parseDouble(estaturaEntry.getText().toString());
                    }
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Debes ingresar tu estatura",Toast.LENGTH_SHORT).show();
                    return;
                }



                //Se toman los datos de masa ingresados por el usuario
                EditText masaEntry = (EditText) findViewById(R.id.masa_entry);
                double masa;
                try{
                    masa = Double.parseDouble(masaEntry.getText().toString());
                }catch (Exception ex){
                    masa = 0;
                }
                if(masaEnLibras){
                    masa=masa*0.4536;
                }

                //Se calcula el índice de masa corporal
                double imc = masa/(estatura*estatura);
                //Se escribe el resultado en el View correspondiente
                TextView imcTextView = (TextView) findViewById(R.id.resultado_imc);
                imcTextView.setText(String.valueOf(imc));
                //Se asigna la condicion correspondiente
                TextView condicionTextView = (TextView) findViewById(R.id.resultado_condicion);
                if(imc==0){
                    condicionTextView.setText(R.string.condicion_indefinido);
                } else if(imc<18){
                    condicionTextView.setText(R.string.condicion_bajo_peso);
                }else if(imc<25) {
                    condicionTextView.setText(R.string.condicion_normal);
                }else if(imc<27){
                    condicionTextView.setText(R.string.condicion_obesidad);
                }else if(imc<30){
                    condicionTextView.setText(R.string.condicion_obesidad_grado_1);
                }else if(imc<30){
                    condicionTextView.setText(R.string.condicion_obesidad_grado_2);
                }else{
                    condicionTextView.setText(R.string.condicion_obesidad_grado_3);
                }
                resultados.setVisibility(View.VISIBLE);
                double minimoRecomendado = 18*estatura*estatura;
                double maximoRecomendado = 24.9*estatura*estatura;
                String unidad = "kgs";
                if(masaEnLibras){
                    minimoRecomendado=minimoRecomendado*2.2;
                    maximoRecomendado=maximoRecomendado*2.2;
                    unidad = "lbs";
                }
                TextView pesoRecomendado = (TextView) findViewById(R.id.resultado_peso_recomendado);
                pesoRecomendado.setText("Entre "+String.valueOf(minimoRecomendado).substring(0,7)+" "+unidad+" y "+String.valueOf(maximoRecomendado).substring(0,7)+" "+unidad);
            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
