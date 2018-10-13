package com.nemerald.apiproject;

import com.nemerald.apiproject.Objects.FavoriteGallery;

public interface FragmentCommunicator {
    FavoriteGallery fetchFavoriteGallery();
    void saveFavoriteGallery(FavoriteGallery favoriteGallery);
    void updateFavoriteList();
}
