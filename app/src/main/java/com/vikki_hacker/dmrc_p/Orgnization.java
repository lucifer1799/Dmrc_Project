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

public class Orgnization extends AppCompatActivity {
    EditText edit_ugname;
    EditText edit_ugbranch;
    EditText edit_ugorg;
    EditText edit_exwords;
    EditText edit_extime;
    EditText edit_hobbies;
    Button btn_next;
    Button btn_submit;
    private static final String REGISTER_URL="http://comfiest-tide.000webhostapp.com/Orgnizational.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgnization);
        edit_ugname = (EditText) findViewById(R.id.id_ugname);
        edit_ugbranch = (EditText) findViewById(R.id.id_branch);
        edit_ugorg = (EditText) findViewById(R.id.id_orgnization);
        edit_exwords = (EditText) findViewById(R.id.id_experience);
        edit_extime = (EditText) findViewById(R.id.id_experience);
        edit_hobbies = (EditText) findViewById(R.id.id_hobbies);
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

                Intent i = new Intent(Orgnization.this,Contact_info.class);
                startActivity(i);

            }
        });
    }

    private void registerUser() {
        String name = edit_ugname.getText().toString().trim().toLowerCase();
        String fname = edit_ugbranch.getText().toString().trim().toLowerCase();
        String mname = edit_ugorg.getText().toString().trim().toLowerCase();
        String age = edit_exwords.getText().toString().trim().toLowerCase();
        String sex = edit_extime.getText().toString().trim().toLowerCase();
        String add = edit_hobbies.getText().toString().trim().toLowerCase();
        register(name,fname,mname,age,sex,add);
    }

    private void register(String name, String fname, String mname,String age , String sex,String add){
        String urlSuffix = "?ug_name=" + name + "&ug_branch=" + fname + "&ug_org=" + mname + "&ex_words=" + age + "&ex_time=" + sex + "&hobbies=" + add;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Orgnization.this, "Please Wait", null, true, true);
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