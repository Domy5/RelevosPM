package domy.com.relevospm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import domy.com.relevospm.Utiles.Utiles;

public class DialogoCambioDia extends DialogFragment {

    String agente;
    String fecha;
    JSONArray JsonTodo;
    Agente Agentes1;

    String DNE;

    public DialogoCambioDia() {
        // Empty constructor required for DialogFragment
    }

    public void setFecha(String Agente, String Fecha) {
        this.agente = Agente;
        this.fecha = Fecha;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.cambios_dia, null);

        TextView fecha_selecionada = (TextView) rootView.findViewById(R.id.fechaSelect);
        RadioButton r1 = (RadioButton) rootView.findViewById(R.id.r1);


        fecha_selecionada.setText(fecha);

        builder.setView(rootView);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.isFabOpen = false;

                MainActivity.fab.startAnimation(MainActivity.rotate_backward);
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.isFabOpen = false;
                
                MainActivity.fab.startAnimation(MainActivity.rotate_backward);
                dialog.cancel();
            }
        });

            return builder.create();

    }
}