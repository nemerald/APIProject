package com.nemerald.apiproject.Objects;

public class FavoriteGallery extends Gallery {

    public void removeFavoritePictureInGallery(Picture picture){
        pictureArrayList.remove(picture);
    }
}
