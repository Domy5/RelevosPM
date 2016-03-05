package domy.com.relevospm;

import java.util.Calendar;
        import java.util.GregorianCalendar;

        import android.app.Application;
        import android.app.PendingIntent;
        import android.appwidget.AppWidgetManager;
        import android.appwidget.AppWidgetProvider;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.widget.RemoteViews;

public class Cosmos_Widget extends AppWidgetProvider {

    //onEnabled():
    //onUpdate():
    //onDeleted():
    //onDisabled():


    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        //Iteramos la lista de widgets en ejecución
        for (int i = 0; i < appWidgetIds.length; i++) {
            //ID del widget actual
            int widgetId = appWidgetIds[i];

            //Actualizamos el widget actual
            actualizarWidget(context, appWidgetManager, widgetId);
        }


       /* ///////////////////////
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            //Intent intent = new Intent(context.getApplicationContext(), TestReceiver.class);
            Intent intent = new Intent();
            intent.setAction(TestReceiver.TEST_INTENT);
            intent.setClassName(TestReceiver.class.getPackage().getName(), TestReceiver.class.getName());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views;

            views = new RemoteViews(context.getPackageName(), R.layout.cosmos_widget);

            views.setOnClickPendingIntent(R.id.BtnActualizar, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
        ///////////////////  */

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("domy.com.relevospm.ACTUALIZAR_WIDGET")) {

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

    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
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
    }

    public static void actualizarWidget(Context context, AppWidgetManager appWidgetManager, int widgetId)
    {
        //Recuperamos el mensaje personalizado para el widget actual
        SharedPreferences prefs = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        String dne = prefs.getString("Dne" + widgetId, ":");
        String h = prefs.getString("h" , ":");

        //Obtenemos la hora actual
        Calendar calendario = new GregorianCalendar();
        String hora = calendario.getTime().toLocaleString();

        //Obtenemos la lista de controles del widget actual
        RemoteViews controles = new RemoteViews(context.getPackageName(), R.layout.cosmos_widget);

        //Asociamos los 'eventos' al widget
        Intent intent = new Intent("domy.com.relevospm.ACTUALIZAR_WIDGET");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        controles.setOnClickPendingIntent(R.id.BtnActualizar, pendingIntent);

        Intent intent2 = new Intent(context, SplashActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, widgetId, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        controles.setOnClickPendingIntent(R.id.FrmWidget, pendingIntent2);

        Intent intent3 = new Intent();
        intent3.setAction(TestReceiver.TEST_INTENT);
        intent3.putExtra("Hora",hora);
        intent3.putExtra("Dne",dne);
        intent3.setClassName(TestReceiver.class.getPackage().getName(), TestReceiver.class.getName());

        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, widgetId, intent3, PendingIntent.FLAG_CANCEL_CURRENT);
        controles.setOnClickPendingIntent(R.id.BotonL, pendingIntent3);


        //Actualizamos el mensaje en el control del widget
        controles.setTextViewText(R.id.LblMensaje, dne);

        //Actualizamos la hora en el control del widget
        controles.setTextViewText(R.id.LblHora, hora);

        //Actualizamos la Linea widget
        controles.setTextViewText(R.id.BotonL, "linea");

        //Actualizamos M
        controles.setTextViewText(R.id.BotonM, h);

        //Actualizamos T
        controles.setTextViewText(R.id.BotonT, "T");

        //Actualizamos N
        controles.setTextViewText(R.id.BotonN, "N");

        //Notificamos al manager de la actualización del widget actual
        appWidgetManager.updateAppWidget(widgetId, controles);
    }
}

