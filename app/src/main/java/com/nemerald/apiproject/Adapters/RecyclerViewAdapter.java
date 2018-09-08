package com.nemerald.apiproject.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nemerald.apiproject.Objects.Picture;
import com.nemerald.apiproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.FieldViewHolder> {

    public static class FieldViewHolder extends RecyclerView.ViewHolder{

        TextView galleryPhotoTitle;
        ImageView galleryPhoto;

        public FieldViewHolder(View itemView) {
            super(itemView);

            galleryPhoto = itemView.findViewById(R.id.gallery_photo);
            galleryPhotoTitle = itemView.findViewById(R.id.gallery_photo_title);

        }
    }
    ArrayList<Picture> pictureArrayList;

    public RecyclerViewAdapter(ArrayList<Picture> pictureArrayList){
        this.pictureArrayList = pictureArrayList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        FieldViewHolder fvh = new FieldViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(FieldViewHolder fieldViewHolder, int position) {

        fieldViewHolder.galleryPhoto.setImageBitmap(getBitmapFromURL(pictureArrayList.get(position).getPictureUrl(pictureArrayList.get(position))));
        fieldViewHolder.galleryPhotoTitle.setText(pictureArrayList.get(position).getPicTitle());
    }

    @Override
    public int getItemCount() {
        return pictureArrayList.size();
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
