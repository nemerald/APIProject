package com.nemerald.apiproject.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nemerald.apiproject.Objects.Picture;
import com.nemerald.apiproject.R;

import org.w3c.dom.Text;

import static android.R.color.darker_gray;

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

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams")
        View alertCustomLayout = inflater.inflate(R.layout.custom_show_picture_dialog, null);

        final TextView pictureTitle = alertCustomLayout.findViewById(R.id.photoTitle);

        pictureTitle.setText(String.format(getString(R.string.picture_title), picture.getPicTitle()));


        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
        builder.setView(alertCustomLayout);
        builder.setTitle(getString(R.string.picture_information));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (checkIfUserPickedAllInputInFreeKickDialog(rgGroupTeam.getCheckedRadioButtonId())) {
                    Team team = match.team1;
                    if (rbAwayTeam.isChecked()) {
                        team = match.team2;
                    }
                    Player player = new FutsalPlayer("", "", "", team, -1);
                    match.addEvent(new FreeKick(-1, player, HelpMethods.getPeriod(match)));
                    Toast.makeText(getActivity(), getString(R.string.freekick_event_added), Toast.LENGTH_LONG).show();

                    alertDialog.dismiss();
                } else {
                    vibrate(getActivity(), getString(R.string.error_vibrate));
                    Toast.makeText(getActivity(), getString(R.string.error_user_input_pick_team), Toast.LENGTH_LONG).show();
                }*/
            }
        });

        return alertDialog;
    }
    @SuppressLint("ResourceType")
    @Override
    public void onStart() {
        super.onStart();

        super.onStart();

        final Resources res = getResources();

        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        final View titleDivider = getDialog().findViewById(titleDividerId);
        if (titleDivider != null){
            titleDivider.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        }
    }
}