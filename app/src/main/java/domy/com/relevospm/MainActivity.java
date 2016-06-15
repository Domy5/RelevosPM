package domy.com.relevospm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import domy.com.relevospm.Utiles.Dia4y2;
import domy.com.relevospm.Utiles.UpdateApp;
import domy.com.relevospm.login.Login;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PERMISOS";

    private String GRUPOTRABAJO = "";
    private String FECHA = "";
    private CustomCalendarView calendar;
    private Button Boton_Ver_Hoy;
    private Button Boton_Ver_Lineas;
    private String DiaDeHoy = "";
    private TextView tv;
    private String grupo;

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        /*
        //Eventos del Drawer Layout
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        */

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    boolean fragmentTransaction = false;
                    Fragment fragment = null;
/*
                    switch (menuItem.getItemId()) {
                        case R.id.menu_seccion_1:
                            fragment = new Fragment1();
                            fragmentTransaction = true;
                            break;
                        case R.id.menu_seccion_2:
                            fragment = new Fragment2();
                            fragmentTransaction = true;
                            break;
                        case R.id.menu_seccion_3:
                            fragment = new Fragment3();
                            fragmentTransaction = true;
                            break;
                        case R.id.menu_opcion_1:
                            Log.i("NavigationView", "Pulsada opción 1");
                            break;
                        case R.id.menu_opcion_2:
                            Log.i("NavigationView", "Pulsada opción 2");
                            break;
                    }*/
                    UpdateApp atualizaAppBeta = new UpdateApp();

                    switch (menuItem.getItemId()) {
                        case R.id.menu_seccion_1:

                        case R.id.menu_seccion_2:

                            fragment = new Fragment1();
                            fragmentTransaction = true;
                            break;

                        case R.id.menu_seccion_3:

                            fragment = new Fragment1();
                            fragmentTransaction = true;
                            break;

                        case R.id.menu_opcion_1:
                            Intent i = new Intent(MainActivity.this, Login.class);
                            i.putExtra("AutoLogin", false);
                            startActivity(i);
                            break;
                        case R.id.menu_opcion_2:
                            atualizaAppBeta.setContext(MainActivity.this);
                            atualizaAppBeta.execute("http://domy.asuscomm.com/app-debug.apk");
                            break;

                        case R.id.menu_opcion_3:
                            atualizaAppBeta.setContext(MainActivity.this);
                            atualizaAppBeta.execute("http://domy.asuscomm.com/beta.apk");
                            break;

                        case R.id.menu_opcion_4:
                            Intent j = new Intent(MainActivity.this, Dialog_Info.class);
                            startActivity(j);
                            break;

                    }

                    if(fragmentTransaction) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

                        menuItem.setChecked(true);
                        getSupportActionBar().setTitle(menuItem.getTitle());
                    }

                    drawerLayout.closeDrawers();

                    return true;
                }
            });

        TextView tv1 = (TextView) findViewById(R.id.datosAgente_1);
        TextView tv2 = (TextView) findViewById(R.id.datosAgente_2);
        TextView tv3 = (TextView) findViewById(R.id.datosAgente_3);

        Boton_Ver_Hoy = (Button) findViewById(R.id.Boton_Ver_Hoy);

        SimpleDateFormat ss = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        DiaDeHoy = ss.format(new Date());

        Boton_Ver_Hoy.setText("HOY --> " + DiaDeHoy);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        String dne = prefs.getString("Dne", ":");
        String a000_mes = null;

        Calendar c = Calendar.getInstance(Locale.getDefault());

        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH) + 1;
        int annio = c.get(Calendar.YEAR);

        Log.v("hola", " dia " + dia + " " + mes + "");

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


        //  Log.v("hola"," dia " + dne);

        JSONArray JsonTodo;

        JsonTodo = Diario.ConsultaDiaSQL("SELECT `GRUPO`,`TURNO`,`VACACIONES_I`,`VACACIONES_T`,`CAMBIO_CON`,`POR`,`COMPENSA` FROM `" + a000_mes + "` WHERE DNE =" + dne);
