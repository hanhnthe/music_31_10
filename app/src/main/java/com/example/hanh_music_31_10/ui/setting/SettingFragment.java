package com.example.hanh_music_31_10.ui.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.hanh_music_31_10.R;

public class SettingFragment extends Fragment {

    public static final String THEME_NIGHT = "theme_night";

    public SettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, container, false);

        LinearLayout managerAcc = view.findViewById(R.id.line4);
        managerAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.settings,
                        new ManagerAccFragment()).addToBackStack(null).commit();
            }
        });

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchNight = view.findViewById(R.id.switch_night);
        switchNight.setChecked(getThemeNightMode());
        switchNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                }
                saveThemeNightMode(!isChecked);
            }
        });

        LinearLayout nightMode = view.findViewById(R.id.line);
        nightMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchNight.performClick();
            }
        });
        return view;
    }

    private void saveThemeNightMode(boolean isnight){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(THEME_NIGHT, isnight);
        editor.apply();
    }
    private boolean getThemeNightMode(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(THEME_NIGHT, false);
    }

}
