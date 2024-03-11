package com.truong.foodapplication.ui.home;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.data.repository.FoodsRepository;
import com.truong.foodapplication.databinding.FragmentHomeBinding;
import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.fooddetail.FoodDetailFragment;
import com.truong.foodapplication.ui.purchase.PurchaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.ItemClickListener {

    private HomeViewModel mViewModel;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private FragmentHomeBinding binding;
    private FoodDetailFragment foodDetailFragment;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private FoodsRepository foodsRepository = new FoodsRepository();
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        mViewModel = new HomeViewModel(baseMainActivityViewModel);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.VISIBLE);

        recyclerView = binding.homeRecycleView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Khởi tạo adapter một lần duy nhất
        homeAdapter = new HomeAdapter(getActivity(), new ArrayList<>());
        homeAdapter.setClickListener(HomeFragment.this::onItemClick);
        recyclerView.setAdapter(homeAdapter);
        mViewModel.getSharedUserData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.homeProfileName.setText(user.getDisplayName());
            }
        });

        mViewModel.getSharedFoodData().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (foods != null){
                    homeAdapter.updateData(foods);
                } else {
                    Log.e(TAG, "Chưa nhận được dữ liệu!");
                }
            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(View view, int position) {
        foodDetailFragment = new FoodDetailFragment();
        mViewModel.setPosition(position);
        changeFragment(foodDetailFragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.inbagBtn.setOnClickListener(v -> {
            PurchaseFragment purchaseFragment = PurchaseFragment.newInstance();
            changeFragment(purchaseFragment);
        });
    }
    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}