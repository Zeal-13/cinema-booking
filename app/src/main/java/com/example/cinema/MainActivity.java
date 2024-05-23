package com.example.cinema;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RRR";

    private static final String FIREBASE_REALTIME_DATABASE_URL =
            "https://cinema-booking-88f02-default-rtdb.firebaseio.com/";

    private BottomNavigationView bottomNavigationView;

    public static String getFirebaseRealtimeDatabaseUrl() {
        return FIREBASE_REALTIME_DATABASE_URL;
    }

    public static String getTag() {
        return TAG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                Navigation.findNavController(this, R.id.fragment_container)
                        .navigate(R.id.bookedFilmsFragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_monitor) {
                Navigation.findNavController(this, R.id.fragment_container)
                        .navigate(R.id.allFilmsFragment);
                return true;
            } else if (item.getItemId() == R.id.navigation_profile) {
                Navigation.findNavController(this, R.id.fragment_container)
                        .navigate(R.id.profileFragment);
                return true;
            } else {
                return false;
            }
        });
    }

    public void showBottomNavigationView(boolean show) {
        if (show) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }
}