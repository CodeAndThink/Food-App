package com.truong.foodapplication.ui.changepassword;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.databinding.FragmentChangeInformationBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.profile.ProfileFragment;

public class ChangeInformationFragment extends Fragment {

    private ChangePasswordViewModel mViewModel;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private FragmentChangeInformationBinding binding;

    public static ChangeInformationFragment newInstance() {
        return new ChangeInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChangeInformationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        mViewModel = new ChangePasswordViewModel(baseMainActivityViewModel);

        baseMainActivityViewModel.getSharedUserData().observe(requireActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null){
                    binding.upDisplayNameEdittext.setText(user.getDisplayName());
                }else {
                    Log.e(TAG, "Loi roi");
                }
            }
        });

        binding.updateUserInformationBtn.setOnClickListener(v -> {
            String password = binding.upPasswordEdittext.getText().toString();
            String repassword = binding.upRePasswordEdittext.getText().toString();
            String displayName = binding.upDisplayNameEdittext.getText().toString();

            mViewModel.updateUser( this ,password, repassword, displayName).observe(requireActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean){
                        Toast.makeText(requireActivity(), getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        View view = requireActivity().getCurrentFocus();
                        if (view != null) {
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        binding.BackBtn.callOnClick();
                    }else {
                        Toast.makeText(requireActivity(), getString(R.string.update_false), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        binding.BackBtn.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
}