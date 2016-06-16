package domy.com.relevospm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import domy.com.relevospm.Utiles.UpdateApp;
import domy.com.relevospm.Utiles.Utiles;
import domy.com.relevospm.Utiles.dia;
import domy.com.relevospm.login.Login;

public class Tabla_Diaria2 extends AppCompatActivity implements View.OnClickListener {

    String GRUPOS;
    String FECHA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabla_diaria);

        Button boton;

        GRUPOS = getIntent().getStringExtra("GRUPOS");
        Button botonX = (Button) findViewById(R.id.x);
        botonX.setText("G " + GRUPOS);

        FECHA = getIntent().getStringExtra("FECHA");
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

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        String dne = prefs.getString("Dne", ":");

        Vector resultado = Diario.Diario(FECHA, dne, getApplicationContext());

        if (resultado.size() != 0) {

            for (int i = 0; i < TodosLosBotones.length; i++) {

                boton = (Button) findViewById(TodosLosBotones[i]);
                boton.setText(resultado.get(i).toString());
                // boton.setText(Fijos.get(i).toString());
                boton.setOnClickListener(this);
            }
        }

        botonX.performClick();

    }

    @Override
    public void onClick(View v) {

        Button b = (Button) v;

        FragmentManager fragmentManager = getSupportFragmentManager();

        DialogoAgente dialogoAgente = new DialogoAgente();
        // dialogoAgente.setAgente(b.getText().toString(),FECHA);
        // dialogoAgente.show(fragmentManager,"hola");


        DialogoAgenteAlertDialogBuilder dialogo = new DialogoAgenteAlertDialogBuilder();
        dialogo.setAgente(b.getText().toString(), FECHA);
        dialogo.show(fragmentManager, "tagPersonalizado");


        //  b.setText(".");

        Toast.makeText(getApplicationContext(), b.getText().toString(), Toast.LENGTH_SHORT).show();

      /*  switch (v.getId()) {

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
        }*/
    }

    public void BotonPasar(View view) {

        int[] TodosLosBotones = {
                R.id.BotonMananaLinea1, R.id.BotonMananaLinea2, R.id.BotonMananaLinea3, R.id.BotonMananaLinea4, R.id.BotonMananaLinea5,
                R.id.BotonMananaLinea6, R.id.BotonMananaLinea7, R.id.BotonMananaLinea8, R.id.BotonMananaLinea9, R.id.BotonMananaLinea10,
                R.id.BotonMananaLinea11, R.id.BotonMananaLinea12, R.id.BotonMananaRVA1, R.id.BotonMananaRVA2, R.id.BotonMananaRVA3,
                R.id.BotonMananaRVA4, R.id.BotonMananaRVA5, R.id.BotonMananaRVA6, R.id.BotonMananaRVA7, R.id.BotonMananaRVA8,
                R.id.BotonMananaRVA9,

                R.id.BotonTardeLinea1, R.id.BotonTardeLinea2, R.id.BotonTardeLinea3, R.id.BotonTardeLinea4, R.id.BotonTardeLinea5,
                R.id.BotonTardeLinea6, R.id.BotonTardeLinea7, R.id.BotonTardeLinea8, R.id.BotonTardeLinea9, R.id.BotonTardeLinea10,
                R.id.BotonTardeLinea11, R.id.BotonTardeLinea12, R.id.BotonTardeRVA1, R.id.BotonTardeRVA2, R.id.BotonTardeRVA3,
                R.id.BotonTardeRVA4, R.id.BotonTardeRVA5, R.id.BotonTardeRVA6, R.id.BotonTardeRVA7, R.id.BotonTardeRVA8,
                R.id.BotonTardeRVA9,

                R.id.BotonNocheLinea1, R.id.BotonNocheLinea2, R.id.BotonNocheLinea3, R.id.BotonNocheLinea4, R.id.BotonNocheLinea5,
                R.id.BotonNocheLinea6, R.id.BotonNocheLinea7, R.id.BotonNocheLinea8, R.id.BotonNocheLinea9, R.id.BotonNocheLinea10,
                R.id.BotonNocheLinea11, R.id.BotonNocheLinea12, R.id.BotonNocheRVA1, R.id.BotonNocheRVA2, R.id.BotonNocheRVA3,
                R.id.BotonNocheRVA4, R.id.BotonNocheRVA5, R.id.BotonNocheRVA6, R.id.BotonNocheRVA7, R.id.BotonNocheRVA8,
                R.id.BotonNocheRVA9};


        for (int i = 0; i < TodosLosBotones.length; i++) {

            AutoResizeTextView boton = (AutoResizeTextView) findViewById(TodosLosBotones[i]);
            boton.setText(DNE(boton.getText().toString()));

        }


    }

    static public String DNE(String Dne) {

        String Nombre = "";

        Properties prop = new Properties();
        try {
            prop.load(MainActivity.class.getClassLoader().getResourceAsStream(
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_opciones) {

            Intent i = new Intent(this, Login.class);
            i.putExtra("AutoLogin", false);
            startActivity(i);

            return true;
        }

        if (id == R.id.action_Actualizar) {

            Utiles.isStoragePermissionGranted(getApplicationContext(),this);

            UpdateApp atualizaApp = new UpdateApp();
            atualizaApp.setContext(getApplicationContext());
            atualizaApp.execute("http://domy.asuscomm.com/app-debug.apk");

            return true;
        }

        if (id == R.id.action_Infor) {

            Intent i = new Intent(this, Dialog_Info.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.action_Actualizar_Beta) {

            Utiles.isStoragePermissionGranted(getApplicationContext(),this);

            UpdateApp atualizaAppBeta = new UpdateApp();
            atualizaAppBeta.setContext(getApplicationContext());
            atualizaAppBeta.execute("http://domy.asuscomm.com/beta.apk");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }


}

