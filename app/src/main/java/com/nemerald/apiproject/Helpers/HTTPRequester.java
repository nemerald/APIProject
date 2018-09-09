package com.nemerald.apiproject.Helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nemerald.apiproject.Objects.Flickr;
import com.nemerald.apiproject.R;

import org.json.JSONObject;

import static com.nemerald.apiproject.MainActivity.getContext;

public class HTTPRequester {

    private int mCurrentRetryCount;
    private int mMaxRetryCount = 2;


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
                Log.d("Error", error.getMessage());
            }
        });
        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                mCurrentRetryCount++;
                Toast.makeText(context, getContext().getString(R.string.retrying), Toast.LENGTH_SHORT).show();
                if(mCurrentRetryCount==mMaxRetryCount){
                    Toast.makeText(context, getContext().getString(R.string.error_max_retry_exceeded), Toast.LENGTH_SHORT).show();
                }
                Log.d("Error", error.getMessage());
            }
        });

        requestQueue.add(req);
    }
}
