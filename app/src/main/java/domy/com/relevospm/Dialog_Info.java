package domy.com.relevospm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Dialog_Info extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialogo_info);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        TextView name = (TextView) findViewById(R.id.codename);
        TextView version = (TextView) findViewById(R.id.codeversion);

        name.setText(   "Code Name    : " + versionName );
        version.setText("Code Version : " + versionCode);

    }
}


