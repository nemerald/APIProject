package com.nemerald.apiproject.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Gallery implements Serializable{

    private String galleryTitle;
    ArrayList<Picture> pictureArrayList = new ArrayList<>();

    public Gallery() {

    }
    public Gallery(JSONObject response){
        try {
            setGalleryTitle(response.getJSONObject("gallery").getJSONObject("title").getString("_content"));
            createGalleryPictureArray(response.getJSONObject("photos").getJSONArray("photo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createGalleryPictureArray(JSONArray dataArray) {
        for(int counter=0;counter<dataArray.length();counter++){
            try {
                JSONObject pictureData = dataArray.getJSONObject(counter);
                addPictureToGalleryList(new Picture(pictureData.getString("id"), pictureData.getString("title"),
                                                    pictureData.getString("farm"), pictureData.getString("server"),
                                                    pictureData.getString("owner"), pictureData.getString("secret")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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

    public void setGalleryTitle(String galleryTitle) {
        this.galleryTitle = galleryTitle;
    }

}
