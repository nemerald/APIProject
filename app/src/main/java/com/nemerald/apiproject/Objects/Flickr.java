package com.nemerald.apiproject.Objects;

public class Flickr implements IFlickr {

    private String galleryId = "72157678340527534";

    public String getFullFlickrURL(){
        return FLICKR_URL+FLICKR_API_METHOD+API_KEY+getGalleryId();
    }

    public String getGalleryId() {
        return String.format("&format=json&nojsoncallback=1&per_page=50&page=1&gallery_id=%s&get_gallery_info=1", galleryId);
    }

    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }
}
