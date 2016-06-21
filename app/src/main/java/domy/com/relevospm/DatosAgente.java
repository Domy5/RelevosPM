package domy.com.relevospm;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import domy.com.relevospm.Utiles.Utiles;


public class DatosAgente  {

    String Dne;
    String Fecha;
    JSONArray JsonTodo;
    Agente agente;

    public void setAgente(String Dne, String Fecha) {
        this.Dne = Dne;
        this.Fecha = Fecha;
    }

    public Agente DatosAgente (){

        String DNE = Dne ;

        String F[] = Fecha.split("/");

        String ordenSQL = "SELECT * FROM " + Utiles.periodo_Mes(F[0],F[1]) + "  WHERE `DNE` = " + DNE;
        //   String ordenSQL = "SELECT * FROM " + a000_mes + "  WHERE `DNE` = " + "15910";

          Log.v("Holaaaaaaaaaaaaaaaaaa","dddd");

        JsonTodo = Diario.ConsultaDiaSQL(ordenSQL);

        int x = 0;

        try {

            agente = new Agente(
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return agente;
    }

}
