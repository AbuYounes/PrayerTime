package com.example.farid.prayertime.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.farid.prayertime.R;
import com.example.farid.prayertime.ui.MyViewModelFactory;
import com.example.farid.prayertime.ui.PrayerViewModel;

import static com.example.farid.prayertime.Constants.ALARM_STATUS;
import static com.example.farid.prayertime.Constants.SHARED_PREF_FRAGMENT;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private PrayerViewModel mPrayerViewModel;
    private NavController navController;
    private SharedPreferences mAlarmPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
