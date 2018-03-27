package ip.histospot.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

import ip.histospot.android.R;
import ip.histospot.android.controllers.MainController;

// https://lh3.googleusercontent.com/-XlEknXHFzzs/AAAAAAAAAAI/AAAAAAAAAGg/tFeHdLx5Nag/photo.jpg
public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    public static final String USER_ID_EXTRA = "userId";
    private static final int RC_SIGN_IN = 41394;
    private GoogleSignInClient gsic;
    private Button noInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        this.gsic = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        this.noInternet = super.findViewById(R.id.no_internet);
        this.noInternet.setOnClickListener(l -> this.signIn());

        if (account != null) {
            this.updateProfile(account);
            return;
        }

        signIn();
    }

    private void signIn() {
        Intent signInIntent = this.gsic.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            this.updateProfile(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GOOGLE_LOGIN", "signInResult:failed code=" + e.getStatusCode());
            this.signIn();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void updateProfile(GoogleSignInAccount acct) {
        if (acct != null) {
            TreeMap<String, String> headers = new TreeMap<>();
            headers.put("googleId", acct.getId());
            headers.put("email", acct.getEmail());
            headers.put("firstName", acct.getFamilyName());
            headers.put("lastName", acct.getGivenName());
            if (acct.getPhotoUrl() != null) {
                headers.put("photoUrl", acct.getPhotoUrl().toString());
            }

            MainController.getInstance().login(this, headers, this, this);
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(LoginActivity.USER_ID_EXTRA, response.getInt("id"));
            startActivity(intent);
            super.finish();
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.noInternet.setVisibility(View.VISIBLE);
    }
}
