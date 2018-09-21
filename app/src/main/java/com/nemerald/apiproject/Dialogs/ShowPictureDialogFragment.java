package com.nemerald.apiproject.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nemerald.apiproject.Helpers.BitmapHelper;
import com.nemerald.apiproject.Helpers.SaveFileToStorage;
import com.nemerald.apiproject.Objects.FavoriteGallery;
import com.nemerald.apiproject.Objects.FileSaveAndGet;
import com.nemerald.apiproject.Objects.Picture;
import com.nemerald.apiproject.R;

import static com.nemerald.apiproject.MainActivity.getCache;

public class ShowPictureDialogFragment extends DialogFragment {

    SaveFileToStorage saveFileToStorage;

    public static ShowPictureDialogFragment newInstance(Picture picture, FavoriteGallery favoriteGallery){
        ShowPictureDialogFragment showPictureDialogFragment = new ShowPictureDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable("picture", picture);
        args.putSerializable("favoriteGallery", favoriteGallery);
        showPictureDialogFragment.setArguments(args);

        return showPictureDialogFragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Picture picture = (Picture) getArguments().getSerializable("picture");
        final FavoriteGallery favoriteGallery = (FavoriteGallery) getArguments().getSerializable("favoriteGallery");

        final LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertCustomLayout = inflater.inflate(R.layout.custom_show_picture_dialog, null);

        final TextView pictureTitle = alertCustomLayout.findViewById(R.id.photoTitle);
        final ImageView pickedImage = alertCustomLayout.findViewById(R.id.pickedImage);
        final Button makeFavorite = alertCustomLayout.findViewById(R.id.makeFavorite);

        if(noNulls(picture, favoriteGallery) && checkIfPictureIsFavorite(favoriteGallery, picture)){
            makeFavorite.setText(R.string.remove_favorite);
        }

        alertCustomLayout.requestFocus();

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.setContentView(alertCustomLayout);
        dialog.show();

        Bitmap bitmap = getCache().getBitmapFromMemCache(picture.getPicId());
        if(bitmap != null){
            pickedImage.setImageBitmap(bitmap);
        }
        else{
            bitmap = new BitmapHelper().getBitmapFromURL(picture, getContext());
            pickedImage.setImageBitmap(bitmap);
        }

        pictureTitle.setText(String.format(getString(R.string.picture_title), picture.getPicTitle()));
        pickedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        makeFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(makeFavorite.getText().toString().matches(getString(R.string.remove_favorite))){
                    Toast.makeText(getContext(), getString(R.string.removed_favorite), Toast.LENGTH_SHORT).show();
                    favoriteGallery.removeFavoritePictureInGallery(picture);
                }
                else{
                    Toast.makeText(getContext(), getString(R.string.new_favorite), Toast.LENGTH_SHORT).show();
                    favoriteGallery.addPictureToGalleryList(picture);
                    saveFileToStorage = new SaveFileToStorage(new FileSaveAndGet(getContext(), picture.getPicId()), new BitmapHelper().getBitmapFromURL(picture, getContext()), getContext());
                }
                dialog.dismiss();
            }
        });

        return dialog;
    }

    private boolean noNulls(Picture picture, FavoriteGallery favoriteGallery) {

    return picture!=null && favoriteGallery!=null;
    }

    private boolean checkIfPictureIsFavorite(FavoriteGallery favoriteGallery, Picture picture) {
        for (Picture controlPicture: favoriteGallery.getPictureArrayList()) {
            if(controlPicture.getPicId().matches(picture.getPicId())){
                return true;
            }
        }
        return false;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onStart() {
        super.onStart();

        Dialog d = getDialog();
        if(d!=null){
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;

            d.getWindow().setLayout(width, height);
        }

        final Resources res = getResources();

        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        final View titleDivider = getDialog().findViewById(titleDividerId);
        if (titleDivider != null){
            titleDivider.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        }
    }
}