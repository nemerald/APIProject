package com.nemerald.apiproject.Objects;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class FileSaveAndGet implements IFileSaveAndGet {

    private String fileName;
    private String filePath;
    private String fileId;
    Context context;
    Picture picture;

    public FileSaveAndGet(Context context, Picture picture){
        this.context = context;
        this.picture = picture;
        setFileName(picture.getPicId());
        setFilePath();
        setFileId(picture.getPicId());
    }
    public FileSaveAndGet(Context context){
        this.context = context;
    }

    public String getAlbumName(){
        return PICTURE_ALBUM_NAME;
    }
    private String getPictureSuffix(){
        return PICTURE_SUFFIX;
    }

    @Override
    public void setFileName(String fileName) {
        StringBuilder strB = new StringBuilder();
        strB.append(fileName);
        strB.append(getPictureSuffix());
        this.fileName = strB.toString();
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFilePath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getFilesDir());
        stringBuilder.append("/");
        stringBuilder.append(getAlbumName());
        filePath = stringBuilder.toString();
    }
    @Override
    public void setFileId(String fileName) {
        fileId = fileName;
    }

    @Override
    public String getFileId() {
        return fileId;
    }

    @Override
    public String getFileJsonRef() {
        JSONObject picJsonRef = new JSONObject();
        try {
            picJsonRef.put("id", picture.getPicId());
            picJsonRef.put("title", picture.getPicTitle());
            picJsonRef.put("farm", picture.getFarm());
            picJsonRef.put("server", picture.getServer());
            picJsonRef.put("secret", picture.getSecret());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return picJsonRef.toString();
    }

    public String getFileFullPathById(String pictureId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getFilesDir());
        stringBuilder.append("/");
        stringBuilder.append(getAlbumName());
        stringBuilder.append("/");
        stringBuilder.append(pictureId);
        stringBuilder.append(getPictureSuffix());
        return stringBuilder.toString();
    }

    public String getFilePath() {
        return filePath;
    }
}
