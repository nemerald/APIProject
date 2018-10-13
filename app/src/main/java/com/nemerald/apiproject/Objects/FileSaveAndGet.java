package com.nemerald.apiproject.Objects;

import android.content.Context;

public class FileSaveAndGet implements IFileSaveAndGet {

    private String fileName;
    private String fileFullPath;
    private String filePath;
    private String fileId;
    Context context;
    Picture picture;

    public FileSaveAndGet(Context context, Picture picture){
        this.context = context;
        setFileName(picture.getPicId());
        setFileFullPath();
        setFileId(picture.getPicId());
        this.picture = picture;
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
    public void setFileFullPath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getFilesDir());
        stringBuilder.append("/");
        stringBuilder.append(getAlbumName());
        filePath = stringBuilder.toString();
        stringBuilder.append("/");
        stringBuilder.append(getFileName());
        fileFullPath = stringBuilder.toString();
    }
    @Override
    public String getFileFullPath() {
        return fileFullPath;
    }
    @Override
    public void setFileId(String fileName) {
        fileId = fileName;
    }

    @Override
    public String getFileId() {
        return fileId;
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
