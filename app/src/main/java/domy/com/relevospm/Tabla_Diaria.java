package domy.com.relevospm;

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

import domy.com.relevospm.Utiles.Dia4y2;
import domy.com.relevospm.Utiles.JSONParser3;

public class Tabla_Diaria extends AppCompatActivity implements View.OnClickListener {


    private BD_Lineas_Fijas BD;

    public JSONArray JsonTodo ;
    public JSONArray JsonVacaciones ;
    public JSONArray JsonCorre ;

    public Agente[] AgentesTrabajan;
    public Agente[] AgentesVacaciones;
    public Agente[] AgentesCorre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabla_diaria);

        //Creamos y abrimos la base de datos
        BD = new BD_Lineas_Fijas(this);
        BD.open();

        String GRUPOS = getIntent().getStringExtra("GRUPOS");
        Button boton = (Button) findViewById(R.id.x);

        String FECHA = getIntent().getStringExtra("FECHA");
        TextView fecha = (TextView) findViewById(R.id.TextFecha);
        fecha.setText(FECHA);

        String F[] = FECHA.split("/");

        int dia = Integer.parseInt(F[0]) ;
        int mes = Integer.parseInt(F[1]) ;
        int anho = Integer.parseInt(F[2]) ;

        int GrupoLibra = Dia4y2.GrupoLibra(dia, mes, anho)  ;

        String a000_mes = null;

        if (mes == 1) a000_mes =  "a010_Enero";else
        if (mes == 2) a000_mes =  "a020_Febrero";else
        if (mes == 3) a000_mes =  "a030_Marzo";else
        if (mes == 4) a000_mes =  "a040_Abril";else
        if (mes == 5) a000_mes =  "a050_Mayo";else
        if (mes == 6) a000_mes =  "a060_Junio";else
        if (mes == 7 &&  dia < 23)	a000_mes =  "a071_V1";else
        if (mes == 7 &&  dia > 22)	a000_mes =  "a072_V2";else
        if (mes == 8 &&  dia < 14) a000_mes =  "a072_V2";else
        if (mes == 8 &&  dia > 13) a000_mes =  "a073_V3";else
        if (mes == 9 &&  dia <  5) a000_mes =  "a073_V3";else
        if (mes == 9 &&  dia >  4 &&  dia < 27) a000_mes =  "a074_V4";else
        if (mes == 9 &&  dia >  25) a000_mes = "a090_Septiembre";else
        if (mes == 10) a000_mes = "a100_Octubre";else
        if (mes == 11) a000_mes = "a110_Noviembre";else
        if (mes == 12) a000_mes = "a120_Diciembre";

        String ordenSQLTrabajan = " SELECT * " +
                " FROM "+ a000_mes +" WHERE GRUPO != " + GrupoLibra +
                " AND (VACACIONES_I IS NULL " +
                " OR (VACACIONES_I > DATE('" + anho +"-"+ mes +"-"+ dia +"') " +
                " OR  VACACIONES_T < DATE('" + anho +"-"+ mes +"-"+ dia +"')))ORDER BY ESCALAFON ASC";

        String ordenSQLVacaciones = " SELECT * FROM " + a000_mes +" WHERE  GRUPO != " + GrupoLibra +" AND"+
                //	" (VACACIONES_I IS NOT NULL " +
                " VACACIONES_I < DATE('" + anho +"-"+ mes +"-"+ dia +"') " +
                " AND  VACACIONES_T > DATE('" + anho +"-"+ mes +"-"+ dia +"')" +
                " OR VACACIONES_I = DATE('" + anho +"-"+ mes +"-"+ dia +"')" +
                " OR VACACIONES_T = DATE('" + anho +"-"+ mes +"-"+ dia +"')ORDER BY ESCALAFON ASC";

        // String ordenSQLCorres =

        // SELECT * FROM a010_Enero WHERE (GRUPO != 5 AND GRUPO != 0) AND PUESTO = "C" AND POR = 0 AND (VACACIONES_I IS NULL OR (VACACIONES_I > DATE('2016-1-5') OR VACACIONES_T < DATE('2016-1-5')))ORDER BY ESCALAFON ASC

        String ordenSQLCorres =" SELECT * FROM " + a000_mes + " WHERE (GRUPO != "+ GrupoLibra +" AND GRUPO != 0) AND PUESTO = \"C\" AND POR = 0 ORDER BY ESCALAFON ASC";

        System.out.print(" orden :::"+ ordenSQLCorres);

        try {

            JsonTodo = ConsultaDiaSQL(ordenSQLTrabajan);

            AgentesTrabajan = new Agente[JsonTodo.length()];

             for (int x = 0; x < JsonTodo.length(); x++) {

                  AgentesTrabajan[x] = new Agente(
                         Integer.parseInt(JsonTodo.getJSONObject(x).getString("id")),
                         Integer.parseInt(JsonTodo.getJSONObject(x).getString("N_MES")),
                         JsonTodo.getJSONObject(x).getString("MES"),
                         JsonTodo.getJSONObject(x).getString("PUESTO"),
                         Integer.parseInt(JsonTodo.getJSONObject(x).getString("ORDEN")),
                         Integer.parseInt(JsonTodo.getJSONObject(x).getString("ESCALAFON")),
                         Integer.parseInt(JsonTodo.getJSONObject(x).getString("DNE")),
                         JsonTodo.getJSONObject(x).getString("NOMBRE"),
                         Integer.parseInt(JsonTodo.getJSONObject(x).getString("GRUPO")),
                         JsonTodo.getJSONObject(x).getString("TURNO"),
                         JsonTodo.getJSONObject(x).getString("VACACIONES_I"),
                         JsonTodo.getJSONObject(x).getString("VACACIONES_T"),
                         JsonTodo.getJSONObject(x).getString("VACACIONES"),
                         Integer.parseInt(JsonTodo.getJSONObject(x).getString("CAMBIO_CON")),
                         Integer.parseInt(JsonTodo.getJSONObject(x).getString("POR")),
                         JsonTodo.getJSONObject(x).getString("COMPENSA"),
                         JsonTodo.getJSONObject(x).getString("OBSERVACIONES"));

             }

  } catch (JSONException e) {e.printStackTrace();}

        try {

            JsonVacaciones = ConsultaDiaSQL(ordenSQLVacaciones);

            AgentesVacaciones = new Agente[JsonVacaciones.length()];

            for (int x = 0; x < JsonVacaciones.length(); x++) {

                AgentesVacaciones[x] = new Agente(
                        Integer.parseInt(JsonVacaciones.getJSONObject(x).getString("id")),
                        Integer.parseInt(JsonVacaciones.getJSONObject(x).getString("N_MES")),
                        JsonVacaciones.getJSONObject(x).getString("MES"),
                        JsonVacaciones.getJSONObject(x).getString("PUESTO"),
                        Integer.parseInt(JsonVacaciones.getJSONObject(x).getString("ORDEN")),
                        Integer.parseInt(JsonVacaciones.getJSONObject(x).getString("ESCALAFON")),
                        Integer.parseInt(JsonVacaciones.getJSONObject(x).getString("DNE")),
                        JsonVacaciones.getJSONObject(x).getString("NOMBRE"),
                        Integer.parseInt(JsonVacaciones.getJSONObject(x).getString("GRUPO")),
                        JsonVacaciones.getJSONObject(x).getString("TURNO"),
                        JsonVacaciones.getJSONObject(x).getString("VACACIONES_I"),
                        JsonVacaciones.getJSONObject(x).getString("VACACIONES_T"),
                        JsonVacaciones.getJSONObject(x).getString("VACACIONES"),
                        Integer.parseInt(JsonVacaciones.getJSONObject(x).getString("CAMBIO_CON")),
                        Integer.parseInt(JsonVacaciones.getJSONObject(x).getString("POR")),
                        JsonVacaciones.getJSONObject(x).getString("COMPENSA"),
                        JsonVacaciones.getJSONObject(x).getString("OBSERVACIONES"));

            }

        } catch (JSONException e) {e.printStackTrace();}

        try {

            JsonCorre = ConsultaDiaSQL(ordenSQLCorres);

            AgentesCorre = new Agente[JsonCorre.length()];

            for (int x = 0; x < JsonCorre.length(); x++) {

                AgentesCorre[x] = new Agente(
                        Integer.parseInt(JsonCorre.getJSONObject(x).getString("id")),
                        Integer.parseInt(JsonCorre.getJSONObject(x).getString("N_MES")),
                        JsonCorre.getJSONObject(x).getString("MES"),
                        JsonCorre.getJSONObject(x).getString("PUESTO"),
                        Integer.parseInt(JsonCorre.getJSONObject(x).getString("ORDEN")),
                        Integer.parseInt(JsonCorre.getJSONObject(x).getString("ESCALAFON")),
                        Integer.parseInt(JsonCorre.getJSONObject(x).getString("DNE")),
                        JsonCorre.getJSONObject(x).getString("NOMBRE"),
                        Integer.parseInt(JsonCorre.getJSONObject(x).getString("GRUPO")),
                        JsonCorre.getJSONObject(x).getString("TURNO"),
                        JsonCorre.getJSONObject(x).getString("VACACIONES_I"),
                        JsonCorre.getJSONObject(x).getString("VACACIONES_T"),
                        JsonCorre.getJSONObject(x).getString("VACACIONES"),
                        Integer.parseInt(JsonCorre.getJSONObject(x).getString("CAMBIO_CON")),
                        Integer.parseInt(JsonCorre.getJSONObject(x).getString("POR")),
                        JsonCorre.getJSONObject(x).getString("COMPENSA"),
                        JsonCorre.getJSONObject(x).getString("OBSERVACIONES"));

            }

        } catch (JSONException e) {e.printStackTrace();}


        boton.setText("G " + GRUPOS);

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

        Vector Fijos = BD.getDatosDne(FECHA);

        Vector resultado = Comparacion_Fijos_y_JsonServicioTrabajan(Fijos, AgentesTrabajan, AgentesVacaciones, AgentesCorre) ;

        for (int i = 0; i < TodosLosBotones.length; i++) {

            boton = (Button) findViewById(TodosLosBotones[i]);
            boton.setText(resultado.get(i).toString());
            // boton.setText(Fijos.get(i).toString());
            boton.setOnClickListener(this);
        }

    }

    private Vector Comparacion_Fijos_y_JsonServicioTrabajan(Vector Fijos, Agente[] agentesT, Agente[] agentesV, Agente[] agentesC) {

        int m = 0;
        int t = 0;
        int n = 0;

        for (int x = 0; x < 33; x++) {

            Fijos.add("X");
        }


        for (int x = 0; x < agentesC.length; x++) {

            if (agentesC[x].getTURNO().equals("M") ) {

                Fijos.set(puestoCorre("M",m) ,agentesC[x].getDNE());
                 m++;
            }
            if (agentesC[x].getTURNO().equals("T") ) {

                Fijos.set(puestoCorre("T",t) ,agentesC[x].getDNE());
                t++;
            }
            if (agentesC[x].getTURNO().equals("N") ) {

                Fijos.set(puestoCorre("N",n) ,agentesC[x].getDNE());
                n++;
            }

        }


        for (int x = 0; x < agentesT.length; x++) {

            if (agentesT[x].getPOR() != 0) {

               // System.out.println("Quien : " + agentesT[x].getDNE() + " POR : " + agentesT[x].getPOR());

                Fijos.set(Fijos.indexOf(agentesT[x].getPOR()), agentesT[x].getDNE());
            }
        }

///////

        for (int y = 0; y < agentesV.length; y++) {

                if(agentesV[y].getCAMBIO_CON() != 0 ){

                  //  System.out.println("Quien : " + agentesV[y].getDNE() + " Cambio Con : " + agentesV[y].getCAMBIO_CON());
                    //public E set(int index, E element)

                    Fijos.set(Fijos.indexOf(agentesV[y].getDNE()),agentesV[y].getCAMBIO_CON( ));

                }

            }

        Vector Vector_Final = Fijos;

        return Vector_Final;
    }

    private int puestoCorre(String turno, int pp) {
        int posicion= 0;

        if (turno.equals("M")){
            int [] p = {36,39,42,45,48,51,54,57,60};
            return p[pp];
        }
        if (turno.equals("T")){
            int [] p = {37,40,43,46,49,52,55,58,61};
            return p[pp];
        }
        if (turno.equals("N")){
            int [] p = {38,41,44,47,50,53,56,59,62};
            return p[pp];
        }

        return posicion;
    }

    //metodo para crear un toast con informacion de cada voto quiero meter si es por vacaciones o por cambio

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

    // este metodo vale para pasar los dne a nombre y viceversa

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
            Button boton;
            boton = (Button) findViewById(TodosLosBotones[i]);
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

    @Override
    public void onResume() {
        super.onResume();
        BD.open();
    }
    @Override
    public void onPause() {
        super.onPause();
        BD.close();
    }
}


/*
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
 */