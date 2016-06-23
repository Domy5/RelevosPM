package domy.com.relevospm;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import domy.com.relevospm.Utiles.Utiles;


public class AgenteDAO {

    private JSONArray JsonTodo;
    private Agente agente;

    public Agente getAgente() {
        return agente;
    }

    public boolean actualizarDatosAgente(String Dne, String Fecha) {

        String F[] = Fecha.split("/");

        String ordenSQL = "SELECT * FROM " + Utiles.periodo_Mes(F[0], F[1]) + "  WHERE `DNE` = " + Dne;
        //   String ordenSQL = "SELECT * FROM " + a000_mes + "  WHERE `DNE` = " + "15910";

        //  Log.v("Holaaaaaaaaaaaaaaaaaa","dddd");

        JsonTodo = Diario.ConsultaDiaSQL(ordenSQL);  //todo: tendria que  hacer una AsyncTask ya que sale fuera a internet

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
            return false;
        }

        return true;

    }

    private class UpdateTask extends AsyncTask<String, String,String> {
        protected String doInBackground(String... urls) {

            return null;
        }

    }
}
