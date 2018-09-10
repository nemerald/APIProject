package com.nemerald.apiproject.Objects;

public class FavoriteGallery extends Gallery {

    public void addFavoritePictureToGallery(Picture picture){
        pictureArrayList.add(picture);
    }
    public void removeFavoritePictureInGallery(Picture picture){
        pictureArrayList.remove(picture);
    }
}
