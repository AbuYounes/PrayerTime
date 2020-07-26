package com.example.farid.prayertime.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.farid.prayertime.R;

public class AlarmDialog extends DialogFragment {
    private String[] minuteValues = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        final NumberPicker aNumberPicker = new NumberPicker(getActivity());
        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        aNumberPicker.setMinValue(0);

        //Specify the maximum value/number of NumberPicker
        aNumberPicker.setMaxValue(minuteValues.length-1);
        aNumberPicker.setWrapSelectorWheel(true);
        aNumberPicker.setDisplayedValues(minuteValues);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        relativeLayout.setLayoutParams(params);
        relativeLayout.addView(aNumberPicker,numPicerParams);

        // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.set_alarm_time)
                    .setView(relativeLayout)
                    .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
}
