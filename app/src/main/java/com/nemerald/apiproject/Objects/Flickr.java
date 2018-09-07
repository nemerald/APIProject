package com.nemerald.apiproject;

public class Flickr {


    private String flickrUrl = "https://api.flickr.com/services/rest/";
    private String flickrMethod = "?method=flickr.galleries.getPhotos";
    private String URL = "https://api.flickr.com/services/rest/?method=flickr.galleries.getPhotos&api_key=976f0cf74d83fe648af1f81be2cb8acb&format=json&nojsoncallback=1&per_page=50&page=1&gallery_id=72157678340527534&get_gallery_info=1";
    private String API_KEY = "976f0cf74d83fe648af1f81be2cb8acb";

    public String getUrl() {
        return URL;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }
}
