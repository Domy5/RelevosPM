package domy.com.relevospm.Utiles;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import domy.com.relevospm.MainActivity;


public class Utiles {


    ProgressDialog progressDialog;

    public static boolean isNumeric(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        int i = 0;
        int stringLength = string.length();
        if (string.charAt(0) == '-') {
            if (stringLength > 1) {
                i++;
            } else {
                return false;
            }
        }
        if (!Character.isDigit(string.charAt(i))
                || !Character.isDigit(string.charAt(stringLength - 1))) {
            return false;
        }
        i++;
        stringLength--;
        if (i >= stringLength) {
            return true;
        }
        for (; i < stringLength; i++) {
            if (!Character.isDigit(string.charAt(i))
                    && string.charAt(i) != '.') {
                return false;
            }
        }
        return true;
    }

 /*   public static boolean isStoragePermissionGranted(Context context, Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }


  /  }*/

    private class TareaDialogo extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress();
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.v("MainActivity", "descargando");
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                progressDialog.dismiss();
            }
        }

    }

    public static boolean isOnline(Context c) {

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static String periodo_Mes(String dia, String mes) {

        int d = Integer.parseInt(dia);
        int m = Integer.parseInt(mes);

        String a000_mes = null;

        if (m == 1) a000_mes = "a010_Enero";
        else if (m == 2) a000_mes = "a020_Febrero";
        else if (m == 3) a000_mes = "a030_Marzo";
        else if (m == 4) a000_mes = "a040_Abril";
        else if (m == 5) a000_mes = "a050_Mayo";
        else if (m == 6) a000_mes = "a060_Junio";
        else if (m == 7 && d < 23) a000_mes = "a071_V1";
        else if (m == 7 && d > 22) a000_mes = "a072_V2";
        else if (m == 8 && d < 14) a000_mes = "a072_V2";
        else if (m == 8 && d > 13) a000_mes = "a073_V3";
        else if (m == 9 && d < 5) a000_mes = "a073_V3";
        else if (m == 9 && d > 4 && d < 27) a000_mes = "a074_V4";
        else if (m == 9 && d > 25) a000_mes = "a090_Septiembre";
        else if (m == 10) a000_mes = "a100_Octubre";
        else if (m == 11) a000_mes = "a110_Noviembre";
        else if (m == 12) a000_mes = "a120_Diciembre";

        return a000_mes;
    }


}
