package ip.histospot.android.utilities;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ApiRequest extends JsonObjectRequest {

    private Map<String, String> headers = new LinkedHashMap<>();

    public ApiRequest(int method, String url, Map<String, String> headers, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this(method, url, listener, errorListener);
        this.headers.putAll(headers);
    }

    public ApiRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.headers;
    }
}
