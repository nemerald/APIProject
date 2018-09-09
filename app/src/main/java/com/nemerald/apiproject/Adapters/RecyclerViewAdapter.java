package com.nemerald.apiproject.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nemerald.apiproject.MainActivity;
import com.nemerald.apiproject.Objects.Picture;
import com.nemerald.apiproject.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import static com.nemerald.apiproject.MainActivity.getContext;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.FieldViewHolder> {

    public static class FieldViewHolder extends RecyclerView.ViewHolder{

        //TextView galleryPhotoTitle;
        ImageView galleryPhoto;

        public FieldViewHolder(View itemView) {
            super(itemView);
            galleryPhoto = itemView.findViewById(R.id.gallery_photo);
            //galleryPhotoTitle = itemView.findViewById(R.id.gallery_photo_title);
        }
        public void bind(final Picture picture, final OnItemClickListener listener) {
            //name.setText(item.name);
            //Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
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

    ArrayList<Picture> pictureArrayList;
    OnItemClickListener listener;

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

        fieldViewHolder.galleryPhoto.setImageBitmap(getBitmapFromURL(pictureArrayList.get(position).getPictureUrl(pictureArrayList.get(position))));
        //fieldViewHolder.galleryPhotoTitle.setText(pictureArrayList.get(position).getPicTitle());
        fieldViewHolder.bind(pictureArrayList.get(position), listener);
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

            int imageWidth = myBitmap.getWidth();
            int imageHeight = myBitmap.getHeight();
            int newWidth = getScreenWidth();
            float scaleFactor = (float)newWidth/(float)imageWidth;
            int newHeight = (int)(imageHeight * scaleFactor);

            myBitmap = Bitmap.createScaledBitmap(myBitmap, newWidth, newHeight, true);

            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getScreenWidth() {
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        return width;
    }
}
