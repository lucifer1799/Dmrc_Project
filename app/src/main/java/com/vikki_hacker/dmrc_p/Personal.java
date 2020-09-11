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

public class Personal extends AppCompatActivity {
    EditText edit_name;
    EditText edit_fname;
    EditText edit_mname;
    EditText edit_age;
    EditText edit_sex;
    EditText edit_add;
    Button btn_next;
    Button btn_submit;
    private static final String REGISTER_URL="http://comfiest-tide.000webhostapp.com/Personal.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        edit_name = (EditText) findViewById(R.id.id_name);
        edit_fname = (EditText) findViewById(R.id.id_fname);
        edit_mname = (EditText) findViewById(R.id.id_mname);
        edit_age = (EditText) findViewById(R.id.id_age);
        edit_sex = (EditText) findViewById(R.id.id_sex);
        edit_add = (EditText) findViewById(R.id.id_add);
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

                Intent i = new Intent(Personal.this,Orgnization.class);
                startActivity(i);

            }
        });
    }

    private void registerUser() {
        String name = edit_name.getText().toString().trim().toLowerCase();
        String fname = edit_fname.getText().toString().trim().toLowerCase();
        String mname = edit_mname.getText().toString().trim().toLowerCase();
        String age = edit_add.getText().toString().trim().toLowerCase();
        String sex = edit_sex.getText().toString().trim().toLowerCase();
        String add = edit_add.getText().toString().trim().toLowerCase();
        register(name,fname,mname,age,sex,add);
    }

    private void register(String name, String fname, String mname,String age , String sex,String add){
        String urlSuffix = "?name=" + name + "&fname=" + fname + "&mname=" + mname + "&age=" + age + "&sex=" + sex + "&ad=" + add;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Personal.this, "Please Wait", null, true, true);
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