package com.nemerald.apiproject.Objects;

import android.graphics.ImageFormat;

import java.io.Serializable;

public class Picture implements Serializable{

    private String picId;
    private String picTitle;
    private String farm;
    private String server;
    private String secret;
    private boolean favorite;

    public Picture(String picId, String picTitle, String farm, String server, String secret, boolean favorite){
        this.picId = picId;
        this.picTitle = picTitle;
        this.farm = farm;
        this.server = server;
        this.secret = secret;
        this.favorite = favorite;
    }

    public String getPicId() {
        return picId;
    }

    public String getPicTitle() {
        return picTitle;
    }

    public String getFarm() {
        return farm;
    }

    public String getServer() {
        return server;
    }

    public String getSecret() {
        return secret;
    }

    public String getPictureUrl(Picture picture) {
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", picture.getFarm(), picture.getServer(), picture.getPicId(), picture.getSecret());
    }
    public boolean isFavorite() {
        return favorite;
    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
