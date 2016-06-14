package domy.com.relevospm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import domy.com.relevospm.Utiles.Utiles;

public class DialogoAgenteAlertDialogBuilder extends DialogFragment {

    String agente;
    String fecha;
    JSONArray JsonTodo;
    Agente Agentes1;

    String DNE;

    public DialogoAgenteAlertDialogBuilder() {
        // Empty constructor required for DialogFragment
    }

    public void setAgente(String Agente, String Fecha) {
        this.agente = Agente;
        this.fecha = Fecha;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       if( Utiles.isNumeric(agente)){
           DNE = agente;
        }else{
           DNE = Tabla_Diaria2.DNE(agente);
        }


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

        JsonTodo = Diario.ConsultaDiaSQL(ordenSQL);

        try {

            Agentes1 = new Agente(
                    Integer.parseInt(JsonTodo.getJSONObject(0).getString("id")),
                    Integer.parseInt(JsonTodo.getJSONObject(0).getString("N_MES")),
                    JsonTodo.getJSONObject(0).getString("MES"),
                    JsonTodo.getJSONObject(0).getString("PUESTO"),
                    Integer.parseInt(JsonTodo.getJSONObject(0).getString("ORDEN")),
                    Integer.parseInt(JsonTodo.getJSONObject(0).getString("ESCALAFON")),
                    Integer.parseInt(JsonTodo.getJSONObject(0).getString("DNE")),
                    JsonTodo.getJSONObject(0).getString("NOMBRE"),
                    Integer.parseInt(JsonTodo.getJSONObject(0).getString("GRUPO")),
                    JsonTodo.getJSONObject(0).getString("TURNO"),
                    JsonTodo.getJSONObject(0).getString("VACACIONES_I"),
                    JsonTodo.getJSONObject(0).getString("VACACIONES_T"),
                    JsonTodo.getJSONObject(0).getString("VACACIONES"),
                    Integer.parseInt(JsonTodo.getJSONObject(0).getString("CAMBIO_CON")),
                    Integer.parseInt(JsonTodo.getJSONObject(0).getString("POR")),
                    JsonTodo.getJSONObject(0).getString("COMPENSA"),
                    JsonTodo.getJSONObject(0).getString("OBSERVACIONES"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialogo_agente, null);

        TextView tvNombre = (TextView) rootView.findViewById(R.id.nombre);
        TextView tvGrupo = (TextView) rootView.findViewById(R.id.grupo);
        TextView tvTurno = (TextView) rootView.findViewById(R.id.turno);
        TextView tvV_i = (TextView) rootView.findViewById(R.id.Vi);
        TextView tvV_t = (TextView) rootView.findViewById(R.id.Vt);
        TextView tvCambio_con = (TextView) rootView.findViewById(R.id.cambio_con);
        TextView tvPor = (TextView) rootView.findViewById(R.id.por);
        TextView tvCompensa = (TextView) rootView.findViewById(R.id.compesa);

        tvNombre.setText(tvNombre.getText().toString() + Agentes1.getNOMBRE().toString());
        tvGrupo.setText(tvGrupo.getText().toString() + String.valueOf(Agentes1.getGRUPO()));
        tvTurno.setText(tvTurno.getText().toString() + Agentes1.getTURNO());
        tvV_i.setText(tvV_i.getText().toString() + Agentes1.getVACACIONES_I());
        tvV_t.setText(tvV_t.getText().toString() + Agentes1.getVACACIONES_T());
        tvCambio_con.setText(tvCambio_con.getText().toString() + " " +
                String.valueOf(Agentes1.getCAMBIO_CON())+ " " +
                Tabla_Diaria2.DNE(String.valueOf(Agentes1.getCAMBIO_CON())) );
        tvPor.setText(tvPor.getText().toString() + " " +
                String.valueOf(Agentes1.getPOR()+ " " +
                        Tabla_Diaria2.DNE(String.valueOf(Agentes1.getPOR()))));
        tvCompensa.setText(tvCompensa.getText().toString() + Agentes1.getCOMPENSA());

        builder.setView(rootView);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return builder.create();
    }
}