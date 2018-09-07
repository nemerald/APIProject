package com.nemerald.apiproject.Helpers;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nemerald.apiproject.MainActivity;
import com.nemerald.apiproject.Objects.Flickr;
import com.nemerald.apiproject.Objects.Gallery;

import org.json.JSONException;
import org.json.JSONObject;

import static com.nemerald.apiproject.MainActivity.getContext;

public class RequestHelper {

    Context context = getContext();
    private RequestHelperListener requestHelperListener;

    public interface RequestHelperListener {
        void onDataLoaded(JSONObject response);
    }
    public RequestHelper(){
        this.requestHelperListener = null;
        makeGalleryRequest();
    }
    public void setRequestHelperListener(RequestHelperListener requestHelperListener) {
        this.requestHelperListener = requestHelperListener;
    }

    public void makeGalleryRequest(){

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest req = new JsonObjectRequest(new Flickr().getUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        requestHelperListener.onDataLoaded(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(req);
    }
}
