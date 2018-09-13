package com.nemerald.apiproject.Objects;

import java.io.Serializable;

public class Picture implements Serializable{

    private String picId;
    private String picTitle;
    private String farm;
    private String server;
    private String pictureOwner;
    private String secret;


    public Picture(String picId, String picTitle, String farm, String server, String pictureOwner, String secret){
        this.picId = picId;
        this.picTitle = picTitle;
        this.farm = farm;
        this.server = server;
        this.pictureOwner = pictureOwner;
        this.secret = secret;
    }

    public String getPicId() {
        return picId;
    }

    public String getPicTitle() {
        return picTitle;
    }

    private String getFarm() {
        return farm;
    }

    private String getServer() {
        return server;
    }

    public String getPictureOwner() {
        return pictureOwner;
    }

    private String getSecret() {
        return secret;
    }

    public String getPictureUrl(Picture picture) {
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", picture.getFarm(), picture.getServer(), picture.getPicId(), picture.getSecret());
    }
}
