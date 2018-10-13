package com.nemerald.apiproject.Objects;

import java.util.ArrayList;

public class FavoriteGallery extends Gallery {
    private ArrayList<Picture> favoritePictureArrayList = new ArrayList<>();

    public void addFavoritePictureToGalleryList(Picture favoritePicture){
        favoritePictureArrayList.add(favoritePicture);
    }
    public void removeFavoritePictureInGallery(Picture picture){
        favoritePictureArrayList.remove(picture);
    }
    public void addFavoritePicturesToGallery(ArrayList<Picture> pictureArrayList){
        for (Picture picture:pictureArrayList) {
            if(picture.isFavorite()){
                addFavoritePictureToGalleryList(picture);
            }
        }
    }
    public ArrayList<Picture> getFavoritePictureArrayList() {
        return favoritePictureArrayList;
    }
}
