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

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity{


    LinearLayout resultados;
    Button botonResultados;
    boolean masaEnLibras; //False significa kgs y true significa libras
    boolean estaturaEnPies; //False significa kms y True significa pies, pulgs
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = new Usuario();
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
                //Se esconden las vistas de libras o de kilogramos
                estaturaEnPies = position == 1;
                esconderVistas(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        botonResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentarResultados();
            }
        });
    }

    //Esta funcion sirve para esconder el teclado una vez presionado el boton
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

    //Esta funcion sirve para esconder los EditText no correspondientes
    private void esconderVistas(int position){
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


    private double calcularEstatura(){
        double estatura;
        if(estaturaEnPies){
            EditText estaturaPiesEntry = (EditText) findViewById(R.id.estatura_pies_entry);
            EditText estaturaPulgsEntry = (EditText) findViewById(R.id.estatura_pulgs_entry);
            int pies = Integer.parseInt(estaturaPiesEntry.getText().toString());
            int pulgs = Integer.parseInt(estaturaPulgsEntry.getText().toString());

            estatura = (pies*12)+pulgs;

        }else{
            EditText estaturaEntry = (EditText) findViewById(R.id.estatura_mts_entry);
            estatura = Double.parseDouble(estaturaEntry.getText().toString());
        }
        return estatura;
    }

    private void presentarResultados() {
        //Se esconde el teclado una clickeado el botón
        hideKeyboard(MainActivity.this);

        double estatura;
        //Se toman los datos de estatura ingresados por el usuario


        try {
            //EditText estaturaEntry = (EditText) findViewById(R.id.estatura_mts_entry);
            estatura = calcularEstatura();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Debes ingresar tu estatura", Toast.LENGTH_SHORT).show();
            resultados.setVisibility(View.GONE);
            return;
        }
        EditText masaEntry = (EditText) findViewById(R.id.masa_entry);
        double masa;
        try {
            masa = Double.parseDouble(masaEntry.getText().toString());
        } catch (Exception ex) {
            masa = 0;
        }
        usuario.calcularResultados(masa, estatura, masaEnLibras, estaturaEnPies);
        //Se escribe el resultado en el View correspondiente
        TextView imcTextView = (TextView) findViewById(R.id.resultado_imc);
        NumberFormat df = DecimalFormat.getInstance();
        df.setMaximumFractionDigits(4);
        imcTextView.setText(df.format(usuario.getImc()));
        //Se asigna la condicion correspondiente
        TextView condicionTextView = (TextView) findViewById(R.id.resultado_condicion);
        TextView consejoTextView = (TextView) findViewById(R.id.resultado_consejo);
        condicionTextView.setText(usuario.getCondicion());
        consejoTextView.setText(usuario.getConsejo());
        TextView pesoRecomendado = (TextView) findViewById(R.id.resultado_peso_recomendado);

        pesoRecomendado.setText("Entre " + df.format(usuario.getMinimoRecomendado()) + " " + usuario.getUnidad() + " y " + df.format(usuario.getMaximoRecomendado()) + " " + usuario.getUnidad());
        resultados.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this, String.valueOf(masaEnLibras), Toast.LENGTH_SHORT).show();
    }
}