//"SELECT `GRUPO`,`TURNO`,`VACACIONES_I`,`VACACIONES_T`,`CAMBIO_CON`,`POR`,`COMPENSA` FROM `a060_Junio` WHERE DNE =`15910`

        try {

            grupo = JsonTodo.getJSONObject(0).getString("GRUPO");
            String turno = JsonTodo.getJSONObject(0).getString("TURNO");
            String v_i = JsonTodo.getJSONObject(0).getString("VACACIONES_I");
            String v_t = JsonTodo.getJSONObject(0).getString("VACACIONES_T");
            String cambio_con = JsonTodo.getJSONObject(0).getString("CAMBIO_CON");
            String por = JsonTodo.getJSONObject(0).getString("POR");
            String compensa = JsonTodo.getJSONObject(0).getString("COMPENSA");

            // id  N_MES  MES   PUESTO  ORDEN   ESCALAFON     DNE  NOMBRE  GRUPO  TURNO   VACACIONES_I  VACACIONES_T  VACACIONES CAMBIO_CON  POR  COMPENSA   OBSERVACIONES
            // 63      1  Enero      C     18          59   15910    Domy      3      N   0000-00-00      0000-00-00                     0      0

            String text1 = "DNE: <font color='#FFFFFF'>" + dne + "</font> GRUPO: <font color='#FFFFFF'>" + grupo + "</font> TURNO: <font color='#FFFFFF'>" + turno + "</font>";
            String text2 = "V I: <font color='#FFFFFF'>" + v_i + "</font> V T: <font color='#FFFFFF'>" + v_t + "</font>";
            String text3 = "CAMBIO CON: <font color='#FFFFFF'>" + cambio_con + "</font> POR: <font color='#FFFFFF'>" + por + "</font> COMPENSA: <font color='#FFFFFF'>" + compensa + "</font>";

            tv1.setText(Html.fromHtml(text1), TextView.BufferType.SPANNABLE);
            tv2.setText(Html.fromHtml(text2), TextView.BufferType.SPANNABLE);
            tv3.setText(Html.fromHtml(text3), TextView.BufferType.SPANNABLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        initializeCalendar(Integer.parseInt(grupo));

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

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //////////////////////////
    public void grupo(int i) {

        List decorators = new ArrayList<>();
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        DaysDecorator1 g1 = new DaysDecorator1();
        DaysDecorator3 g3 = new DaysDecorator3();
        DaysDecorator5 g5 = new DaysDecorator5();

        switch (i) {

            case 1:
                decorators.add(g1);
                break;
            case 3:
                decorators.add(g3);
                break;
            case 5:
                decorators.add(g5);
                break;
            default:

                break;
        }
        calendar.setDecorators(decorators);
        calendar.refreshCalendar(currentCalendar);


    }
    public void grupo(View v) {

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
    public void initializeCalendar(int grupo) {
        calendar = (CustomCalendarView) findViewById(R.id.calendar_viewNEW);

//Initialize calendar with date
        final Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

//Show Monday as first date of week
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

//Show/hide overflow days of a month
        calendar.setShowOverflowDate(true);

//call refreshCalendar to update calendar the view
        //     calendar.refreshCalendar(currentCalendar);

        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Arch_Rival_Bold.ttf");
        calendar.setCustomTypeface(typeface);
        calendar.refreshCalendar(currentCalendar);

        grupo(grupo);

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

                String D[] = df.format(date).toString().split("-");

                int dia = Integer.parseInt(D[0]);
                int Mes = Integer.parseInt(D[1]);
                int anio = Integer.parseInt(D[2]);

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
                        , Toast.LENGTH_SHORT).show();

                //  calendar.refreshCalendar(currentCalendar);


            }

            @Override
            public void onMonthChanged(Date date) {
                //  SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
            }
        });

    }
    public void lanzar_dia(View view) {

        if ((FECHA == null) || (FECHA.isEmpty())) {

            FECHA = DiaDeHoy;
        }

        Intent i = new Intent(this, Tabla_Diaria2.class);

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
    @Override
    protected void onStart() {
        // initializeCalendar();
        super.onStart();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onResume() {
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
