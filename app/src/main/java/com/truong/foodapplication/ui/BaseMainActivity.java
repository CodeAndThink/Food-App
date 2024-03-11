package com.truong.foodapplication.ui;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.messaging.FirebaseMessaging;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.MyFirebaseMessagingService;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.databinding.ActivityBaseMainBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.mainviewmodel.SharedViewModel;
import com.truong.foodapplication.ui.home.HomeFragment;
import com.truong.foodapplication.ui.notification.NotificationFragment;
import com.truong.foodapplication.ui.profile.ProfileFragment;

public class BaseMainActivity extends AppCompatActivity {
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private MyFirebaseMessagingService myFirebaseMessagingService;
    private SharedViewModel  sharedViewModel;
    private ActivityBaseMainBinding binding;
    private User user;
    private String username;
    private String displayName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_main);
        binding = ActivityBaseMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent getdata = getIntent();

        username = getdata.getStringExtra("username");
        displayName = getdata.getStringExtra("displayName");

        FirebaseApp.initializeApp(this);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        baseMainActivityViewModel = new ViewModelProvider(this).get(BaseMainActivityViewModel.class);
        user = new User(username, displayName);
        baseMainActivityViewModel.setSharedUserData(user);
        myFirebaseMessagingService = new MyFirebaseMessagingService();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navigation_home)
            {
                loadFragment(new HomeFragment());
                return true;
            }else if (item.getItemId() == R.id.navigation_notifications) {
                loadFragment(new NotificationFragment());
                return true;
            }else if (item.getItemId() == R.id.navigation_profile) {
                loadFragment(new ProfileFragment());
                return true;
            }
            return false;
        });
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}