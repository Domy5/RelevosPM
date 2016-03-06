package domy.com.relevospm;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Vector;


public class TestReceiver extends BroadcastReceiver {

    public static final String TEST_INTENT= "MyTestIntent";
    private static BD_Lineas_Fijas BD;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Calendar calendario = new GregorianCalendar();
        String hora = calendario.getTime().toLocaleString();

        Calendar c = new GregorianCalendar();

        //String dia = Integer.toString(c.get(Calendar.DATE));
        String dia = "9";
        String mes = Integer.toString(1 + c.get(Calendar.MONTH));
        String annio = Integer.toString(c.get(Calendar.YEAR));

        //Toast.makeText(context, "..."+hora, Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String FECHA = dia + "/" + mes + "/" + annio;

      //  Toast.makeText(context, FECHA, Toast.LENGTH_SHORT).show();

        Vector resultado = new Diario().Diario(FECHA,context);

      //  Toast.makeText(context, "hola"  , Toast.LENGTH_SHORT).show();

       if( resultado.contains(Integer.parseInt(prefs.getString("Dne", ":")))){

           Toast.makeText(context, Integer.toString( resultado.indexOf(Integer.parseInt(prefs.getString("Dne", ":")))) , Toast.LENGTH_SHORT).show();

       }else{

           Toast.makeText(context, "Libras"  , Toast.LENGTH_SHORT).show();
       }

        editor.putString("L", hora.substring(hora.length()-1,hora.length()));
        editor.putString("M", Tabla_Diaria2.DNE(resultado.get(0).toString()));
        editor.putString("T", Tabla_Diaria2.DNE(resultado.get(1).toString()));
        editor.putString("N", Tabla_Diaria2.DNE(resultado.get(2).toString()));
        editor.apply();
       // if(intent.getAction().equals(TEST_INTENT)) {

       //     Toast.makeText(context, "Test Goooo", Toast.LENGTH_SHORT).show();
       // }

    }

}