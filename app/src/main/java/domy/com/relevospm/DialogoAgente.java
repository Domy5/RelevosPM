package domy.com.relevospm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import domy.com.relevospm.Utiles.Utiles;


public class DialogoAgente extends DialogFragment {

    String agente;
    String fecha;
    JSONArray JsonTodo;
    Agente Agentes1;

    public DialogoAgente() {
        // Empty constructor required for DialogFragment
    }

    public void setAgente(String Agente, String Fecha) {
        this.agente = Agente;
        this.fecha = Fecha;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        String DNE = agente ;

        String F[] = fecha.split("/");

        String ordenSQL = "SELECT * FROM " + Utiles.periodo_Mes(F[0],F[1])+ "  WHERE `DNE` = " + DNE;
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

        // se va a crear el dialogo

        View rootView = inflater.inflate(R.layout.dialogo_agente, container);
        getDialog().setTitle("Info Agente: ");

        TextView tvNombre = (TextView) rootView.findViewById(R.id.nombre);
        TextView tvGrupo = (TextView) rootView.findViewById(R.id.grupo);
        TextView tvTurno = (TextView) rootView.findViewById(R.id.turno);
        TextView tvV_i = (TextView) rootView.findViewById(R.id.Vi);
        TextView tvV_t = (TextView) rootView.findViewById(R.id.Vt);
        TextView tvCambio_con = (TextView) rootView.findViewById(R.id.cambio_con);
        TextView tvPor = (TextView) rootView.findViewById(R.id.por);
        TextView tvCompensa = (TextView) rootView.findViewById(R.id.compesa);

        tvNombre.setText( tvNombre.getText().toString()+Agentes1.getNOMBRE().toString());
        tvGrupo.setText(tvGrupo.getText().toString()+String.valueOf(Agentes1.getGRUPO()));
        tvTurno.setText(tvTurno.getText().toString()+Agentes1.getTURNO());
        tvV_i.setText(tvV_i.getText().toString()+Agentes1.getVACACIONES_I());
        tvV_t.setText(tvV_t.getText().toString()+Agentes1.getVACACIONES_T());
        tvCambio_con.setText(tvCambio_con.getText().toString()+String.valueOf(Agentes1.getCAMBIO_CON()));
        tvPor.setText(tvPor.getText().toString()+String.valueOf(Agentes1.getPOR()));
        tvCompensa.setText(tvCompensa.getText().toString()+Agentes1.getCOMPENSA());

        return rootView;
    }

  /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder buider = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        buider.setView(inflater.inflate(R.layout.dialogo_agente, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        return buider.create();
        // return super.onCreateDialog(savedInstanceState);
    } */
}
