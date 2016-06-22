package domy.com.relevospm.Utiles;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//http://www.iteramos.com/pregunta/6334/android-instalar-apk-mediante-programacion

public class UpdateApp extends AsyncTask<String, Void, Void> {
    private static final String TAG = "permisos";
    private Context context;
    private Activity activity;
    ProgressDialog pDialog;

    public void setContext(Context context) {
        this.context = context;

        pDialog = new ProgressDialog(context);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Procesando la informacion");
        pDialog.setMessage("Descargando...");

    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... arg0) {
        try {
            URL url = new URL(arg0[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            String PATH = "/mnt/sdcard/Download/";

            Log.v("descargarr", PATH);
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, "app-debug.apk");
            if (outputFile.exists()) {
                outputFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(outputFile);

            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            Utiles.isStoragePermissionGranted(context,activity);  // todo tengo que darle una vuelta a los permisos

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/Download/app-debug.apk")), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
            context.startActivity(intent);


        } catch (Exception e) {
            Log.e("UpdateAPP", "Update error! " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pDialog.dismiss();
    }
}