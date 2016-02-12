package domy.com.relevospm;

/*
 *  Code Prepared by **Muhammad Mubashir**.
 *  Analyst Software Enggineer.
    Emial Id : muhammad.mubashir.bscs@gmail.com
    Skype Id : muhammad.mubashir.ansari
    Code: **August, 2011.**

    Description: **Get Updates(means New .Apk File) from IIS Server and Download it on Device SD Card,
                 and Uninstall Previous (means OLD .apk) and Install New One.
                 and also get Installed App Version Code & Version Name.**

    All Rights Reserved.
*/

        import java.io.BufferedReader;
        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;
       // import com.SelfInstall01.SelfInstall01Activity;
        import android.Manifest;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.app.AlertDialog.Builder;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.StrictMode;
        import android.support.v4.app.ActivityCompat;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

public class SelfInstall01Activity extends Activity
{
    private static final String TAG ="PERMISOS " ;

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
    public int VersionCode;
    public String VersionName="";
    public String ApkName ;
    public String AppName ;
    public String BuildVersionPath="";
    public String urlpath ;
    public String PackageName;
    public String InstallAppPackageName;
    public String Text="";

    TextView tvApkStatus;
    Button btnCheckUpdates;
    Button btnCancelar;
    TextView tvInstallVersion;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_self);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Text= "Old";
        Text= "New";


        ApkName = "app-debug.apk";//"Test1.apk";// //"DownLoadOnSDcard_01.apk"; //
        AppName = "C O S M O S";//"Test1"; //

        BuildVersionPath = "http://domimtz.synology.me/Version.txt";
        PackageName = "package:domy.com.relevospm"; //"package:com.Test1".toString();
        // urlpath = "http://domimtz.synology.me/"+ Text +"_Apk/" + ApkName;
         urlpath = "http://domimtz.synology.me/" + ApkName;


        String versionNameApp = BuildConfig.VERSION_NAME; //1.2
        int versionCodeApp = BuildConfig.VERSION_CODE; //3

        tvApkStatus =(TextView)findViewById(R.id.tvApkStatus);
        tvApkStatus.setText("Name " + versionNameApp +" Code " + versionCodeApp );

        GetVersionFromServer(BuildVersionPath);

        tvInstallVersion = (TextView)findViewById(R.id.tvInstallVersion);
        tvInstallVersion.setText("Name " + VersionName + " Code " + VersionCode);

        btnCheckUpdates =(Button)findViewById(R.id.btnUpdates);
        btnCheckUpdates.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0)

            {
                GetVersionFromServer(BuildVersionPath);

              tvInstallVersion.setText("Name " + VersionName + " Code " + VersionCode);

                if(checkInstalledApp(AppName)) {
                    Toast.makeText(getApplicationContext(), "Application Found " + AppName, Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(getApplicationContext(), "Application Not Found. "+ AppName, Toast.LENGTH_SHORT).show();
                }
            }


        });

        btnCancelar =(Button)findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0)

            {
                finish();
            }
        });}

            private Boolean checkInstalledApp(String appName) {
                return getPackages(appName);
            }

            // Get Information about Only Specific application which is Install on Device.
            public String getInstallPackageVersionInfo(String appName) {
                String InstallVersion = "";
                ArrayList<PInfo> apps = getInstalledApps(false); /* false = no system packages */
                final int max = apps.size();
                for (int i = 0; i < max; i++) {
                    //apps.get(i).prettyPrint();
                    if (apps.get(i).appname.equals(appName)) {
                        InstallVersion = "Install Version Code: " + apps.get(i).versionCode +
                                " Version Name: " + apps.get(i).versionName;
                        break;
                    }
                }

                return InstallVersion;
            }

            private Boolean getPackages(String appName) {
                Boolean isInstalled = false;
                ArrayList<PInfo> apps = getInstalledApps(false); /* false = no system packages */
                final int max = apps.size();
                for (int i = 0; i < max; i++) {
                    //apps.get(i).prettyPrint();

                    if (apps.get(i).appname.equals(appName)) {
                /*if(apps.get(i).versionName.toString().contains(VersionName.toString()) == true &&
                        VersionCode == apps.get(i).versionCode)
                {
                    isInstalled = true;
                    Toast.makeText(getApplicationContext(),
                            "Code Match", Toast.LENGTH_SHORT).show();
                    openMyDialog();
                }*/
                        if (VersionCode > apps.get(i).versionCode) {
                            isInstalled = true;

                    /*Toast.makeText(getApplicationContext(),
                            "Install Code is Less.!", Toast.LENGTH_SHORT).show();*/

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            //SelfInstall01Activity.this.finish(); Close The App.

                                            //   DownloadOnSDcard();
                                            //   InstallApplication();

                                            isStoragePermissionGranted();

                                            UpdateApp atualizaApp = new UpdateApp();
                                            atualizaApp.setContext(getApplicationContext());
                                            atualizaApp.execute("http://domy.asuscomm.com/app-debug.apk");

                                            //  UnInstallApplication(PackageName);

                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked

                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("Disponible nueva App.\n\n¿Quieres instalar ahora?...").setPositiveButton("Si Instalar.", dialogClickListener)
                                    .setNegativeButton("No Gracias.", dialogClickListener).show();

                        }
                        if (VersionCode <= apps.get(i).versionCode) {
                            isInstalled = true;
                    /*Toast.makeText(getApplicationContext(),
                            "Install Code is better.!", Toast.LENGTH_SHORT).show();*/

                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            //Yes button clicked
                                            //SelfInstall01Activity.this.finish(); Close The App.

                                            // DownloadOnSDcard();
                                            // InstallApplication();

                                            isStoragePermissionGranted();

                                            UpdateApp atualizaApp = new UpdateApp();
                                            atualizaApp.setContext(getApplicationContext());
                                            atualizaApp.execute("http://domy.asuscomm.com/app-debug.apk");

                                            //  UnInstallApplication(PackageName);

                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            //No button clicked

                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setMessage("NO Necesita Instalar.").setPositiveButton("Forza Instalacion", dialogClickListener)
                                    .setNegativeButton("Cancelar.", dialogClickListener).show();
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
                    //newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
                    res.add(newInfo);
                }
                return res;
            }

            public void UnInstallApplication(String packageName)// Specific package Name Uninstall.
            {
                //Uri packageURI = Uri.parse("package:com.CheckInstallApp");
                Uri packageURI = Uri.parse(packageName);
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
            }

            public void InstallApplication() {
                Uri packageURI = Uri.parse(PackageName);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, packageURI);

//      Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.setFlags(Intent.ACTION_PACKAGE_REPLACED);

                //intent.setAction(Settings. ACTION_APPLICATION_SETTINGS);

                intent.setDataAndType
                        (Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + ApkName)),
                                "application/vnd.android.package-archive");

                // Not open this Below Line Bcuz...
                ////intent.setClass(this, Project02Activity.class); // This Line Call Activity Recursively its dangerous.

                startActivity(intent);
            }

            public void GetVersionFromServer(String BuildVersionPath) {
                //this is the file you want to download from the remote server
                //path ="http://10.0.2.2:82/Version.txt";
                //this is the name of the local file you will create
                // version.txt contain Version Code = 2; \n Version name = 2.1;
                String s = "";

                URL u;
                try {
                    u = new URL(BuildVersionPath);
                    String str1;
                    String str2 = "";

                    //    HttpURLConnection c = (HttpURLConnection) u.openConnection();
                    //    c.setRequestMethod("GET");
                    //    c.setDoOutput(true);
                    //    c.connect();

                    // Toast.makeText(getApplicationContext(), "Http"+c.getContentEncoding(), Toast.LENGTH_SHORT).show();

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
                //
                Log.d("URLLL", s);
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

                VersionCode = Integer.parseInt(fields[0].toString());// .ToString() Return String Value.
                VersionName = fields[1].toString();


                //return true;
            }// Method End.

            // Download On My Mobile SDCard or Emulator.
            public void DownloadOnSDcard() {
                try {
                    URL url = new URL(urlpath); // Your given URL.

                    HttpURLConnection c = (HttpURLConnection) url.openConnection();
                    c.setRequestMethod("GET");
                    c.setDoOutput(true);
                    c.connect(); // Connection Complete here.!

                    //Toast.makeText(getApplicationContext(), "HttpURLConnection complete.", Toast.LENGTH_SHORT).show();

                    String PATH = Environment.getExternalStorageDirectory() + "/download/";
                    File file = new File(PATH); // PATH = /mnt/sdcard/download/
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File outputFile = new File(file, ApkName);
                    FileOutputStream fos = new FileOutputStream(outputFile);

                    //      Toast.makeText(getApplicationContext(), "SD Card Path: " + outputFile.toString(), Toast.LENGTH_SHORT).show();

                    InputStream is = c.getInputStream(); // Get from Server and Catch In Input Stream Object.

                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1); // Write In FileOutputStream.
                    }
                    fos.close();
                    is.close();//till here, it works fine - .apk is download to my sdcard in download file.
                    // So plz Check in DDMS tab and Select your Emualtor.

                    //Toast.makeText(getApplicationContext(), "Download Complete on SD Card.!", Toast.LENGTH_SHORT).show();
                    //download the APK to sdcard then fire the Intent.
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Error! " +
                            e.toString(), Toast.LENGTH_LONG).show();
                }
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
        }