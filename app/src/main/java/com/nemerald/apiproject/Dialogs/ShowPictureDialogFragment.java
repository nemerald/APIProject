package com.nemerald.apiproject.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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

import com.nemerald.apiproject.FragmentCommunicator;
import com.nemerald.apiproject.Helpers.BitmapHelper;
import com.nemerald.apiproject.Helpers.FileStorageHandler;
import com.nemerald.apiproject.Objects.FavoriteGallery;
import com.nemerald.apiproject.Objects.FileSaveAndGet;
import com.nemerald.apiproject.Objects.Picture;
import com.nemerald.apiproject.R;

import static com.nemerald.apiproject.MainActivity.getCache;

public class ShowPictureDialogFragment extends DialogFragment {

    FileStorageHandler fileStorageHandler;
    FragmentCommunicator mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentCommunicator) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentCommunicator");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

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

        final FavoriteGallery favoriteGallery = (FavoriteGallery) getArguments().getSerializable("favoriteGallery");
        final Picture picture = (Picture) getArguments().getSerializable("picture");

        final LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertCustomLayout = inflater.inflate(R.layout.custom_show_picture_dialog, null);

        final TextView pictureTitle = alertCustomLayout.findViewById(R.id.photoTitle);
        final ImageView pickedImage = alertCustomLayout.findViewById(R.id.pickedImage);
        final Button makeFavorite = alertCustomLayout.findViewById(R.id.makeFavorite);

        if(noNulls(picture, favoriteGallery) && picture.isFavorite()){
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
                if(picture.isFavorite()){
                    Toast.makeText(getContext(), getString(R.string.removed_favorite), Toast.LENGTH_SHORT).show();
                    favoriteGallery.removeFavoritePictureInGallery(picture);
                    fileStorageHandler = new FileStorageHandler(new FileSaveAndGet(getContext(), picture), getContext());
                    fileStorageHandler.removePictureFromFileStorage(picture);
                    picture.setFavorite(false);
                    mCallback.updateFavoriteList();
                }
                else{
                    Toast.makeText(getContext(), getString(R.string.new_favorite), Toast.LENGTH_SHORT).show();
                    favoriteGallery.addFavoritePictureToGalleryList(picture);
                    fileStorageHandler = new FileStorageHandler(new FileSaveAndGet(getContext(), picture), getContext());
                    fileStorageHandler.savePictureToFileToStorage(new BitmapHelper().getBitmapFromURL(picture, getContext()));
                    picture.setFavorite(true);
                }
                dialog.dismiss();
            }
        });

        return dialog;
    }

    private boolean noNulls(Object picture, FavoriteGallery favoriteGallery) {

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