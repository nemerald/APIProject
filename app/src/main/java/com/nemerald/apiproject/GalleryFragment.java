package com.nemerald.apiproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.nemerald.apiproject.Adapters.RecyclerViewAdapter;
import com.nemerald.apiproject.Dialogs.ShowPictureDialogFragment;
import com.nemerald.apiproject.Helpers.HTTPRequester;
import com.nemerald.apiproject.Objects.Flickr;
import com.nemerald.apiproject.Objects.Gallery;
import com.nemerald.apiproject.Objects.Picture;

import org.json.JSONObject;

import static com.nemerald.apiproject.MainActivity.getContext;

public class GalleryFragment extends Fragment {

    TextView galleryTitle;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Gallery gallery;

    public GalleryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        galleryTitle = view.findViewById(R.id.galleryTitle);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            galleryTitle.setText(getString(R.string.loading_pictures));

            Flickr flickr = new Flickr();
            flickr.setGalleryId("72157678340527534");

            final Handler handler = new Handler();

            final HTTPRequester httpRequester = new HTTPRequester(flickr, handler);
            httpRequester.setHTTPRequesterListener(new HTTPRequester.HTTPRequesterListener() {
                @Override
                public void onDataLoaded(final Object response) {
                    if (response instanceof VolleyError) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(((VolleyError) response).getMessage()!=null){
                                    Toast.makeText(getContext(), errorMessageConstructor(((VolleyError) response)), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getContext(), getString(R.string.unkown_error), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        galleryTitle.setText(getString(R.string.no_pictures_loaded));
                    } else {
                        initGalleryData((JSONObject) response);
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                }

                @Override
                public void onRetrying(final String message) {
                    galleryTitle.setText(message);
                }
            });
        }
    }
    private void initGalleryData(JSONObject response) {
        gallery = new Gallery(response);
        galleryTitle.setText(gallery.getGalleryTitle());
        initializeAdapter();

    }
    private void initializeAdapter() {
        recyclerView.setAdapter(new RecyclerViewAdapter(gallery.getPictureArrayList(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Picture picture) {
                ShowPictureDialogFragment newFragment = ShowPictureDialogFragment.newInstance(picture);
                newFragment.show(getFragmentManager(), "dialog");
            }
        }));
    }
    private String errorMessageConstructor(VolleyError error) {
        //Constructing error message depending on the information availible from the Class. Some of the errors holds networks status code, some dont.
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            return error.getMessage();
        } else if (error instanceof AuthFailureError) {
            return error.getMessage();
        } else if (error instanceof ServerError) {
            return error.getMessage();
        } else if (error instanceof NetworkError) {
            return error.getMessage();
        } else if (error instanceof ParseError) {
            return error.getMessage();
        }
        return error.getMessage();
    }
}
