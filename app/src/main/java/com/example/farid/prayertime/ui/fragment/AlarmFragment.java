package com.example.farid.prayertime.ui.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.receiver.AlarmBroadcastReceiver;
import com.example.farid.prayertime.R;
import com.example.farid.prayertime.rxbus.RxAlarmTimeInMinutes;
import com.example.farid.prayertime.rxbus.RxBusPrayerTime;
import com.example.farid.prayertime.model.TimePrayer;
import com.example.farid.prayertime.ui.PrayerViewModel;
import com.example.farid.prayertime.util.PrayerTimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.farid.prayertime.Constants.ALARM_STATUS;

public class AlarmFragment extends Fragment {
    private static final String TAG = "AlarmFragment";

    @BindView(R.id.alarm_clock)
    ImageView mAlarmClock;

    private AlarmManager mAlarmManager;

    private Intent mIntent;

    private PrayerViewModel prayerViewModel;
    private TimePrayer mTimePrayer;
    private AlarmTime mAlarmTime;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() !=null) {
            prayerViewModel = new ViewModelProvider(getActivity()).get(PrayerViewModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_alarm, container, false);
        ButterKnife.bind(this, rootView);

        if(getActivity() != null){
            mAlarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prayerViewModel.getPrayerTime().observe(getActivity(), new Observer<TimePrayer>() {
            @Override
            public void onChanged(TimePrayer timePrayer) {
                mTimePrayer = timePrayer;

                clickAlarmClock();
            }
        });

        prayerViewModel.getAllPrayersAlarm().observe(getActivity(), new Observer<AlarmTime>() {
            @Override
            public void onChanged(AlarmTime alarmTime) {
                mAlarmTime = alarmTime;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        PrayerTimeUtils.toggleNotification(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        PrayerTimeUtils.toggleNotification(true);
    }

    private void clickAlarmClock(){
        mAlarmClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null) {
                    RxBusPrayerTime.publish(mTimePrayer);
                    RxAlarmTimeInMinutes.publish(mAlarmTime);
                    mIntent = new Intent(getActivity(), AlarmBroadcastReceiver.class);
                    mIntent.putExtra(ALARM_STATUS, "off");
                    getActivity().sendBroadcast(mIntent);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, mIntent, 0);
                    mAlarmManager.cancel(pendingIntent);
                }
                if(getActivity() != null){
                    getActivity().onBackPressed();
                }

            }
        });
    }

}
