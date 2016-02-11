package domy.com.relevospm.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domy.com.relevospm.Main_Seleccionar_Dia;
import domy.com.relevospm.R;

public class Login extends Activity implements OnClickListener {

    private static final String LOGIN_URL = "http://domimtz.synology.me/bd/login.php";
    // La respuesta del JSON es
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    // Clase JSONParser
    JSONParser2 jsonParser2 = new JSONParser2();


    // si trabajan de manera local "localhost" :
    // En windows tienen que ir, run CMD > ipconfig
    // buscar su IP
    // y poner de la siguiente manera
    // "http://xxx.xxx.x.x:1234/cas/login.php";
    private EditText user, pass;
    private Button mSubmit, mRegister;
    private ProgressDialog pDialog;

    private Switch mySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // setup input fields
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        mySwitch = (Switch) findViewById(R.id.switch1);

        SharedPreferences  preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        user.setText( preferencias.getString("Usuario",""));
        pass.setText( preferencias.getString("Password",""));
        mySwitch.setChecked( preferencias.getBoolean("Switch",false));

        // setup buttons
        mSubmit = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);

        // register listeners
        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);
      //  mySwitch.setOnClickListener(this);

        Boolean AutoLogin = getIntent().getBooleanExtra("AutoLogin",true);

        if (mySwitch.isChecked() && AutoLogin ){

            mSubmit.performClick();
        }


        //set the switch to ON
       // mySwitch.setChecked(true);
        //attach a listener to check for changes in state

        // esto no hace nada
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                }
            });

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login:
              //  new AttemptLogin().execute();

                new AttemptLogin(user.getText().toString(), pass.getText().toString(), mySwitch.isChecked()).execute();

                break;
            case R.id.register:
                Intent i = new Intent(this, Register.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        String username;
        String password;
        boolean myS;

        public AttemptLogin(String username1, String password1, boolean myS1) {
            username = username1;
            password = password1;
            myS = myS1;

        }

        @Override
        protected String doInBackground(String... args) {

            int success;

            try {
                // Building Parameters
            //    List params = new ArrayList();
            //    params.add(new BasicNameValuePair("username", username));
            //    params.add(new BasicNameValuePair("password", password));


                HashMap<String, String> params = new HashMap<>();
                // params.put("name", args[0]);
                // params.put("password", args[1]);

                params.put("username", username);
                params.put("password", password);

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser2.makeHttpRequest(LOGIN_URL, "POST",
                        params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    // save user data

                    SharedPreferences preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE);
                    Editor editor = preferencias.edit();
                    editor.putString("Usuario", username);
                    editor.putString("Password", password);
                    editor.putBoolean("Switch", myS);

                   // editor.commit();
                    editor.apply();

                    Intent i = new Intent(Login.this, Main_Seleccionar_Dia.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Intentando el Inicio de sesión...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }



        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }
        }


      //  @Override
      //  protected void onProgressUpdate(Void... values) {}

    }
}