package com.truong.foodapplication.ui.profile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.databinding.FragmentProfileBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.LoginActivity;
import com.truong.foodapplication.ui.changepassword.ChangeInformationFragment;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private ProfileViewModel profileViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        baseMainActivityViewModel.getSharedUserData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    binding.textboxDisplayname.setText(user.getDisplayName());
                    binding.textboxUsername.setText(user.getUserName());
                }else {
                    Log.d(TAG, "loi roi ne");
                }
            }
        });
        binding.deleteUserBtn.setOnClickListener(v -> {
            profileViewModel.deleteUser();
        });
        binding.updateUserInformationBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new ChangeInformationFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        binding.logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}