package domy.com.relevospm;

import org.json.JSONArray;
import org.json.JSONException;


public class DatosAgente  {

    String agente;
    String fecha;
    JSONArray JsonTodo;
    Agente Agentes1;

    public void setAgente(String Agente, String Fecha) {
        this.agente = Agente;
        this.fecha = Fecha;
    }

    public Agente DatosAgente (){

        String DNE = agente ;

        String F[] = fecha.split("/");

        int dia = Integer.parseInt(F[0]);
        int mes = Integer.parseInt(F[1]);
        int anho = Integer.parseInt(F[2]);

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

        String ordenSQL = "SELECT * FROM " + a000_mes + "  WHERE `DNE` = " + DNE;
        //   String ordenSQL = "SELECT * FROM " + a000_mes + "  WHERE `DNE` = " + "15910";
        // Log.v("Holaaaaaaaaaaaaaaaaaa",ordenSQL);

        JsonTodo = Diario.ConsultaDiaSQL(ordenSQL);

        int x = 0;

        try {

            Agentes1 = new Agente(
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

        return Agentes1;
    }

}
