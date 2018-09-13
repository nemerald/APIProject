package com.nemerald.apiproject.Helpers;

import android.content.Context;
import android.os.Handler;
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

public class HTTPRequester {

    private int mCurrentRetryCount;
    private int mMaxRetryCount = 2;

    private HTTPRequesterListener httpRequesterListener;

    public interface HTTPRequesterListener {
        void onDataLoaded(Object response);
    }
    public HTTPRequester(Flickr flickr, final Handler handler, Context context){
        this.httpRequesterListener = null;
        makeGalleryRequest(flickr, handler, context);
    }
    public void setHTTPRequesterListener(HTTPRequesterListener requestHelperListener) {
        this.httpRequesterListener = requestHelperListener;
    }

    private void makeGalleryRequest(Flickr flickr, final Handler handler, final Context context){

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
                return 3000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                mCurrentRetryCount++;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getString(R.string.retrying), Toast.LENGTH_SHORT).show();
                    }
                });
                if(mCurrentRetryCount==mMaxRetryCount){
                  httpRequesterListener.onDataLoaded(error);
                }
            }
        });

        requestQueue.add(req);
    }
}
