package domy.com.relevospm;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import domy.com.relevospm.Utiles.JSONParser2;
import domy.com.relevospm.Utiles.JSONParser3;
import domy.com.relevospm.login.Register;

public class Tabla_Diaria extends AppCompatActivity implements View.OnClickListener {


    private BD_Lineas_Fijas BD;

    public JSONArray JsonTodo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabla_diaria);

        //Creamos y abrimos la base de datos
        BD = new BD_Lineas_Fijas(this);
        BD.open();

        String GRUPOS = getIntent().getStringExtra("GRUPOS");
        Button boton = (Button) findViewById(R.id.x);


         try{

           JsonTodo = ConsultaDiaSQL("SELECT * FROM `a010_Enero`");

            // `id`, `N_MES`, `MES`, `PUESTO`, `ORDEN`, `ESCALAFON`, `DNE`, `NOMBRE`, `GRUPO`, `TURNO`, `VACACIONES_I`, `VACACIONES_T`, `VACACIONES`, `CAMBIO_CON`, `POR`, `COMPENSA`, `OBSERVACIONES`

             List<String> list = new ArrayList<>();
             for(int i = 0; i < JsonTodo.length(); i++){
                 list.add(JsonTodo.getJSONObject(i).getString("VACACIONES_I"));
             }

             for(int x=0;x<list.size();x++) {
                 System.out.println("domyyyyy : " + list.get(x));
             }

        } catch (JSONException e) {e.printStackTrace();}

      //  boton.setText("G " + GRUPOS);

        String FECHA = getIntent().getStringExtra("FECHA");
        TextView fecha = (TextView) findViewById(R.id.TextFecha);
        fecha.setText(FECHA);

        Button bm1 = (Button) findViewById(R.id.BotonMananaLinea1);
        Button bt1 = (Button) findViewById(R.id.BotonTardeLinea1);
        Button bn1 = (Button) findViewById(R.id.BotonNocheLinea1);

        Button bm2 = (Button) findViewById(R.id.BotonMananaLinea2);
        Button bt2 = (Button) findViewById(R.id.BotonTardeLinea2);
        Button bn2 = (Button) findViewById(R.id.BotonNocheLinea2);

        Button bm3 = (Button) findViewById(R.id.BotonMananaLinea3);
        Button bt3 = (Button) findViewById(R.id.BotonTardeLinea3);
        Button bn3 = (Button) findViewById(R.id.BotonNocheLinea3);

        Button bm4 = (Button) findViewById(R.id.BotonMananaLinea4);
        Button bt4 = (Button) findViewById(R.id.BotonTardeLinea4);
        Button bn4 = (Button) findViewById(R.id.BotonNocheLinea4);

        Button bm5 = (Button) findViewById(R.id.BotonMananaLinea5);
        Button bt5 = (Button) findViewById(R.id.BotonTardeLinea5);
        Button bn5 = (Button) findViewById(R.id.BotonNocheLinea5);

        Button bm6 = (Button) findViewById(R.id.BotonMananaLinea6);
        Button bt6 = (Button) findViewById(R.id.BotonTardeLinea6);
        Button bn6 = (Button) findViewById(R.id.BotonNocheLinea6);

        Button bm7 = (Button) findViewById(R.id.BotonMananaLinea7);
        Button bt7 = (Button) findViewById(R.id.BotonTardeLinea7);
        Button bn7 = (Button) findViewById(R.id.BotonNocheLinea7);

        Button bm8 = (Button) findViewById(R.id.BotonMananaLinea8);
        Button bt8 = (Button) findViewById(R.id.BotonTardeLinea8);
        Button bn8 = (Button) findViewById(R.id.BotonNocheLinea8);

        Button bm9 = (Button) findViewById(R.id.BotonMananaLinea9);
        Button bt9 = (Button) findViewById(R.id.BotonTardeLinea9);
        Button bn9 = (Button) findViewById(R.id.BotonNocheLinea9);

        Button bm10 = (Button) findViewById(R.id.BotonMananaLinea10);
        Button bt10 = (Button) findViewById(R.id.BotonTardeLinea10);
        Button bn10 = (Button) findViewById(R.id.BotonNocheLinea10);

        Button bm11 = (Button) findViewById(R.id.BotonMananaLinea11);
        Button bt11 = (Button) findViewById(R.id.BotonTardeLinea11);
        Button bn11 = (Button) findViewById(R.id.BotonNocheLinea11);

        Button bm12 = (Button) findViewById(R.id.BotonMananaLinea12);
        Button bt12 = (Button) findViewById(R.id.BotonTardeLinea12);
        Button bn12 = (Button) findViewById(R.id.BotonNocheLinea12);

        Button bmR1 = (Button) findViewById(R.id.BotonMananaRVA1);
        Button btR1 = (Button) findViewById(R.id.BotonTardeRVA1);
        Button bnR1 = (Button) findViewById(R.id.BotonNocheRVA1);

        Button bmR2 = (Button) findViewById(R.id.BotonMananaRVA2);
        Button btR2 = (Button) findViewById(R.id.BotonTardeRVA2);
        Button bnR2 = (Button) findViewById(R.id.BotonNocheRVA2);

        Button bmR3 = (Button) findViewById(R.id.BotonMananaRVA3);
        Button btR3 = (Button) findViewById(R.id.BotonTardeRVA3);
        Button bnR3 = (Button) findViewById(R.id.BotonNocheRVA3);

        Button bmR4 = (Button) findViewById(R.id.BotonMananaRVA4);
        Button btR4 = (Button) findViewById(R.id.BotonTardeRVA4);
        Button bnR4 = (Button) findViewById(R.id.BotonNocheRVA4);

        Button bmR5 = (Button) findViewById(R.id.BotonMananaRVA5);
        Button btR5 = (Button) findViewById(R.id.BotonTardeRVA5);
        Button bnR5 = (Button) findViewById(R.id.BotonNocheRVA5);

        Button bmR6 = (Button) findViewById(R.id.BotonMananaRVA6);
        Button btR6 = (Button) findViewById(R.id.BotonTardeRVA6);
        Button bnR6 = (Button) findViewById(R.id.BotonNocheRVA6);

        Button[] Botones = {
                bm1,bt1,bn1,bm2,bt2,bn2,bm3,bt3,bn3,bm4,bt4,bn4,bm5,bt5,bn5,bm6,bt6,bn6,bm7,bt7,bn7,
                bm8,bt8,bn8,bm9,bt9,bn9,bm10,bt10, bn10,bm11,bt11,bn11,bm12,bt12,bn12,bmR1,btR1,bnR1,
                bmR2,btR2,bnR2,bmR3,btR3,bnR3,bmR4,btR4,bnR4,bmR5,btR5,bnR5,bmR6,btR6, bnR6,

        };

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

        };

        Vector resultado = BD.getDatosDne(FECHA);

        for (int i = 0; i < TodosLosBotones.length; i++) {

            boton = (Button) findViewById(TodosLosBotones[i]);
            boton.setText(resultado.get(i).toString());
            boton.setOnClickListener(this);
        }

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
                Toast.makeText(getApplicationContext(), "naaa", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    public void BotonPasar(View view) {

        int[] TodosLosBotones = {
                R.id.BotonMananaLinea1, R.id.BotonMananaLinea2, R.id.BotonMananaLinea3, R.id.BotonMananaLinea4, R.id.BotonMananaLinea5,
                R.id.BotonMananaLinea6, R.id.BotonMananaLinea7, R.id.BotonMananaLinea8, R.id.BotonMananaLinea9, R.id.BotonMananaLinea10,
                R.id.BotonMananaLinea11, R.id.BotonMananaLinea12, R.id.BotonMananaRVA1, R.id.BotonMananaRVA2, R.id.BotonMananaRVA3,
                R.id.BotonMananaRVA4, R.id.BotonMananaRVA5, R.id.BotonMananaRVA6,

                R.id.BotonTardeLinea1, R.id.BotonTardeLinea2, R.id.BotonTardeLinea3, R.id.BotonTardeLinea4, R.id.BotonTardeLinea5,
                R.id.BotonTardeLinea6, R.id.BotonTardeLinea7, R.id.BotonTardeLinea8, R.id.BotonTardeLinea9, R.id.BotonTardeLinea10,
                R.id.BotonTardeLinea11, R.id.BotonTardeLinea12, R.id.BotonTardeRVA1, R.id.BotonTardeRVA2, R.id.BotonTardeRVA3,
                R.id.BotonTardeRVA4, R.id.BotonTardeRVA5, R.id.BotonTardeRVA6,

                R.id.BotonNocheLinea1, R.id.BotonNocheLinea2, R.id.BotonNocheLinea3, R.id.BotonNocheLinea4, R.id.BotonNocheLinea5,
                R.id.BotonNocheLinea6, R.id.BotonNocheLinea7, R.id.BotonNocheLinea8, R.id.BotonNocheLinea9, R.id.BotonNocheLinea10,
                R.id.BotonNocheLinea11, R.id.BotonNocheLinea12, R.id.BotonNocheRVA1, R.id.BotonNocheRVA2, R.id.BotonNocheRVA3,
                R.id.BotonNocheRVA4, R.id.BotonNocheRVA5, R.id.BotonNocheRVA6};


        for (int i = 0; i < TodosLosBotones.length; i++) {
            Button boton;
            boton = (Button) findViewById(TodosLosBotones[i]);
            boton.setText(DNE(boton.getText().toString()));
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        BD.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        BD.open();
    }

    static public String DNE(String Dne) {

        String Nombre = "";

        Properties prop = new Properties();
        try {
            prop.load(Main_Seleccionar_Dia.class.getClassLoader().getResourceAsStream(
                    "res/raw/datos.propi"));

            Nombre = prop.getProperty(Dne);

            //  System.out.println("Contenido del Dne: "+ Dne +" = "+ Nombre);


        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna propie");
            System.out.println("Contenido fallo");
        }
        return Nombre;
    }

   /* public void Phone() {
        //  <uses-permission android:name="android.permission.GET_ACCOUNTS" />

        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();

        ArrayList googleAccounts = new ArrayList();
        for (Account ac : accounts) {

            String acname = ac.name;
            String actype = ac.type;

            System.out.println("cuentas : " + acname + ", " + actype);
            if(actype.equals("com.whatsapp")){
                String phoneNumber = ac.name;
                System.out.println("Numero : " + phoneNumber);
            }
        }


    }   */

    public JSONArray ConsultaDiaSQL (String ConSql) {

         JSONParser3 jsonParser3 = new JSONParser3();
         String URL = "http://domimtz.synology.me/bd/ConsultaSQL.php"; // todo: tengo que hacer el php

        ///  String  ConSql = "SELECT * FROM `a010_Enero`";

                HashMap<String, String> params = new HashMap<>();
                params.put("sql", ConSql);

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONArray jsonObjRecv = jsonParser3.makeHttpRequest(URL, "POST",
                        params);
                Log.d("Sql exitoso!", jsonObjRecv.toString());

            return jsonObjRecv;
    }


}
