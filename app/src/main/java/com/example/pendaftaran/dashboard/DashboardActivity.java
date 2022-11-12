package com.example.pendaftaran.dashboard;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.pendaftaran.Anggota.AnggotaFragment;
import com.example.pendaftaran.LaporanKegiatan.LaporanFragment;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Preferences;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class DashboardActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        setStatusBarGradiant(DashboardActivity.this);
        BubbleNavigationLinearView bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
        bubbleNavigationLinearView.setBadgeValue(0, "30");
        bubbleNavigationLinearView.setBadgeValue(1, null); //invisible badge

        preferences = new Preferences(this);


        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new DashboardFragment());
        fragmentTransaction.commit();
        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position) {
                    case 0:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new DashboardFragment());
                        fragmentTransaction.commit();
                        break;

                    case 1:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        sharedPreferences = getSharedPreferences("akun", MODE_PRIVATE);
//                        String id = sharedPreferences.getString("id", "");
//                        Toast.makeText(DashboardActivity.this, id, Toast.LENGTH_SHORT).show();
//                        if (id.equals("")) {
                        fragmentTransaction.replace(R.id.fragment_container, new LaporanFragment());
//                        } else {
//                            Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
//                            startActivity(intent);
//                            finish();
////
//                        }

                        fragmentTransaction.commit();
                        break;

                    case 2:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, new AnggotaFragment());
                        fragmentTransaction.commit();
                        break;
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.color.bottomNavigationBackground);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.black));
            window.setBackgroundDrawable(background);

        }
    }


}