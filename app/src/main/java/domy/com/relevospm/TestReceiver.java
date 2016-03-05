package domy.com.relevospm;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class TestReceiver extends BroadcastReceiver {

    public static final String TEST_INTENT= "MyTestIntent";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Calendar calendario = new GregorianCalendar();
        String hora = calendario.getTime().toLocaleString();

        Toast.makeText(context, "..."+hora, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, intent.getStringExtra("Dne"), Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("h", hora.substring(hora.length()-1,hora.length()));

        editor.apply();
       // if(intent.getAction().equals(TEST_INTENT)) {

       //     Toast.makeText(context, "Test Goooo", Toast.LENGTH_SHORT).show();
       // }
    }
}