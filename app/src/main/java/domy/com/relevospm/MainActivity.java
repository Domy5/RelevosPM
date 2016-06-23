package domy.com.relevospm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;

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
    private Button Boton_Ver_Lineas;
    private String DiaDeHoy = "";
    private TextView tv;

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private String dne;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;

    public static Boolean isFabRota = false;
    public static Boolean isFabMove = false;

    public static FloatingActionButton fab;
    public static Animation rotate_forward, rotate_backward, move_forward, move_backward;
    RelativeLayout rl_footer;

    //http://es.androids.help/q1094

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); // todo lo puse por el problema de salir a fuera en vez de crear la   Asynctask
        StrictMode.setThreadPolicy(policy);
      ///

        rl_footer = (RelativeLayout) findViewById(R.id.rl_footer);

        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Button boton_Ver_Hoy = (Button) findViewById(R.id.Boton_Ver_Hoy);

        tv1 = (TextView) findViewById(R.id.datosAgente_1);
        tv2 = (TextView) findViewById(R.id.datosAgente_2);
        tv3 = (TextView) findViewById(R.id.datosAgente_3);

        /*//Eventos del Drawer Layout
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}
            @Override
            public void onDrawerOpened(View drawerView) { }
            @Override
            public void onDrawerClosed(View drawerView) {}
            @Override
            public void onDrawerStateChanged(int newState) {}});*/

        navView = (NavigationView) findViewById(R.id.navview);
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

                                atualizaAppBeta.setActivity(MainActivity.this);
                                atualizaAppBeta.setContext(MainActivity.this);
                                atualizaAppBeta.execute("http://domy.asuscomm.com/app-debug.apk");
                                break;

                            case R.id.menu_opcion_3:

                                atualizaAppBeta.setActivity(MainActivity.this);
                                atualizaAppBeta.setContext(MainActivity.this);
                                atualizaAppBeta.execute("http://domy.asuscomm.com/beta.apk");
                                break;

                            case R.id.menu_opcion_4:
                                Intent j = new Intent(MainActivity.this, Dialog_Info.class);
                                startActivity(j);
                                break;

                        }

                        if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });


        SimpleDateFormat ss = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // todo ojo con el locale
        //  Date date = new Date();
        DiaDeHoy = ss.format(new Date());

        if (boton_Ver_Hoy != null) {
            boton_Ver_Hoy.setText("HOY --> ".concat(DiaDeHoy));
        }

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
        dne = prefs.getString("Dne", ":");

        Calendar c = Calendar.getInstance(Locale.getDefault());

        int dia = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH) + 1;
        int annio = c.get(Calendar.YEAR);

        String fecha = String.valueOf(dia) + "/" + String.valueOf(mes) + "/" + String.valueOf(annio);

        AgenteDAO agenteDao = new AgenteDAO();


        Log.e("problema de conc", dne);
        Log.e("problema de conc", fecha);

        agenteDao.actualizarDatosAgente(dne, fecha);

        Agente agente = agenteDao.getAgente();

        String text1 = "DNE: <font color='#FFFFFF'>" + agente.getDNE() + "</font> GRUPO: <font color='#FFFFFF'>" + agente.getGRUPO() + "</font> TURNO: <font color='#FFFFFF'>" + agente.getTURNO() + "</font>";
        String text2 = "V I: <font color='#FFFFFF'>" + agente.getVACACIONES_I() + "</font> V T: <font color='#FFFFFF'>" + agente.getVACACIONES_T() + "</font>";
        String text3 = "CAMBIO CON: <font color='#FFFFFF'>" + agente.getCAMBIO_CON() + "</font> POR: <font color='#FFFFFF'>" + agente.getPOR() + "</font> COMPENSA: <font color='#FFFFFF'>" + agente.getCOMPENSA() + "</font>";

        tv1.setText(Html.fromHtml(text1), TextView.BufferType.SPANNABLE);
        tv2.setText(Html.fromHtml(text2), TextView.BufferType.SPANNABLE);
        tv3.setText(Html.fromHtml(text3), TextView.BufferType.SPANNABLE);

        initializeCalendar();

        fab = (FloatingActionButton) findViewById(R.id.botonFlotante);

        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        move_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_forward);
        move_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_backward);

        move_backward.setInterpolator(new CustomBounceInterpolator(500));
        move_forward.setInterpolator(new CustomBounceInterpolator(500));

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               animateFABmove();

                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });

    }

    public void initializeCalendar() {
        calendar = (CustomCalendarView) findViewById(R.id.calendar_viewNEW);

        final Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Arch_Rival_Bold.ttf");
        calendar.setCustomTypeface(typeface);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setShowOverflowDate(true);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String D[] = df.format(calendar.getCurrentCalendar().getTime()).split("/");

        int Dia = Integer.parseInt(D[0]);
        int Mes = Integer.parseInt(D[1]);
        int Anio = Integer.parseInt(D[2]);

        FECHA = Dia + "/" + Mes + "/" + Anio;
        //PintarCalendario(datosAgente.DatosAgente().getGRUPO());
        PintarCalendario(dne, FECHA);

        calendar.refreshCalendar(currentCalendar);

        calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                String D[] = df.format(date).split("/");

                int Dia_selecion_Calendar = Integer.parseInt(D[0]);
                int Mes_selecion_Calendar = Integer.parseInt(D[1]);
                int Anio_selecion_Calendar = Integer.parseInt(D[2]);

                int GL = Dia4y2.GrupoLibra(Dia_selecion_Calendar, Mes_selecion_Calendar, Anio_selecion_Calendar);
                String GT = Integer.toString(Dia4y2.GrupoTrabaja(Dia_selecion_Calendar, Mes_selecion_Calendar, Anio_selecion_Calendar));

                GRUPOTRABAJO = GT.substring(0, 1) + " y " + GT.substring(1, 2);
                FECHA = Dia_selecion_Calendar + "/" + Mes_selecion_Calendar + "/" + Anio_selecion_Calendar;
                Boton_Ver_Lineas = (Button) findViewById(R.id.Boton_Ver_Lineas);

                Boton_Ver_Lineas.setText("Ver Lineas de -->".concat(FECHA));

                //  Toast.makeText(getApplicationContext(),
                //          FECHA + "\n" +
                //                  "Libra Grupo : " + GL + "\n" +
                //                  "Trabaja Grupo : " + GRUPOTRABAJO
                //          , Toast.LENGTH_SHORT).show();

                //  Snackbar.make(drawerLayout, FECHA + "\n" + "Libra: " + GL + " Trabaja: " + GRUPOTRABAJO, Snackbar.LENGTH_SHORT).show();

                Snackbar snackbar = Snackbar
                        .make(drawerLayout, FECHA + "\n" + "Libra: " + GL + " Trabaja: " + GRUPOTRABAJO, Snackbar.LENGTH_LONG);

                snackbar.setActionTextColor(Color.RED);

                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                textView.setGravity(Gravity.CENTER_HORIZONTAL);

                snackbar.show();


                //calendar.refreshCalendar(calendar.getCurrentCalendar());

            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                String D[] = df.format(date).split("/");

                int Dia_selecion_Calendar = Integer.parseInt(D[0]);
                int Mes_selecion_Calendar = Integer.parseInt(D[1]);
                int Anio_selecion_Calendar = Integer.parseInt(D[2]);

                String fecha1 = String.valueOf(Dia_selecion_Calendar) + "/" + String.valueOf(Mes_selecion_Calendar) + "/" + String.valueOf(Anio_selecion_Calendar);
                String fecha = String.valueOf(1) + "/" + String.valueOf(Mes_selecion_Calendar) + "/" + String.valueOf(Anio_selecion_Calendar);

                AgenteDAO agenteDao = new AgenteDAO();

                agenteDao.actualizarDatosAgente(dne, fecha);
                Agente agente = agenteDao.getAgente();

                // PintarCalendario(datosAgente.DatosAgente().getGRUPO());
                PintarCalendario(dne, fecha1);

                String text1 = "DNE: <font color='#FFFFFF'>" + agente.getDNE() + "</font> GRUPO: <font color='#FFFFFF'>" + agente.getGRUPO() + "</font> TURNO: <font color='#FFFFFF'>" + agente.getTURNO() + "</font>";
                String text2 = "V I: <font color='#FFFFFF'>" + agente.getVACACIONES_I() + "</font> V T: <font color='#FFFFFF'>" + agente.getVACACIONES_T() + "</font>";
                String text3 = "CAMBIO CON: <font color='#FFFFFF'>" + agente.getCAMBIO_CON() + "</font> POR: <font color='#FFFFFF'>" + agente.getPOR() + "</font> COMPENSA: <font color='#FFFFFF'>" + agente.getCOMPENSA() + "</font>";

                tv1.setText(Html.fromHtml(text1), TextView.BufferType.SPANNABLE);
                tv2.setText(Html.fromHtml(text2), TextView.BufferType.SPANNABLE);
                tv3.setText(Html.fromHtml(text3), TextView.BufferType.SPANNABLE);

                //   calendar.refreshCalendar(calendar.getCurrentCalendar());

            }
        });

    }

    public void PintarCalendario(String dne, String fecha) {

        List decorators = new ArrayList<>();

        DaysDecorator g1 = new DaysDecorator();

        g1.setDne(dne);
        g1.setFecha(fecha);

        decorators.clear();

        decorators.add(g1);

        calendar.setDecorators(decorators);
        calendar.refreshCalendar(calendar.getCurrentCalendar());

    }

    public void PintarCalendario(View v) {
        // SimpleDateFormat ss = new SimpleDateFormat("dd/MM/yyyy");
        // String fecha = ss.format(calendar.getCurrentCalendar());
        // String fecha = "21/6/2016";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fecha = df.format(calendar.getCurrentCalendar().getTime());

        List decorators = new ArrayList<>();

        DaysDecorator g1 = new DaysDecorator();

        g1.setFecha(fecha);

        decorators.clear();

        switch (v.getId()) {

            case R.id.G1:
                g1.setGrupo(1);
                break;
            case R.id.G3:
                g1.setGrupo(3);
                break;
            case R.id.G5:
                g1.setGrupo(5);
                break;
            default:

                break;
        }

        decorators.add(g1);

        calendar.setDecorators(decorators);
        calendar.refreshCalendar(calendar.getCurrentCalendar());

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:

                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:

                Intent i = new Intent(MainActivity.this, Login.class);
                i.putExtra("AutoLogin", false);
                startActivity(i);
                return true;

            case R.id.action_permisos:

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

                return true;

            case R.id.action_salir:
                finish();

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
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
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

   /* @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.botonFlotante:

                animateFAB();
                // animateFABmove();
                break;
        }
    }*/

    public void animateFAB() {

        if (isFabRota) {

            fab.startAnimation(rotate_backward);
            isFabRota = false;

        } else {


            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogoCambioDia dialogo = new DialogoCambioDia();
            dialogo.setFecha("hola", FECHA);
            dialogo.show(fragmentManager, "tagPersonalizado");
            fab.startAnimation(rotate_forward);
            isFabRota = true;

        }
    }

    public void animateFABmove() {

        if (isFabMove) {

         //   fab.startAnimation(move_forward);
            SlideToDown();
            isFabMove = false;
        } else {

         //   fab.startAnimation(move_backward);
            SlideToAbove();
            isFabMove = true;

        }
    }

    public class CustomBounceInterpolator implements Interpolator {

        private float timeDivider;
        private AccelerateInterpolator a;
        private BounceInterpolator b;

        public CustomBounceInterpolator(float timeDivider) {
            a = new AccelerateInterpolator();
            b = new BounceInterpolator();
            this.timeDivider = timeDivider;
        }

        public float getInterpolation(float t) {
            if (t < timeDivider)
                return a.getInterpolation(t);
            else
                return b.getInterpolation(t);
        }

    }

    public void SlideToAbove() {
        Animation slide = null;

        //slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -70.0f,Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
         slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -5.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(false);
        rl_footer.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                rl_footer.clearAnimation();

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        rl_footer.getWidth(), rl_footer.getHeight());
                // lp.setMargins(0, 0, 0, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                rl_footer.setLayoutParams(lp);

            }

        });

    }

    public void SlideToDown() {
        Animation slide = null;
        //  slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -70.0f, Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f);
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 5.0f);


        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(false);
        rl_footer.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                rl_footer.clearAnimation();

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams( rl_footer.getWidth(), rl_footer.getHeight());
                lp.setMargins(0, rl_footer.getWidth(), 0, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                rl_footer.setLayoutParams(lp);

            }

        });

    }


}
