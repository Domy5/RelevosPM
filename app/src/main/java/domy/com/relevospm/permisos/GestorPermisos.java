package domy.com.relevospm.permisos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by manana on 22/06/2016.
 */
public class GestorPermisos {

    public static boolean checkearPermiso(Context context,String permiso){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED ) {
                //Requiere permisos para Android 6.0, así que le solititamos al usuario que nos los
                //proporcione
                //Esto mostrará un dialogo pidiendonos que aceptemos o declinemos el permiso
                //y el resultado será enviado a la función de callbac
                //onRequestPermissionsResult con el codigo de petición que le hayamos enviado
                //(en este caso el 100)
                Log.e("Location", "No se tienen permisos necesarios!, se requieren.");
                return false;
            } else {
                Log.i("Location", "Permisos necesarios OK!.");
                return true;
            }
        }else {
            Log.v("MainActivity", "Versión de SDK inferior a la 6.0, los permisos ya se dieron cuando se instalo la aplicación");
            return true;
        }
    }

/*
    public static void solicitarPermiso(Activity activity,String permiso, int requestCode){

        ActivityCompat.requestPermissions(activity, new String[]{permiso}, requestCode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                invocarLlamadasProvider();
            } else {
                Toast.makeText(this, "Hasta que no des permisos de llamadas no se podrán mostrar "
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
