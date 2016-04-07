package domy.com.relevospm;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import domy.com.relevospm.Utiles.Dia4y2;
import domy.com.relevospm.Utiles.UpdateApp;
import domy.com.relevospm.login.Login;

public class Main_Seleccionar_Dia extends AppCompatActivity {

    private static final String TAG = "PERMISOS";

    String GRUPOTRABAJO = "";

    String FECHA = "";

    CalendarView calendar;

    Button Boton_Ver_Hoy;

    Button Boton_Ver_Lineas;

    String DiaDeHoy = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the main layout of the activity
        setContentView(R.layout.activity_main);

        initializeCalendar();

        Boton_Ver_Hoy = (Button) findViewById(R.id.Boton_Ver_Hoy);

        SimpleDateFormat ss = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        DiaDeHoy = ss.format(new Date());

        Boton_Ver_Hoy.setText("HOY --> " + DiaDeHoy);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }

    }

    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar);

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);

        //The background color for the selected week.
        //   calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        //   calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.darkgreen));

        //sets the color for the separator line between weeks.
        //   calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.darkgreen));
        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        //   calendar.setSelectedDateVerticalBar(R.color.green);

        //sets the listener to be notified upon selected date change.

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                int Mes = month + 1;

                int GL = Dia4y2.GrupoLibra(day, Mes, year);
                String GT = Integer.toString(Dia4y2.GrupoTrabaja(day, Mes, year));

                GRUPOTRABAJO = GT.substring(0, 1) + " y " + GT.substring(1, 2);

                FECHA = day + "/" + Mes + "/" + year;

                Boton_Ver_Lineas = (Button) findViewById(R.id.Boton_Ver_Lineas);
                Boton_Ver_Lineas.setText("Ver Lineas de -->" + FECHA);

                //     Toast.makeText(getApplicationContext(), day + "/" + month + 1 + "/" + year, Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(),
                        FECHA + "\n" +
                                "Libra Grupo : " + GL + "\n" +
                                "Trabaja Grupo : " + GRUPOTRABAJO
                        , Toast.LENGTH_LONG).show();

            }
        });

    }

    public void lanzar_dia(View view) {

        if ((FECHA == null) || (FECHA.isEmpty())) {

            FECHA = DiaDeHoy;
        }

        Intent i = new Intent(this, Tabla_Diaria2.class); // TODO: aqui cambiar a tabla diaria

        i.putExtra("GRUPOS", GRUPOTRABAJO);
        i.putExtra("FECHA", FECHA);
        startActivity(i);
    }

    public void lanzar_dia_hoy(View view) {

        FECHA = DiaDeHoy;

        String[] f = FECHA.split("/");

        int day = Integer.parseInt(f[0]);
        int mes = Integer.parseInt(f[1]);
        int year = Integer.parseInt(f[2]);

        String GT = Integer.toString(Dia4y2.GrupoTrabaja(day, mes, year));

        GRUPOTRABAJO = GT.substring(0, 1) + " y " + GT.substring(1, 2);

        Intent i = new Intent(this, Tabla_Diaria2.class);

        i.putExtra("GRUPOS", GRUPOTRABAJO);
        i.putExtra("FECHA", FECHA);
        startActivity(i);
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_opciones) {

            Intent i = new Intent(this, Login.class);
            i.putExtra("AutoLogin", false);
            startActivity(i);

            return true;
        }

        if (id == R.id.action_Actualizar) {

            isStoragePermissionGranted();

            UpdateApp atualizaApp = new UpdateApp();
            atualizaApp.setContext(getApplicationContext());
            atualizaApp.execute("http://domy.asuscomm.com/app-debug.apk");

            return true;
        }

        if (id == R.id.action_Infor) {

            Intent i = new Intent(this, Dialog_Info.class);
            startActivity(i);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
