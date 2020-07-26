package com.example.farid.prayertime.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.farid.prayertime.R;
import com.example.farid.prayertime.receiver.AlarmBroadcastReceiver;
import com.example.farid.prayertime.ui.MyViewModelFactory;
import com.example.farid.prayertime.ui.PrayerViewModel;
import com.example.farid.prayertime.ui.fragment.AlarmDialogFragment;
import com.example.farid.prayertime.ui.fragment.AlarmFragment;

import static com.example.farid.prayertime.Constants.ALARM_STATUS;
import static com.example.farid.prayertime.Constants.SHARED_PREF_FRAGMENT;

public class MainActivity extends AppCompatActivity implements AlarmDialogFragment.OnDataPassMillisecondsListsener, AlarmFragment.OnStopAlarmListener {
    private static final String TAG = "MainActivity";
    private PrayerViewModel mPrayerViewModel;
    private NavController navController;
    private SharedPreferences mAlarmPrefs;

    private AlarmManager mAlarmManager;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        mAlarmPrefs = getSharedPreferences(SHARED_PREF_FRAGMENT, Context.MODE_PRIVATE);
        mPrayerViewModel = new ViewModelProvider(this, new MyViewModelFactory(this.getApplication())).get(PrayerViewModel.class);
        navController = Navigation.findNavController(MainActivity.this, R.id.my_nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.prayerTimeFragment:

                        break;
                    case R.id.alarmFragment:
                        break;

                }
            }
        });
    }

    private void startAlarm(long alarmMilliseconds) {
        mIntent = new Intent(this, AlarmBroadcastReceiver.class);
        mIntent.putExtra(ALARM_STATUS, "on");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, mIntent, 0);
        mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmMilliseconds, pendingIntent);
    }

    private void cancelAlarm() {
        mIntent = new Intent(this, AlarmBroadcastReceiver.class);
        mIntent.putExtra(ALARM_STATUS, "off");
        sendBroadcast(mIntent);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, mIntent, 0);
        mAlarmManager.cancel(pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isAlarming = mAlarmPrefs.getBoolean(ALARM_STATUS, false);
            if (isAlarming) {
                Navigation.findNavController(this, R.id.my_nav_host_fragment).navigate(R.id.alarmFragment);
            }
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alarm_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alarm:
                Navigation.findNavController(this, R.id.my_nav_host_fragment).navigate(R.id.setMinutesFragment);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataPass(long alarmMilliseconds) {
        startAlarm(alarmMilliseconds);
    }

    @Override
    public void onStopAlarm() {
        cancelAlarm();
    }
}
