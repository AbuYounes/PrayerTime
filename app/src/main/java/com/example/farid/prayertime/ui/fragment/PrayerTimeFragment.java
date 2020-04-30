package com.example.farid.prayertime.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.farid.prayertime.R;
import com.example.farid.prayertime.model.TimePrayer;
import com.example.farid.prayertime.ui.MyViewModelFactory;
import com.example.farid.prayertime.ui.PrayerViewModel;
import com.example.farid.prayertime.util.PrayerTimeUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrayerTimeFragment extends Fragment {
    private static final String TAG = "PrayerTimeFragment";


    @BindView(R.id.date_textView_latin)
    TextView dateLatinTextView;
    @BindView(R.id.date_textView_arabic)
    TextView dateArabicTextView;
    @BindView(R.id.fajrTime)
    TextView mFajrTime;
    @BindView(R.id.shoerouqTime)
    TextView mShoerouqTime;
    @BindView(R.id.dohrTime)
    TextView mDohrTime;
    @BindView(R.id.asrTime)
    TextView mAsrTime;
    @BindView(R.id.maghribTime)
    TextView mMaghribTime;
    @BindView(R.id.ishaTime)
    TextView mIshaTime;

    private PrayerViewModel prayerViewModel;
    private TimePrayer mTimePrayer;

    public PrayerTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() !=null) {
            prayerViewModel = new ViewModelProvider(getActivity(), new MyViewModelFactory(getActivity().getApplication())).get(PrayerViewModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_prayer_time, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() != null){
            prayerViewModel.getPrayerTime().observe(getActivity(), timePrayer -> {
                mTimePrayer = timePrayer;
                initViews();
            });
        }

        PrayerTimeUtils.toggleNotification(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        PrayerTimeUtils.toggleNotification(true);
    }


    private void initViews(){
        dateLatinTextView.setText(mTimePrayer.getNlDate());
        dateArabicTextView.setText(mTimePrayer.getArDate());
        mFajrTime.setText(mTimePrayer.getFajr());
        mShoerouqTime.setText(mTimePrayer.getShoeroeq());
        mDohrTime.setText(mTimePrayer.getDohr());
        mAsrTime.setText(mTimePrayer.getAsr());
        mMaghribTime.setText(mTimePrayer.getMaghrib());
        mIshaTime.setText(mTimePrayer.getIsha());
    }


}
