package com.nemerald.apiproject.Objects;

public interface IFileSaver {
    String PICTURE_ALBUM_NAME = "APIProject/Favorite Gallery";
    String PICTURE_SUFFIX = ".png";
    void setFileName(String fileName);
    String getFileName();
    void setFileFullPath();
    String getFileFullPath();
}
