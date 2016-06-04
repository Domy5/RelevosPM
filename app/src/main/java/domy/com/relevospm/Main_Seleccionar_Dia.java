package domy.com.relevospm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import domy.com.relevospm.Utiles.Dia4y2;
import domy.com.relevospm.Utiles.UpdateApp;
import domy.com.relevospm.login.Login;

public class Main_Seleccionar_Dia extends AppCompatActivity {

    private static final String TAG = "PERMISOS";

    String GRUPOTRABAJO = "";

    String FECHA = "";

    CustomCalendarView calendar;

    Button Boton_Ver_Hoy;

    Button Boton_Ver_Lineas;

    String DiaDeHoy = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the main layout of the activity
        setContentView(R.layout.calendario);

        initializeCalendar();

        Boton_Ver_Hoy = (Button) findViewById(R.id.Boton_Ver_Hoy);

        SimpleDateFormat ss = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        DiaDeHoy = ss.format(new Date());

        Boton_Ver_Hoy.setText("HOY --> " + DiaDeHoy);
    }

    public void grupo(View v){

        List decorators = new ArrayList<>();
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
        DaysDecorator1 g1 = new DaysDecorator1();
        DaysDecorator3 g3 = new DaysDecorator3();
        DaysDecorator5 g5 = new DaysDecorator5();


        switch (v.getId()) {

            case R.id.G1:
                decorators.add(g1);
                break;
            case R.id.G3:
                decorators.add(g3);
                break;
            case R.id.G5:
                decorators.add(g5);
                break;
            default:

                break;
        }
        calendar.setDecorators(decorators);
        calendar.refreshCalendar(currentCalendar);

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
        calendar = (CustomCalendarView) findViewById(R.id.calendar_viewNEW);

//Initialize calendar with date
      final  Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

//Show Monday as first date of week
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

//Show/hide overflow days of a month
        calendar.setShowOverflowDate(true);

//call refreshCalendar to update calendar the view
   //     calendar.refreshCalendar(currentCalendar);

        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Arch_Rival_Bold.ttf");
            calendar.setCustomTypeface(typeface);
            calendar.refreshCalendar(currentCalendar);

//calendar.setBackgroundColor(0xff0000);

        //adding calendar day decorators
        //List decorators = new ArrayList<>();
       // decorators.add(new DaysDecorator().decorate("lo que sea " ,Grupo));

       // DaysDecorator1 g1 = new DaysDecorator1();
       // DaysDecorator3 g3 = new DaysDecorator3();
        //DaysDecorator5 g5 = new DaysDecorator5();
        //decorators.add(g3);
       // calendar.setDecorators(decorators);
       /// calendar.refreshCalendar(currentCalendar);

//Handling custom calendar events
        calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
             //   Toast.makeText(Main_Seleccionar_Dia.this, df.format(date), Toast.LENGTH_SHORT).show();

                String D[] = df.format(date).toString().split("-");

                int dia =  Integer.parseInt(D[0]);
                int Mes =  Integer.parseInt(D[1]);
                int anio =  Integer.parseInt(D[2]);

                int GL = Dia4y2.GrupoLibra(dia, Mes, anio);
                String GT = Integer.toString(Dia4y2.GrupoTrabaja(dia, Mes, anio));

                GRUPOTRABAJO = GT.substring(0, 1) + " y " + GT.substring(1, 2);

                FECHA = dia + "/" + Mes + "/" + anio;

                Boton_Ver_Lineas = (Button) findViewById(R.id.Boton_Ver_Lineas);
                Boton_Ver_Lineas.setText("Ver Lineas de -->" + FECHA);

                //     Toast.makeText(getApplicationContext(), day + "/" + month + 1 + "/" + year, Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(),
                        FECHA + "\n" +
                                "Libra Grupo : " + GL + "\n" +
                                "Trabaja Grupo : " + GRUPOTRABAJO
                        , Toast.LENGTH_LONG).show();

              //  calendar.refreshCalendar(currentCalendar);



            }

            @Override
            public void onMonthChanged(Date date) {
              //  SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
             //   Toast.makeText(Main_Seleccionar_Dia.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*
    public void initializeCalendar() {
        calendar = (CalendarView) findViewById(R.id.calendar_viewNEW);

        calendar.setShowWeekNumber(false);

        calendar.setFirstDayOfWeek(2);

        Date now = new Date();
        calendar.setDate(now.getTime());// para que siempre que se reinicia mueste el dia de hoy

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

    }*/

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

    @Override
    protected void onStart() {
        // initializeCalendar();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        initializeCalendar();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        initializeCalendar();
        super.onResume();
    }

    @Override
    protected void onPause() {
        // initializeCalendar();
        super.onPause();
    }

    @Override
    protected void onStop() {
        // initializeCalendar();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // initializeCalendar();
        super.onDestroy();
    }

}
