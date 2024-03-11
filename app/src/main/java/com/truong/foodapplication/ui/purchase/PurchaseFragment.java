package com.truong.foodapplication.ui.purchase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.data.model.User;
import com.truong.foodapplication.data.repository.FoodsRepository;
import com.truong.foodapplication.databinding.FragmentHomeBinding;
import com.truong.foodapplication.databinding.FragmentPurchaseBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.fooddetail.FoodDetailFragment;
import com.truong.foodapplication.ui.home.HomeAdapter;
import com.truong.foodapplication.ui.home.HomeFragment;
import com.truong.foodapplication.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class PurchaseFragment extends Fragment {

    private PurchaseViewModel mViewModel;
    private RecyclerView recyclerView;
    private PurchaseAdapter purchaseAdapter;
    private FragmentPurchaseBinding binding;
    private FoodDetailFragment foodDetailFragment;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private FoodsRepository foodsRepository = new FoodsRepository();
    public static PurchaseFragment newInstance() {
        return new PurchaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        mViewModel = new PurchaseViewModel(baseMainActivityViewModel);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.VISIBLE);

        recyclerView = binding.purchaseRecycleview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Khởi tạo adapter một lần duy nhất
        purchaseAdapter = new PurchaseAdapter(getActivity(), new ArrayList<>());
        purchaseAdapter.setClickListener(PurchaseFragment.this::onItemClick);
        recyclerView.setAdapter(purchaseAdapter);
        mViewModel.getSharedUserData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
            }
        });

        mViewModel.getSharedFoodData().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (foods != null){
                    
                } else {
                    Log.e(TAG, "Chưa nhận được dữ liệu!");
                }
            }
        });
        return view;
    }

    private void onItemClick(View view, int i) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}