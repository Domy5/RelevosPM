package domy.com.relevospm;

import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import domy.com.relevospm.Utiles.Utiles;


public class AgenteDAO {

    private JSONArray JsonTodo;
    private static Agente agente;


    public Agente getAgente() {
        return agente;
    }

    public boolean actualizarDatosAgente(String Dne, String Fecha) {

        String F[] = Fecha.split("/");

        String ordenSQL = "SELECT * FROM " + Utiles.periodo_Mes(F[0], F[1]) + "  WHERE `DNE` = " + Dne;
        //   String ordenSQL = "SELECT * FROM " + a000_mes + "  WHERE `DNE` = " + "15910";

        //  Log.v("Holaaaaaaaaaaaaaaaaaa","dddd");

        new HiloEnSegundoPlano().execute(ordenSQL); //Arrancamos el AsyncTask. el método "execute" envía datos directamente a doInBackground()

      //  JsonTodo = Diario.ConsultaDiaSQL(ordenSQL);  //todo: tendria que  hacer una AsyncTask ya que sale fuera a internet

        return true;

    }

    public boolean actualizarDatosAgentePeriodo(String Dne, String periodo) {


        String ordenSQL = "SELECT * FROM " + periodo + "  WHERE `DNE` = " + Dne;
        //   String ordenSQL = "SELECT * FROM " + a000_mes + "  WHERE `DNE` = " + "15910";

        //  Log.v("Holaaaaaaaaaaaaaaaaaa","dddd");

        new HiloEnSegundoPlano().execute(ordenSQL); //Arrancamos el AsyncTask. el método "execute" envía datos directamente a doInBackground()

        //  JsonTodo = Diario.ConsultaDiaSQL(ordenSQL);  //todo: tendria que  hacer una AsyncTask ya que sale fuera a internet

        return true;

    }


    private class HiloEnSegundoPlano extends AsyncTask <String, Agente, Agente> {
        @Override
        protected void onPreExecute() {
         //   agente = new Agente(1, 1, "", "", 1, 1, 1, "", 1,"", "", "", "", 1, 1, "", "");
        }

        @Override
        protected Agente doInBackground(String... varEntrarBackground) {

            JsonTodo = Diario.ConsultaDiaSQL(varEntrarBackground[0]);

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
            publishProgress(agente);


            return agente;
        }

        @Override
        protected void onProgressUpdate(Agente... porcentajeProgreso) {


        }
        @Override
        protected void onPostExecute(Agente cantidadProcesados) {

            String text1 = "DNE: <font color='#FFFFFF'>" + agente.getDNE() + "</font> GRUPO: <font color='#FFFFFF'>" + agente.getGRUPO() + "</font> TURNO: <font color='#FFFFFF'>" + agente.getTURNO() + "</font>";
            String text2 = "V I: <font color='#FFFFFF'>" + agente.getVACACIONES_I() + "</font> V T: <font color='#FFFFFF'>" + agente.getVACACIONES_T() + "</font>";
            String text3 = "CAMBIO CON: <font color='#FFFFFF'>" + agente.getCAMBIO_CON() + "</font> POR: <font color='#FFFFFF'>" + agente.getPOR() + "</font> COMPENSA: <font color='#FFFFFF'>" + agente.getCOMPENSA() + "</font>";

            MainActivity.tv1.setText(Html.fromHtml(text1), TextView.BufferType.SPANNABLE);
            MainActivity.tv2.setText(Html.fromHtml(text2), TextView.BufferType.SPANNABLE);
            MainActivity.tv3.setText(Html.fromHtml(text3), TextView.BufferType.SPANNABLE);
        }

    }
}
