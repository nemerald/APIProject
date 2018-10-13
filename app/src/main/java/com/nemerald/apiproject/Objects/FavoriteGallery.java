package com.nemerald.apiproject.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavoriteGallery extends Gallery {
    private ArrayList<Picture> favoritePictureArrayList = new ArrayList<>();

    public void addFavoritePictureToGalleryList(Picture favoritePicture){
        favoritePictureArrayList.add(favoritePicture);
    }
    public void removeFavoritePictureInGallery(Picture picture){
        favoritePictureArrayList.remove(picture);
    }
    public void addFavoritePictureToGallery(JSONObject pictureObject){
        try {
            addFavoritePictureToGalleryList(new Picture(pictureObject.getString("id"), pictureObject.getString("title"),
                                            pictureObject.getString("farm"), pictureObject.getString("server"),
                                            pictureObject.getString("secret"), true));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Picture> getFavoritePictureArrayList() {
        return favoritePictureArrayList;
    }
}
