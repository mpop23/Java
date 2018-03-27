package ip.histospot.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.TreeMap;

import ip.histospot.android.R;
import ip.histospot.android.SearchActivity;
import ip.histospot.android.activities.LoginActivity;
import ip.histospot.android.activities.MainActivity;
import ip.histospot.android.controllers.MainController;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ImageView poza_profil;
    private ImageButton search;
    private static final int REQUEST_PHOTO_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        poza_profil = rootView.findViewById(R.id.poza_profil);

        poza_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Schimbare poza profil"), REQUEST_PHOTO_CODE);
            }
        });

        search=rootView.findViewById(R.id.searchB);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_page();

            }
        });

        int userId = getActivity().getIntent().getIntExtra(LoginActivity.USER_ID_EXTRA, 0);
        TreeMap<String, String> headers = new TreeMap<>();
        headers.put("id", "" + userId);
        MainController.getInstance().profile(super.getContext(), headers, this, this);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_CODE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            poza_profil.setImageURI(selectedImageUri);
        }
    }


    @Override
    public void onResponse(JSONObject response) {
        try {
            TextView nume = super.getView().findViewById(R.id.nume_utilizator);
            nume.setText(response.getString("firstName"));

            TextView prenume = super.getView().findViewById(R.id.prenume_utilizator);
            prenume.setText(response.getString("lastName"));

            byte[] photoBytes = Base64.decode(response.getString("photo"), Base64.DEFAULT);

            ImageView image = this.getView().findViewById(R.id.poza_profil);
            Bitmap decoded = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
            image.setImageBitmap(decoded);
        } catch (JSONException e) {
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }


    public void search_page(){

        startActivity(new Intent(this.getContext(), SearchActivity.class));

    }
}
