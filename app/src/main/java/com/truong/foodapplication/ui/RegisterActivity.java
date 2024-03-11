package com.truong.foodapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.truong.foodapplication.R;
import com.truong.foodapplication.databinding.ActivityRegisterBinding;
import com.truong.foodapplication.mainviewmodel.RegisterActivityViewModel;
import com.truong.foodapplication.mainviewmodel.SharedViewModel;

public class RegisterActivity extends AppCompatActivity {
    private RegisterActivityViewModel registerActivityViewModel;
    private SharedViewModel sharedViewModel;
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        registerActivityViewModel = new ViewModelProvider(this).get(RegisterActivityViewModel.class);
        registerActivityViewModel = new RegisterActivityViewModel(this);

        binding.inDisplayNameEdittext.setText(com.truong.foodapplication.optionfunctions.functions.generateDisplayName());

        binding.BackBtn.setOnClickListener(v -> {
            Intent back = new Intent(this, LoginActivity.class);
            startActivity(back);
            finish();
        });

        binding.RegisterBtn.setOnClickListener(v -> {
            String username = binding.inEmailEdittext.getText().toString();
            String password = binding.inPasswordEdittext.getText().toString();
            String repassword = binding.reInPasswordEdittext.getText().toString();
            String displayname = binding.inDisplayNameEdittext.getText().toString();

            switch (com.truong.foodapplication.optionfunctions.functions.checkValid(username, password, repassword, displayname)){
                case 0:
                    registerActivityViewModel.registerNewUser(username, repassword, displayname).observe(this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) {
                                Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(login);
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this, getString(R.string.register_false), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    break;
                case 1:
                    Toast.makeText(this, getString(R.string.invalid_username), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(this,getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
                case 3:
                    Toast.makeText(this,getString(R.string.invalid_displayname), Toast.LENGTH_SHORT).show();
            }
        });
    }
}