package com.truong.foodapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.databinding.ActivityLoginBinding;
import com.truong.foodapplication.databinding.ActivityRegisterBinding;
import com.truong.foodapplication.mainviewmodel.LoginActivityViewModel;
import com.truong.foodapplication.mainviewmodel.SharedViewModel;

public class LoginActivity extends AppCompatActivity {
    private LoginActivityViewModel loginActivityViewModel;
    private SharedViewModel sharedViewModel;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        loginActivityViewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        loginActivityViewModel = new LoginActivityViewModel(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        binding.linkTextView.setOnClickListener(v -> {
            Intent register = new Intent(this, RegisterActivity.class);
            startActivity(register);
            finish();
        });

        binding.LoginBtn.setOnClickListener(v -> {
            String username = binding.inEmailEdittext.getText().toString();
            String password = binding.inPasswordEdittext.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()){
                loginActivityViewModel.Login(username, password).observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user!=null){
                            String username = user.getUserName();
                            String displayName = user.getDisplayName();
                            Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, BaseMainActivity.class);
                            intent.putExtra("username", username);
                            intent.putExtra("displayName", displayName);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_false), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}