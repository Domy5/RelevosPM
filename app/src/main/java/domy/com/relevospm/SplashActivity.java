package domy.com.relevospm;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import domy.com.relevospm.Utiles.UpdateApp;
import domy.com.relevospm.Utiles.Utiles;
import domy.com.relevospm.login.Login;
import domy.com.relevospm.permisos.GestorPermisos;

public class SplashActivity extends Activity {

    class PInfo {
        private String appname = "";
        private String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        //private Drawable icon;
        /*private void prettyPrint() {
            //Log.v(appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
        }*/
    }

    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 2000; // 1,5 segundos
    private static final String TAG = "PERMISOS ";
    public static int VersionCode;
    public static String VersionName = "";
    public String ApkName;
    public String AppName;
    public String BuildVersionPath = "";
    public String urlpath;
    public String PackageName;
    public String InstallAppPackageName;

    static Context context;
    static Activity activity;
    static String versionNameApp = BuildConfig.VERSION_NAME; //1.2
    static int versionCodeApp = BuildConfig.VERSION_CODE; //3

    TextView tvVerSer;
    TextView tvVerApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.splash);

        ApkName = "app-debug.apk";
        AppName = "C O S M O S";
        BuildVersionPath = "http://domimtz.synology.me/Version.txt";
        PackageName = "package:domy.com.relevospm";
        urlpath = "http://domimtz.synology.me/" + ApkName;

        activity = this;
        context = getBaseContext();

        if (Utiles.isOnline(getApplicationContext())) {

            GetVersionFromServer(BuildVersionPath);
            checkInstalledApp(AppName);

            tvVerSer = (TextView) findViewById(R.id.versionServidor);
            tvVerSer.setText(" NameServer : " + VersionName + " CodeServer : " + VersionCode);

            tvVerApp = (TextView) findViewById(R.id.versionApp);
            tvVerApp.setText(" NameApp    : " + versionNameApp + " CodeApp    : " + versionCodeApp);

            checkInstalledApp(AppName);

            //  TareaAsincrona tareaAsincrona = new TareaAsincrona();
            //  tareaAsincrona.execute();

        } else {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

                            finish();

                            break;

                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("No hay INTERNET ! ! ! ! ! \n\nIntenta conectarte y vuelve a intentarlo...")
                    .setPositiveButton("OK. salir", dialogClickListener).show();

        }

    }

    public void GetVersionFromServer(String BuildVersionPath) {
        String s = "";

        URL u;
        try {
            u = new URL(BuildVersionPath);
            String str1;
            String str2 = "";

            BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));

            while ((str1 = in.readLine()) != null) {
                str2 = str2 + str1;
            }
            in.close();
            s = str2;

        } catch (MalformedURLException e) {
            Log.w("", "MALFORMED URL EXCEPTION");
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }

        String temp = "";

        for (int i = 0; i < s.length(); i++) {
            i = s.indexOf("=") + 1;
            while (s.charAt(i) == ' ') // Skip Spaces
            {
                i++; // Move to Next.
            }
            while (s.charAt(i) != ';' && (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.')) {
                temp = temp.concat(Character.toString(s.charAt(i)));
                i++;
            }
            //
            s = s.substring(i); // Move to Next to Process.!
            temp = temp + " "; // Separate w.r.t Space Version Code and Version Name.

        }
        String[] fields = temp.split(" ");// Make Array for Version Code and Version Name.

        VersionCode = Integer.parseInt(fields[0]);
        VersionName = fields[1];

    }

    private Boolean checkInstalledApp(String appName) {
        return getPackages(appName);

    }

    private Boolean getPackages(String appName) {
        Boolean isInstalled = false;
        ArrayList<PInfo> apps = getInstalledApps(false); /* false = no system packages */
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            if (apps.get(i).appname.equals(appName)) {
                if (VersionCode > apps.get(i).versionCode) {
                    isInstalled = true;

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:


                                    UpdateApp atualizaApp = new UpdateApp();
                                    atualizaApp.setContext(getApplicationContext());
                                    atualizaApp.execute("http://domy.asuscomm.com/app-debug.apk");

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:

                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {

                                            Intent intent = new Intent(SplashActivity.this, Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, DURACION_SPLASH);

                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Disponible nueva App.\n\n¿Quieres instalar ahora?...").setPositiveButton("Si Instalar.", dialogClickListener)
                            .setNegativeButton("No Gracias.", dialogClickListener).show();

                }
                if (VersionCode <= apps.get(i).versionCode) {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            String permiso = Manifest.permission.WRITE_EXTERNAL_STORAGE;

                            int requestCode = 100;

                            boolean permisosCheck = GestorPermisos.checkearPermiso(context, permiso);

                            if (!permisosCheck) {

                                ActivityCompat.requestPermissions(activity, new String[]{permiso}, requestCode);

                            } else {

                                Intent intent = new Intent(SplashActivity.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, DURACION_SPLASH);

                }
            }
        }

        return isInstalled;
    }

    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {

        Context c = getApplicationContext();

        ArrayList<PInfo> res = new ArrayList<PInfo>();
        List<PackageInfo> packs = c.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            res.add(newInfo);
        }
        return res;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, " permisos dados "
                        , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Hasta que no des permisos no se podrás acceder"
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}