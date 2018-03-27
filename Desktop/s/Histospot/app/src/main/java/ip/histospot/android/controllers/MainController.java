package ip.histospot.android.controllers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import ip.histospot.android.utilities.ApiRequest;

public final class MainController {


    public final static String API_ADRESS = "http://89.35.229.23:8080/Histospot/api/";

    private static final MainController instance = new MainController();

    private MainController() {
    }

    public static MainController getInstance() {
        return instance;
    }

    public void login(Context context, Map<String, String> headers, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.POST, MainController.API_ADRESS + "login", headers, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }

    public void profile(Context context, Map<String, String> headers, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        ApiRequest request = new ApiRequest(Request.Method.GET, MainController.API_ADRESS + "profile", headers, listener, errorListener);

        Volley.newRequestQueue(context).add(request);
    }
}
