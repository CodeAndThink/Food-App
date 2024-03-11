package com.truong.foodapplication.mainviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.data.repository.UserRepository;
import com.truong.foodapplication.ui.LoginActivity;

public class LoginActivityViewModel extends ViewModel {
    private SharedViewModel sharedViewModel;
    private LoginActivity loginActivity;
    private FirebaseAuth firebaseAuth;
    private UserRepository userRepository;
    private MutableLiveData<User> userData;
    public LoginActivityViewModel(){
        userRepository = new UserRepository();
        userData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public LoginActivityViewModel(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
        sharedViewModel = new ViewModelProvider(loginActivity).get(SharedViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        userData = new MutableLiveData<>();
        userRepository = new UserRepository();
    }
    public LiveData<User> Login(String email, String password) {
        return userRepository.loginUser(email, password);
    }
}
