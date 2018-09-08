package com.nemerald.apiproject.Objects;

public class Flickr implements IFlickr {


    String FLICKR_URL = "https://api.flickr.com/services/rest/";
    String FLICKR_API_METHOD = "?method=flickr.galleries.getPhotos";
    String API_KEY = "&api_key=976f0cf74d83fe648af1f81be2cb8acb";
    String responseFormat ="&format=json&nojsoncallback=1&per_page=50&page=1&gallery_id=72157678340527534&get_gallery_info=1";

    public String getFullFlickrURL(){
        return FLICKR_URL+FLICKR_API_METHOD+API_KEY+responseFormat;
    }

    @Override
    public void changeMethod(String newMethod) {
        FLICKR_API_METHOD = newMethod;
    }
}
