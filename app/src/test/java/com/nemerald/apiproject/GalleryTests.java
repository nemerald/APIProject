package com.nemerald.apiproject;

import com.nemerald.apiproject.Objects.FavoriteGallery;
import com.nemerald.apiproject.Objects.Gallery;
import com.nemerald.apiproject.Objects.Picture;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GalleryTests {
    @Test

    public void setting_and_getting_name(){
        Gallery gallery = new Gallery();
        gallery.setGalleryTitle("Test galleri");
        Assert.assertEquals("Test galleri", gallery.getGalleryTitle());
    }

    public void test_adding_pictures_to_gallery(){
        Gallery gallery = new Gallery();
        gallery.setGalleryTitle("Test galleri");
        gallery.addPictureToGalleryList(new Picture("testId", "testTitle", "testFarm", "testServer",
                                                    "testPictureOwner","testSecret"));
        Assert.assertEquals(1, gallery.getPictureArrayList().size());
    }
    public void test_adding_and_removing_object_in_favorite_gallery(){
        FavoriteGallery favoriteGallery = new FavoriteGallery();
        Picture favoritePicture = new Picture("testId", "testTitle", "testFarm", "testServer",
                "testPictureOwner","testSecret");
        favoriteGallery.addPictureToGalleryList(favoritePicture);
        favoriteGallery.removeFavoritePictureInGallery(favoritePicture);
        Assert.assertEquals(0, favoriteGallery.getPictureArrayList().size());
    }
}