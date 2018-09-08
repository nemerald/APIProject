package com.nemerald.apiproject.Helpers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nemerald.apiproject.Objects.Flickr;

import org.json.JSONObject;

import static com.nemerald.apiproject.MainActivity.getContext;

public class HTTPRequester {

    Context context = getContext();
    private HTTPRequesterListener httpRequesterListener;

    public interface HTTPRequesterListener {
        void onDataLoaded(Object response);
    }
    public HTTPRequester(Flickr flickr){
        this.httpRequesterListener = null;
        makeGalleryRequest(flickr);
    }
    public void setHTTPRequesterListener(HTTPRequesterListener requestHelperListener) {
        this.httpRequesterListener = requestHelperListener;
    }

    public void makeGalleryRequest(Flickr flickr){

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest req = new JsonObjectRequest(flickr.getFullFlickrURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        httpRequesterListener.onDataLoaded(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                httpRequesterListener.onDataLoaded(error);
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(req);
    }
}
