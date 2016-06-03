package domy.com.relevospm.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import domy.com.relevospm.Diario;
import domy.com.relevospm.R;
import domy.com.relevospm.SplashActivity;

public class Cosmos_Widget2 extends AppWidgetProvider {

    public static String l = "*";

    public static Vector resultado = null;

    public static int a = 3;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Iteramos la lista de widgets en ejecución
        for (int i = 0; i < appWidgetIds.length; i++) {
            //ID del widget actual
            int widgetId = appWidgetIds[i];

            //Actualizamos el widget actual

            actualizarWidget(context, appWidgetManager, widgetId);

        }
       super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("domy.com.relevospm.ACTUALIZAR_WIDGET2")) {

            //Obtenemos el ID del widget a actualizar
            int widgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            //Obtenemos el widget manager de nuestro contexto
            AppWidgetManager widgetManager =
                    AppWidgetManager.getInstance(context);

            //Actualizamos el widget

                if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    actualizarWidget(context, widgetManager, widgetId);
                }

        }

          super.onReceive(context, intent);
    }

    public static void actualizarWidget(Context context, AppWidgetManager appWidgetManager, int widgetId)
    {

        // int SDK_INT = android.os.Build.VERSION.SDK_INT;
        // if (SDK_INT > 8)

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Recuperamos el mensaje personalizado para el widget actual
        SharedPreferences prefs = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        String dne = prefs.getString("Dne", ":");

        //Obtenemos la hora actual
        Calendar calendario = new GregorianCalendar();
        String hora = calendario.getTime().toLocaleString();

        //Obtenemos la lista de controles del widget actual
        RemoteViews controles = new RemoteViews(context.getPackageName(), R.layout.cosmos_widget2);

        //Asociamos los 'eventos' al widget

        Intent intent = new Intent("domy.com.relevospm.ACTUALIZAR_WIDGET2");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        controles.setOnClickPendingIntent(R.id.BotonL, pendingIntent);

        Intent intent2 = new Intent(context, SplashActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, widgetId, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        controles.setOnClickPendingIntent(R.id.FrmWidget, pendingIntent2);


       // Intent intent3 = new Intent(context, WidgetConfig.class);
       // PendingIntent pendingIntent3 = PendingIntent.getActivity(context, widgetId, intent3, PendingIntent.FLAG_UPDATE_CURRENT);

       // controles.setOnClickPendingIntent(R.id.config, pendingIntent3);

        String dia = Integer.toString(calendario.get(Calendar.DATE));
        String mes = Integer.toString(1 + calendario.get(Calendar.MONTH));
        String annio = Integer.toString(calendario.get(Calendar.YEAR));

        String FECHA = dia + "/" + mes + "/" + annio;

        resultado =  Diario.Diario(FECHA, dne ,context);

        if( resultado != null ) {

            if (resultado.contains(Integer.parseInt(dne)) || resultado.contains(dne)) {

                int posicion = resultado.indexOf(Integer.parseInt(dne));

              //  Toast.makeText(context, "Posición: " + Integer.toString(posicion), Toast.LENGTH_SHORT).show();

                int LINEA = (int) Math.floor((posicion / a) - 0.1) + 2;

                if (LINEA >= 13) {
                    l = "RVA " + Integer.toString(LINEA - 12);
                } else {
                    l = "LINEA " + Integer.toString(LINEA);
                }

            } else {

              //  Toast toast1 = Toast.makeText(context, "Libras", Toast.LENGTH_SHORT);
              //  toast1.setGravity(Gravity.TOP,0,0);
              //  toast1.show();

                l = "LIBRAS";

            }
        }

        controles.setTextViewText(R.id.LblMensaje, dne);
        controles.setTextViewText(R.id.LblHora, hora);
        controles.setTextViewText(R.id.BotonL, l);
        controles.setTextColor(R.id.BotonL, Color.BLUE);

        //Notificamos al manager de la actualización del widget actual
        appWidgetManager.updateAppWidget(widgetId, controles);
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        /*

        //Accedemos a las preferencias de la aplicación
        SharedPreferences prefs = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        //Eliminamos las preferencias de los widgets borrados
        for(int i=0; i<appWidgetIds.length; i++)
        {
            //ID del widget actual
            int widgetId = appWidgetIds[i];

            editor.remove("Dne" + widgetId);
        }

        //Aceptamos los cambios
        editor.apply();

        super.onDeleted(context, appWidgetIds);

        */
    }

    public static int puestoM( int p ) {

        int[][] PosicionTURNO ={{0,3,6, 9,12,15,18,21,24,27,30,33,36,39,42,45,48,51,54,57,60}   //mañana
                               ,{1,4,7,10,13,16,19,22,25,28,31,34,37,40,43,46,49,52,55,58,61}   //tarde
                               ,{2,5,8,11,14,17,20,23,26,29,32,35,38,41,44,47,50,53,56,59,62}}; //noche

        int posicion = 0;

        for (int j=0; j < PosicionTURNO[0].length; j++) {

            if (PosicionTURNO[0][j] == p) {
                return p;
            } }

        for (int j=0; j < PosicionTURNO[1].length; j++) {

            if (PosicionTURNO[1][j] == p) {
                return p -1;
            } }

        for (int j=0; j < PosicionTURNO[2].length; j++) {

            if (PosicionTURNO[2][j] == p) {
                return p - 2;
            } }

        return posicion;
    }
}

