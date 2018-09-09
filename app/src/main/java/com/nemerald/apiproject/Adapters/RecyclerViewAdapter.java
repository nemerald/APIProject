package com.nemerald.apiproject.Adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nemerald.apiproject.Objects.Picture;
import com.nemerald.apiproject.R;

import java.util.ArrayList;

import static com.nemerald.apiproject.Helpers.BitmapHelper.getBitmapFromURL;
import static com.nemerald.apiproject.MainActivity.getCache;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.FieldViewHolder> {

    ArrayList<Picture> pictureArrayList;
    OnItemClickListener listener;

    public static class FieldViewHolder extends RecyclerView.ViewHolder{

        ImageView galleryPhoto;

        public FieldViewHolder(View itemView) {
            super(itemView);
            galleryPhoto = itemView.findViewById(R.id.gallery_photo);
        }
        public void bind(final Picture picture, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(picture);
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Picture picture);
    }

    public RecyclerViewAdapter(ArrayList<Picture> pictureArrayList, OnItemClickListener listener){
        this.pictureArrayList = pictureArrayList;
        this.listener = listener;
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

        Bitmap bitmap = getCache().getBitmapFromMemCache(pictureArrayList.get(position).getPicId());
        if(bitmap != null){
            fieldViewHolder.galleryPhoto.setImageBitmap(bitmap);
        }
        else{
            bitmap = getBitmapFromURL(pictureArrayList.get(position));
            fieldViewHolder.galleryPhoto.setImageBitmap(bitmap);
        }

        fieldViewHolder.bind(pictureArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return pictureArrayList.size();
    }


}
