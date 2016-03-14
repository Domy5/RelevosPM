package domy.com.relevospm;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Vector;

import domy.com.relevospm.Utiles.Dia4y2;
import domy.com.relevospm.Utiles.JSONParser3;
import domy.com.relevospm.Utiles.dia;

public class Diariocopia {

    public static int GrupoLibra;

    public static Vector Diario(String dia365,Context context) {

        Vector Vector_Final = null;

        JSONArray JsonTodo;

        Agente[] AgentesTrabajan = new Agente[0];

        String FECHA = dia365;
        int DIA365 = dia.Fecha(FECHA);

        String F[] = FECHA.split("/");

        int dia = Integer.parseInt(F[0]);
        int mes = Integer.parseInt(F[1]);
        int anho = Integer.parseInt(F[2]);

        GrupoLibra = Dia4y2.GrupoLibra(dia, mes, anho);

        String a000_mes = null;

        if (mes == 1) a000_mes = "a010_Enero";
        else if (mes == 2) a000_mes = "a020_Febrero";
        else if (mes == 3) a000_mes = "a030_Marzo";
        else if (mes == 4) a000_mes = "a040_Abril";
        else if (mes == 5) a000_mes = "a050_Mayo";
        else if (mes == 6) a000_mes = "a060_Junio";
        else if (mes == 7 && dia < 23) a000_mes = "a071_V1";
        else if (mes == 7 && dia > 22) a000_mes = "a072_V2";
        else if (mes == 8 && dia < 14) a000_mes = "a072_V2";
        else if (mes == 8 && dia > 13) a000_mes = "a073_V3";
        else if (mes == 9 && dia < 5) a000_mes = "a073_V3";
        else if (mes == 9 && dia > 4 && dia < 27) a000_mes = "a074_V4";
        else if (mes == 9 && dia > 25) a000_mes = "a090_Septiembre";
        else if (mes == 10) a000_mes = "a100_Octubre";
        else if (mes == 11) a000_mes = "a110_Noviembre";
        else if (mes == 12) a000_mes = "a120_Diciembre";

        String ordenSQLTrabajan = " SELECT * FROM " + a000_mes + " ORDER BY ESCALAFON ASC";

        try {
            System.out.println("JsonTrabajan");
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

        BD.close();

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

            if (agentesT[x].getPUESTO().equals("C") && agentesT[x].getPOR() == 0 && agentesT[x].getGRUPO() != GrupoLibra
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
        Log.d("Sql exitoso!", jsonObjRecv.toString());

        return jsonObjRecv;
    }
}
