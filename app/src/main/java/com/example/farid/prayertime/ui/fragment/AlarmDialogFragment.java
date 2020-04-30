package com.example.farid.prayertime.ui.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.farid.prayertime.receiver.AlarmBroadcastReceiver;
import com.example.farid.prayertime.R;
import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.model.TimePrayer;
import com.example.farid.prayertime.ui.MyViewModelFactory;
import com.example.farid.prayertime.ui.PrayerViewModel;
import com.example.farid.prayertime.util.PrayerTimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmDialogFragment extends Fragment {
    private static final String TAG = "AlarmDialogFragment";

    @BindView(R.id.minutes_numberpicker)
    NumberPicker mAlarmNumberPicker;
    @BindView(R.id.save_alarm_time)
    Button saveMinutesBtn;

    private PrayerViewModel prayerViewModel;
    private TimePrayer mTimePrayer;
    private AlarmTime mAlarmTime;
    private long mMillis;

    private String[] minuteValues = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60"};
    private AlarmManager mAlarmManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() !=null) {
            prayerViewModel = new ViewModelProvider(getActivity(), new MyViewModelFactory(getActivity().getApplication())).get(PrayerViewModel.class);
        }
    }

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View rootView =  inflater.inflate(R.layout.dialog_set_alarm, container, false);
        ButterKnife.bind(this, rootView);

        if(getActivity() != null){
            mAlarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }


        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        mAlarmNumberPicker.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        mAlarmNumberPicker.setMaxValue(minuteValues.length-1);
        mAlarmNumberPicker.setWrapSelectorWheel(true);
        mAlarmNumberPicker.setDisplayedValues(minuteValues);

        saveMinutes();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prayerViewModel.getPrayerTime().observe(getActivity(), new Observer<TimePrayer>() {
            @Override
            public void onChanged(TimePrayer timePrayer) {
                mTimePrayer = timePrayer;
                mMillis = PrayerTimeUtils.convertTimeStringToMilliSeconds(mTimePrayer);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        PrayerTimeUtils.toggleNotification(false);
    }

    private void saveMinutes(){
        saveMinutesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrayerTimeUtils.startAlarm(getActivity(), mAlarmManager, mAlarmTime, mMillis);
                if(getActivity() != null){
                    getActivity().onBackPressed();
                }
            }
        });
    }
}