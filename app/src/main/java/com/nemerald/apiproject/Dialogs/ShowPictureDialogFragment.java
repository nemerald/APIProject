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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nemerald.apiproject.Objects.Picture;
import com.nemerald.apiproject.R;

import static com.nemerald.apiproject.Helpers.BitmapHelper.getBitmapFromURL;
import static com.nemerald.apiproject.MainActivity.getCache;

public class ShowPictureDialogFragment extends DialogFragment {

    public static ShowPictureDialogFragment newInstance(Picture picture){
        ShowPictureDialogFragment showPictureDialogFragment = new ShowPictureDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable("picture", picture);
        showPictureDialogFragment.setArguments(args);

        return showPictureDialogFragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Picture picture = (Picture) getArguments().getSerializable("picture");

        final LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertCustomLayout = inflater.inflate(R.layout.custom_show_picture_dialog, null);

        final TextView pictureTitle = alertCustomLayout.findViewById(R.id.photoTitle);
        final ImageView pickedImage = alertCustomLayout.findViewById(R.id.pickedImage);

        Bitmap bitmap = getCache().getBitmapFromMemCache(picture.getPicId());
        if(bitmap != null){
            pickedImage.setImageBitmap(bitmap);
        }
        else{
            bitmap = getBitmapFromURL(picture);
            pickedImage.setImageBitmap(bitmap);
        }

        pictureTitle.setText(String.format(getString(R.string.picture_title), picture.getPicTitle()));

        alertCustomLayout.requestFocus();

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.setContentView(alertCustomLayout);
        dialog.show();

        return dialog;
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