package com.nemerald.apiproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nemerald.apiproject.Adapters.RecyclerViewAdapter;
import com.nemerald.apiproject.Dialogs.ShowPictureDialogFragment;
import com.nemerald.apiproject.Objects.FavoriteGallery;
import com.nemerald.apiproject.Objects.Picture;

public class FavoriteFragment extends Fragment{

    TextView galleryTitle;
    RecyclerView recyclerView;
    FavoriteGallery favoriteGallery;
    FragmentCommunicator mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentCommunicator) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentCommunicator");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState!=null)
        {
            //handle your data on screen rotation
            galleryTitle = view.findViewById(R.id.favoriteGalleryTitle);
            recyclerView = view.findViewById(R.id.favorite_fragment_recycler_view);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(llm);

            favoriteGallery = (FavoriteGallery) savedInstanceState.getSerializable(getString(R.string.favorite_gallery_tag));
            if(favoriteGallery!=null){
                galleryTitle.setText(favoriteGallery.getGalleryTitle());
            }
            else{
                favoriteGallery = mCallback.fetchFavoriteGallery();
                galleryTitle.setText(favoriteGallery.getGalleryTitle());
            }

        }else{
            galleryTitle = view.findViewById(R.id.favoriteGalleryTitle);
            recyclerView = view.findViewById(R.id.favorite_fragment_recycler_view);

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(llm);

            favoriteGallery = mCallback.fetchFavoriteGallery();
            galleryTitle.setText(favoriteGallery.getGalleryTitle());
        }

        initializeAdapter();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCallback.saveFavoriteGallery(favoriteGallery);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getString(R.string.favorite_gallery_tag), favoriteGallery);
    }
    public void initializeAdapter() {
        recyclerView.setAdapter(new RecyclerViewAdapter(favoriteGallery.getFavoritePictureArrayList(), getContext(), new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Picture picture) {
                ShowPictureDialogFragment newFragment = ShowPictureDialogFragment.newInstance(picture, favoriteGallery);
                newFragment.show(getFragmentManager(), "dialog");
            }
        }));
    }
    public void updateFavoriteList(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
