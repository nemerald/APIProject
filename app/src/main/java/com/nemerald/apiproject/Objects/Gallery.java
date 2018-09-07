package com.nemerald.apiproject;

import java.util.ArrayList;

public class Gallery {
    private String galleryTitle;
    private ArrayList<Picture> pictureArrayList;

    public Gallery(){}

    public Gallery(String title){
        this.galleryTitle = galleryTitle;
    }

    public String getGalleryTitle() {
        return galleryTitle;
    }

    public void addPictureToGalleryList(Picture picture){
        pictureArrayList.add(picture);
    }

    public ArrayList<Picture> getPictureArrayList() {
        return pictureArrayList;
    }
}
