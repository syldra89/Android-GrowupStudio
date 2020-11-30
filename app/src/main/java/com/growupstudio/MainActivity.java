package com.growupstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    // agregar implementation 'com.android.volley:volley:1.1.1' a gradle
    // agregar <uses-permission android:name="android.permission.INTERNET"></uses-permission> a manifest
    // agregar android:usesCleartextTraffic="true" a manifest
    private EditText _username,_password;
    private Button btnRegister, btnLogin;
    private static String URL_LOGIN = "http://192.168.0.3/growupstudio/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _username = (EditText)findViewById(R.id.txt_userL);
        _password = (EditText)findViewById(R.id.txt_passwordL);
        btnRegister = (Button)findViewById(R.id.btn_register);
        btnLogin = (Button)findViewById(R.id.btn_login);

        //Cuando clickeo el boton de registrar hace...
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
        //Cuando clickeo el boton de login hace...
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = _username.getText().toString().trim();
                String password = _password.getText().toString().trim();
                Toast.makeText(MainActivity.this,"Logueo correcto", Toast.LENGTH_SHORT).show();

                Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean ok = jsonObject.getBoolean("success");
                            if(ok==true){
                                Intent openControlPanel = new Intent(MainActivity.this, ControlPanelUser.class);
                                MainActivity.this.startActivity(openControlPanel);

                            }else{
                                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos"+response, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error "+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                LoginRequest lr = new LoginRequest(username, password, response);
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(lr);

            }
        });



    }

    /* =============================================================================================
    private void openControlPanel(final String username, final String password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){

                                for(int i = 0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String username = object.getString("username").trim();
                                    Toast.makeText(MainActivity.this, "Logueo correcto",
                                    Toast.LENGTH_SHORT).show();

                                }
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error "+e,
                            Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error "+error,
                        Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    } =========================================================================================== */

    private void openRegister(){
        Intent openRegister = new Intent(this, Register.class);
        startActivity(openRegister);
    }
}
