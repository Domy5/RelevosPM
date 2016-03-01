package domy.com.relevospm.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import domy.com.relevospm.R;
import domy.com.relevospm.Utiles.JSONParser2;

public class Register extends Activity implements OnClickListener {
    //testing on Emulator:
    private static final String REGISTER_URL = "http://domimtz.synology.me/bd/register.php";
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private static final Set<String> DNE = new HashSet<String>(Arrays.asList(
            new String[] { "0","02660", "02870", "02998", "03074", "03289", "03906", "04003",
            "04105", "04114", "04252", "04265", "04313", "04324", "04814", "04839", "04870",
            "04936", "05033", "05122", "05134", "05139", "05160", "05201", "05238", "05239",
            "05242", "05244", "05268", "05291", "05293", "05538", "05540", "05550", "05557",
            "05740", "15279", "15315", "15336", "15346", "15354", "15355", "15373", "15492",
            "15591", "15594", "15597", "15704", "15757", "15814", "15906", "15908", "15910",
            "15944", "15974", "16049", "16142", "16151", "16311", "16484", "16589", "16637",
            "16955", "17074", "17233", "17367", "17477", "17904", "18203", "18267", "18316",
            "18327", "18352", "18366", "18647", "18951"}));

    // JSON parser class
    JSONParser2 jsonParser2 = new JSONParser2();

    //si lo trabajan de manera local en xxx.xxx.x.x va su ip local
    // private static final String REGISTER_URL = "http://xxx.xxx.x.x:1234/cas/register.php";
    private EditText user, pass;
    private Button mRegister;
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);

        mRegister = (Button) findViewById(R.id.register);
        mRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if(DNE.contains(user.getText().toString())){

        new CreateUser(user.getText().toString(),pass.getText().toString()).execute();

        }
        else{
            Toast toast1 =
            Toast.makeText(getApplicationContext(),
                            "No es un Numero valido "

                    , Toast.LENGTH_LONG);

            toast1.setGravity(Gravity.CENTER,0,0);

            toast1.show();
        }

    }

    class CreateUser extends AsyncTask<String, String, String> {

        String username;
        String password;


        public CreateUser(String username1, String password1) {
            username = username1;
            password = password1;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creando Usuario en BD...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            try {
                // Building Parameters
               // List params = new ArrayList();
               // params.add(new BasicNameValuePair("username", username));
               // params.add(new BasicNameValuePair("password", password));

                HashMap<String, String> params = new HashMap<>();
              //  params.put("name", args[0]);
             //   params.put("password", args[1]);

                params.put("username", username);
                params.put("password", password);

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser2.makeHttpRequest(
                        REGISTER_URL, "POST", params);

                // full json response
                Log.d("intento de registrarse", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Usuario Creado!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Registo Fallido!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}