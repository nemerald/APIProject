package com.nemerald.apiproject;

public class Picture {

    private String picId;
    private String picTitle;
    private String farm;
    private String server;
    private String uId;
    private String secret;


    public Picture(String picId, String picTitle, String farm, String server, String uID, String secret){
        this.picId = picId;
        this.picTitle = picTitle;
        this.farm = farm;
        this.server = server;
        this.uId = uID;
        this.secret = secret;
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

    public String getuId() {
        return uId;
    }

    public String getSecret() {
        return secret;
    }

    public String getPictureUrl(Picture picture) {
        return String.format("https://farm%s.staticflickr.com/%s/%s_%s.jpg", picture.getFarm(), picture.getServer(), picture.getuId(), picture.getSecret());
    }
}
