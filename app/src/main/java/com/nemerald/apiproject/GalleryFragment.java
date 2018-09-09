package com.nemerald.apiproject;

import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.nemerald.apiproject.Adapters.RecyclerViewAdapter;
import com.nemerald.apiproject.Helpers.HTTPRequester;
import com.nemerald.apiproject.Objects.Flickr;
import com.nemerald.apiproject.Objects.Gallery;
import com.nemerald.apiproject.Objects.Picture;

import org.json.JSONObject;

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
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            progressBar.setVisibility(ProgressBar.VISIBLE);
            galleryTitle.setText(getString(R.string.loading_pictures));

            Flickr flickr = new Flickr();
            flickr.setGalleryId("72157678340527534");

            final HTTPRequester httpRequester = new HTTPRequester(flickr);
            httpRequester.setHTTPRequesterListener(new HTTPRequester.HTTPRequesterListener() {
                @Override
                public void onDataLoaded(Object response) {
                    if(!response.getClass().isInstance(VolleyError.class)){
                        initGalleryData((JSONObject) response);
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                    else{
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Log.d(getString(R.string.error_message),((VolleyError) response).getMessage());
                        Toast.makeText(getContext(), ((VolleyError) response).getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
                Toast.makeText(getContext(), picture.getPicTitle(), Toast.LENGTH_SHORT).show();
            }
        }));

    }
}
