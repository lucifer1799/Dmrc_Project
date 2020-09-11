package com.vikki_hacker.dmrc_p;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Contact_info extends AppCompatActivity {
    EditText edit_altemail;
    EditText edit_mono;
    EditText edit_tphoneno;
    EditText edit_state;
    EditText edit_lcity;
    EditText edit_pincode;
    Button btn_next;
    Button btn_submit;
    private static final String REGISTER_URL="http://comfiest-tide.000webhostapp.com/Contact.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        edit_altemail = (EditText) findViewById(R.id.id_altemail);
        edit_mono = (EditText) findViewById(R.id.id_mono);
        edit_tphoneno = (EditText) findViewById(R.id.id_tphoneno);
        edit_state = (EditText) findViewById(R.id.id_state);
        edit_lcity = (EditText) findViewById(R.id.id_lcity);
        edit_pincode = (EditText) findViewById(R.id.id_pin);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        btn_next=(Button)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Contact_info.this,Signature.class);
                startActivity(i);

            }
        });
    }

    private void registerUser() {
        String name = edit_altemail.getText().toString().trim().toLowerCase();
        String fname = edit_mono.getText().toString().trim().toLowerCase();
        String mname = edit_tphoneno.getText().toString().trim().toLowerCase();
        String age = edit_state.getText().toString().trim().toLowerCase();
        String sex = edit_lcity.getText().toString().trim().toLowerCase();
        String add = edit_pincode.getText().toString().trim().toLowerCase();
        register(name,fname,mname,age,sex,add);
    }

    private void register(String name, String fname, String mname,String age , String sex,String add){
        String urlSuffix = "?alt_email=" + name + "&mo_no=" + fname + "&tphone_no=" + mname + "&state=" + age + "&l_city=" + sex + "&pin_code=" + add;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Contact_info.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Uploaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferReader=null;
                try {
                    URL url=new URL(REGISTER_URL+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();
                    return  result;

                }catch (Exception e){
                    return null;
                }
            }

        }
        RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix);
    }
}