package domy.com.relevospm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import domy.com.relevospm.Utiles.AutoResizeTextView;
import domy.com.relevospm.Utiles.Dia4y2;
import domy.com.relevospm.Utiles.JSONParser3;
import domy.com.relevospm.Utiles.dia;

public class Tabla_Diaria2 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabla_diaria);

        Button boton ;

        String GRUPOS = getIntent().getStringExtra("GRUPOS");
        Button botonX = (Button) findViewById(R.id.x);
        botonX.setText("G " + GRUPOS);

        String FECHA = getIntent().getStringExtra("FECHA");
        TextView fecha = (TextView) findViewById(R.id.TextFecha);
        fecha.setText(FECHA);

        int[] TodosLosBotones = {

                R.id.BotonMananaLinea1, R.id.BotonTardeLinea1, R.id.BotonNocheLinea1,
                R.id.BotonMananaLinea2, R.id.BotonTardeLinea2, R.id.BotonNocheLinea2,
                R.id.BotonMananaLinea3, R.id.BotonTardeLinea3, R.id.BotonNocheLinea3,
                R.id.BotonMananaLinea4, R.id.BotonTardeLinea4, R.id.BotonNocheLinea4,
                R.id.BotonMananaLinea5, R.id.BotonTardeLinea5, R.id.BotonNocheLinea5,
                R.id.BotonMananaLinea6, R.id.BotonTardeLinea6, R.id.BotonNocheLinea6,
                R.id.BotonMananaLinea7, R.id.BotonTardeLinea7, R.id.BotonNocheLinea7,
                R.id.BotonMananaLinea8, R.id.BotonTardeLinea8, R.id.BotonNocheLinea8,
                R.id.BotonMananaLinea9, R.id.BotonTardeLinea9, R.id.BotonNocheLinea9,
                R.id.BotonMananaLinea10, R.id.BotonTardeLinea10, R.id.BotonNocheLinea10,
                R.id.BotonMananaLinea11, R.id.BotonTardeLinea11, R.id.BotonNocheLinea11,
                R.id.BotonMananaLinea12, R.id.BotonTardeLinea12, R.id.BotonNocheLinea12,
                R.id.BotonMananaRVA1, R.id.BotonTardeRVA1, R.id.BotonNocheRVA1,
                R.id.BotonMananaRVA2, R.id.BotonTardeRVA2, R.id.BotonNocheRVA2,
                R.id.BotonMananaRVA3, R.id.BotonTardeRVA3, R.id.BotonNocheRVA3,
                R.id.BotonMananaRVA4, R.id.BotonTardeRVA4, R.id.BotonNocheRVA4,
                R.id.BotonMananaRVA5, R.id.BotonTardeRVA5, R.id.BotonNocheRVA5,
                R.id.BotonMananaRVA6, R.id.BotonTardeRVA6, R.id.BotonNocheRVA6,
                R.id.BotonMananaRVA7, R.id.BotonTardeRVA7, R.id.BotonNocheRVA7,
                R.id.BotonMananaRVA8, R.id.BotonTardeRVA8, R.id.BotonNocheRVA8,
                R.id.BotonMananaRVA9, R.id.BotonTardeRVA9, R.id.BotonNocheRVA9,
        };

        Vector resultado =  Diario.Diario(FECHA,getApplicationContext());

        for (int i = 0; i < TodosLosBotones.length; i++) {

            boton = (Button) findViewById(TodosLosBotones[i]);
            boton.setText(resultado.get(i).toString());
            // boton.setText(Fijos.get(i).toString());
            boton.setOnClickListener(this);
        }

        botonX.performClick();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.x:
                Toast.makeText(getApplicationContext(), "Botón X", Toast.LENGTH_SHORT).show();
                break;
            case R.id.BotonMananaLinea1:
                Button bm = (Button) findViewById(R.id.BotonMananaLinea1);

                Toast.makeText(getApplicationContext(), bm.getText() , Toast.LENGTH_SHORT).show();

                break;
            default:
                Toast.makeText(getApplicationContext(), ".", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    public void BotonPasar(View view) {

        int[] TodosLosBotones = {
                R.id.BotonMananaLinea1, R.id.BotonMananaLinea2, R.id.BotonMananaLinea3, R.id.BotonMananaLinea4, R.id.BotonMananaLinea5,
                R.id.BotonMananaLinea6, R.id.BotonMananaLinea7, R.id.BotonMananaLinea8, R.id.BotonMananaLinea9, R.id.BotonMananaLinea10,
                R.id.BotonMananaLinea11, R.id.BotonMananaLinea12, R.id.BotonMananaRVA1, R.id.BotonMananaRVA2, R.id.BotonMananaRVA3,
                R.id.BotonMananaRVA4, R.id.BotonMananaRVA5, R.id.BotonMananaRVA6,R.id.BotonMananaRVA7,R.id.BotonMananaRVA8,
                R.id.BotonMananaRVA9,

                R.id.BotonTardeLinea1, R.id.BotonTardeLinea2, R.id.BotonTardeLinea3, R.id.BotonTardeLinea4, R.id.BotonTardeLinea5,
                R.id.BotonTardeLinea6, R.id.BotonTardeLinea7, R.id.BotonTardeLinea8, R.id.BotonTardeLinea9, R.id.BotonTardeLinea10,
                R.id.BotonTardeLinea11, R.id.BotonTardeLinea12, R.id.BotonTardeRVA1, R.id.BotonTardeRVA2, R.id.BotonTardeRVA3,
                R.id.BotonTardeRVA4, R.id.BotonTardeRVA5, R.id.BotonTardeRVA6,R.id.BotonTardeRVA7, R.id.BotonTardeRVA8,
                R.id.BotonTardeRVA9,

                R.id.BotonNocheLinea1, R.id.BotonNocheLinea2, R.id.BotonNocheLinea3, R.id.BotonNocheLinea4, R.id.BotonNocheLinea5,
                R.id.BotonNocheLinea6, R.id.BotonNocheLinea7, R.id.BotonNocheLinea8, R.id.BotonNocheLinea9, R.id.BotonNocheLinea10,
                R.id.BotonNocheLinea11, R.id.BotonNocheLinea12, R.id.BotonNocheRVA1, R.id.BotonNocheRVA2, R.id.BotonNocheRVA3,
                R.id.BotonNocheRVA4, R.id.BotonNocheRVA5, R.id.BotonNocheRVA6, R.id.BotonNocheRVA7, R.id.BotonNocheRVA8,
                R.id.BotonNocheRVA9};


        for (int i = 0; i < TodosLosBotones.length; i++) {

            AutoResizeTextView boton = (AutoResizeTextView ) findViewById(TodosLosBotones[i]);
            boton.setText(DNE(boton.getText().toString()));

        }


    }

    static public String DNE(String Dne) {

        String Nombre = "";

        Properties prop = new Properties();
        try {
            prop.load(Main_Seleccionar_Dia.class.getClassLoader().getResourceAsStream(
                    "res/raw/datos.propi"));//todo : este archivo tenqo que ponerlo en la raiz de la sd ahora esta en raw

            Nombre = prop.getProperty(Dne);

            //  System.out.println("Contenido del Dne: "+ Dne +" = "+ Nombre);


        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna propie");
            System.out.println("Contenido fallo");
        }
        return Nombre;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

}

