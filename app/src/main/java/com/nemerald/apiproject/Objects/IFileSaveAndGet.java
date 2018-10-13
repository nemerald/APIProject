package com.nemerald.apiproject.Objects;

public interface IFileSaveAndGet {
    String PICTURE_ALBUM_NAME = "APIProject/Favorite Gallery";
    String PICTURE_SUFFIX = ".png";
    void setFileName(String fileName);
    String getFileName();
    void setFilePath();
    void setFileId(String fileName);
    String getFileId();
    String getFileJsonRef();
}
