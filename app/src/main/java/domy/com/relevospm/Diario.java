package domy.com.relevospm;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Vector;

import domy.com.relevospm.Utiles.Dia4y2;
import domy.com.relevospm.Utiles.JSONParser3;
import domy.com.relevospm.Utiles.Utiles;
import domy.com.relevospm.Utiles.dia;

public class Diario {

    public static int GrupoLibra;

    public static Vector Diario(String dia365,String DNE, Context context) {

        Vector Vector_Final = null;

        JSONArray JsonTodo;

        Agente[] AgentesTrabajan = new Agente[0];

        String FECHA = dia365;

        int DIA365 = domy.com.relevospm.Utiles.dia.Fecha(FECHA);

        String F[] = FECHA.split("/");

        int dia = Integer.parseInt(F[0]);
        int mes = Integer.parseInt(F[1]);
        int anho = Integer.parseInt(F[2]);

        GrupoLibra = Dia4y2.GrupoLibra(dia, mes, anho);

        String ordenSQLTrabajan = "SELECT * FROM " + Utiles.periodo_Mes(F[0],F[1]) + " ORDER BY ESCALAFON ASC";

        ConsultaDiaSQL("UPDATE users SET inicios_widget = inicios_widget + 1 WHERE username = " + DNE);


        try {
            System.out.println("JsonTrabajan"+ ordenSQLTrabajan);
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        BD_Lineas_Fijas BD = new BD_Lineas_Fijas(context);

        BD.open();

        Vector Fijos = BD.getDatosDne(FECHA);

        BD.close(); // todo cierra base de datos estaba // 14/6/2016

        Vector resultado = Comparacion_Fijos_y_JsonServicioTrabajan(Fijos, AgentesTrabajan, DIA365);

        Vector_Final = resultado;

        return Vector_Final;
    }

    public static Vector Comparacion_Fijos_y_JsonServicioTrabajan(Vector Fijos, Agente[] agentesT, int dia365) {

        int m = 0;
        int t = 0;
        int n = 0;

        for (int x = 0; x < 33; x++) {

            Fijos.add("X");
        }

        for (int x = 0; x < agentesT.length; x++) {

            if (agentesT[x].getPUESTO().equals("C") && agentesT[x].getPOR() == 0
                    && agentesT[x].getGRUPO() != GrupoLibra
                    && ((dia.Fecha(agentesT[x].getVACACIONES_I()) == 0
                    && dia.Fecha(agentesT[x].getVACACIONES_T()) == 0)
                    || (dia.Fecha(agentesT[x].getVACACIONES_I()) <= dia365
                    && dia.Fecha(agentesT[x].getVACACIONES_T()) >= dia365))) {

                if (agentesT[x].getTURNO().equals("M")) {

                    Fijos.set(puestoCorre("M", m), agentesT[x].getDNE());
                    m++;
                }
                if (agentesT[x].getTURNO().equals("T")) {

                    Fijos.set(puestoCorre("T", t), agentesT[x].getDNE());
                    t++;
                }
                if (agentesT[x].getTURNO().equals("N")) {

                    Fijos.set(puestoCorre("N", n), agentesT[x].getDNE());
                    n++;
                }
            }
        }

        for (int x = 0; x < agentesT.length; x++) {

            if (agentesT[x].getGRUPO() != GrupoLibra) {
                if (agentesT[x].getPOR() != 0) {
                    if (Fijos.contains(agentesT[x].getPOR())) {
                        Fijos.set(Fijos.indexOf(agentesT[x].getPOR()), agentesT[x].getDNE());
                    } else {
                        //  Toast.makeText(getApplicationContext(), "FALLO : dne " + agentesT[x].getDNE() + " Por " + agentesT[x].getPOR(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        for (int x = 0; x < agentesT.length; x++) {
            if (agentesT[x].getCAMBIO_CON() != 0) {
                if (agentesT[x].getGRUPO() != GrupoLibra) {
                    if (dia.Fecha(agentesT[x].getVACACIONES_I()) <= dia365 && dia.Fecha(agentesT[x].getVACACIONES_T()) >= dia365) {
                        if (Fijos.contains(agentesT[x].getDNE())) {
                            Fijos.set(Fijos.indexOf(agentesT[x].getDNE()), agentesT[x].getCAMBIO_CON());
                        } else {
                            //   Toast.makeText(getApplicationContext(), "FALLO : dne " + agentesT[x].getDNE() + " Cambio Con " + agentesT[x].getCAMBIO_CON(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }


        Vector Vector_Final = Fijos;

        return Vector_Final;
    }

    public static int puestoCorre(String turno, int pp) {
        int posicion = 0;

        if (turno.equals("M")) {
            int[] p = {36, 39, 42, 45, 48, 51, 54, 57, 60};
            return p[pp];
        }
        if (turno.equals("T")) {
            int[] p = {37, 40, 43, 46, 49, 52, 55, 58, 61};
            return p[pp];
        }
        if (turno.equals("N")) {
            int[] p = {38, 41, 44, 47, 50, 53, 56, 59, 62};
            return p[pp];
        }

        return posicion;
    }

    public static JSONArray ConsultaDiaSQL(String ConSql) {

        JSONParser3 jsonParser3 = new JSONParser3();
        String URL = "http://domimtz.synology.me/bd/ConsultaSQL.php"; // todo: tengo que hacer el php

        ///  String  ConSql = "SELECT * FROM `a010_Enero`";

        HashMap<String, String> params = new HashMap<>();
        params.put("sql", ConSql);

        Log.d("request!", "starting");
        // getting product details by making HTTP request
        JSONArray jsonObjRecv = jsonParser3.makeHttpRequest(URL, "POST",
                params);
      //  Log.d("Sql exitoso!", jsonObjRecv.toString());


        return jsonObjRecv;
    }
}
