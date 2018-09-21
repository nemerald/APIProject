package com.nemerald.apiproject.Objects;

import android.content.Context;

public class FileSaveAndGet implements IFileSaveAndGet {

    private String fileName;
    private String fileFullPath;
    private String fileId;
    Context context;

    public FileSaveAndGet(Context context, String fileName){
        this.context = context;
        setFileName(fileName);
        setFileFullPath();
        setFileId(fileName);
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
        StringBuilder strB = new StringBuilder();
        strB.append(context.getFilesDir());
        strB.append(getAlbumName());
        strB.append("/");
        strB.append(getFileName());
        fileFullPath = strB.toString();
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
}
