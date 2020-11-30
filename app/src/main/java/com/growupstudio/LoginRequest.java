package com.growupstudio;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static String URL_LOGIN = "http://192.168.0.3/growupstudio/login.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Request.Method.POST, URL_LOGIN ,listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }
    @Override
    protected Map<String,String> getParams(){
        return params;
    }
}
