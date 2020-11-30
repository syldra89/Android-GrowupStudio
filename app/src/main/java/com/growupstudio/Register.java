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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    //Declaro las variables
    private EditText username, password, email;
    private Button btnConfirm;
    private static String URL_REGISTER = "http://192.168.0.3/growupstudio/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Inicializo las variables
        username = (EditText)findViewById(R.id.txt_userR);
        password = (EditText)findViewById(R.id.txt_passwordR);
        email = (EditText)findViewById(R.id.txt_emailR);
        btnConfirm = (Button)findViewById(R.id.btn_registerR);

        //Hago un click listener en el boton Confirmar
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register(){

        //Declaro las variables de texto que voy a mandar a la BD
        final String username = this.username.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        //Mando una request con POST que me trae un String
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                //Si me responde la URL...
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //Si se conecta con la base...
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if (success.equals("1")){
                            Toast.makeText(Register.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        }

                        Intent i = new Intent(Register.this, MainActivity.class);
                        Register.this.startActivity(i);
                        Register.this.finish();

                    //Si no se puede conectar con la base...
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(Register.this, "Error "+e, Toast.LENGTH_SHORT).show();

                    }

                    }
                },
                //Si no me responde la URL...
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, "Error "+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params= new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
