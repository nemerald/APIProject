package com.nemerald.apiproject.Objects;

public class Flickr implements IFlickr {

    private String galleryId;

    public String getFullFlickrURL(){
        return FLICKR_URL+FLICKR_API_METHOD+API_KEY+getGalleryId();
    }

    @Override
    public void setGalleryId(String galleryId) {
        this.galleryId = galleryId;
    }

    @Override
    public String getGalleryId() {
        return String.format("&format=json&nojsoncallback=1&per_page=50&page=1&gallery_id=%s&get_gallery_info=1", galleryId);
    }
}
