package com.truong.foodapplication.ui.fooddetail;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.databinding.FragmentFoodDetailBinding;
import com.truong.foodapplication.mainviewmodel.BaseMainActivityViewModel;
import com.truong.foodapplication.ui.home.HomeFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodDetailFragment extends Fragment {

    private FoodDetailViewModel mViewModel;
    private BaseMainActivityViewModel baseMainActivityViewModel;
    private List<String> mPrice;
    private RecyclerView recyclerView;
    private FragmentFoodDetailBinding binding;
    private FoodDetailAdapter foodDetailAdapter;
    private int quantity = 1;
    private int position;
    private String mSizePrice = "0.0";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFoodDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        baseMainActivityViewModel = new ViewModelProvider(requireActivity()).get(BaseMainActivityViewModel.class);
        mViewModel = new FoodDetailViewModel(baseMainActivityViewModel);

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.GONE);

        recyclerView = binding.foodDetailSize;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL); // Hoặc LinearLayoutManager.HORIZONTAL
        recyclerView.setLayoutManager(layoutManager);

        // Khởi tạo adapter một lần duy nhất
        foodDetailAdapter = new FoodDetailAdapter(getActivity(), new ArrayList<>());
        foodDetailAdapter.setClickListener(FoodDetailFragment.this::onItemClick);
        recyclerView.setAdapter(foodDetailAdapter);

        mViewModel.getPosition().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                position = integer;
            }
        });

        mViewModel.getSharedFoodData().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (foods != null){
                    // Cập nhật dữ liệu của adapter khi danh sách foods thay đổi
                    Context context = binding.getRoot().getContext();
                    Resources resources = context.getResources();
                    int resourceId = resources.getIdentifier(foods.get(position).getFoodSmallUrl(), "drawable", context.getPackageName());
                    binding.foodDetailImage.setImageResource(resourceId);
                    binding.foodDetailName.setText(foods.get(position).getFoodName());
                    foodDetailAdapter.updateData(foods.get(position).getFoodSize());
                    mPrice = new ArrayList<>(foods.get(position).getFoodPrice());
                    Log.d(TAG, "In fact, you chose " + position);
                } else {
                    Log.e(TAG, "Chưa nhận được dữ liệu!");
                }
            }
        });
        return view;
    }

    private void onItemClick(View view, int i) {
        mSizePrice = mPrice.get(i);
        quantity = 1;
        binding.foodDetailPrice.setText(mSizePrice);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.foodDetailPlusBtn.setOnClickListener(v -> {
            double price = Double.parseDouble(mSizePrice);
            if (price!=0.0){
                quantity+=1;
            }
            double sum = price * quantity;
            DecimalFormat df = new DecimalFormat("#.#"); // Định dạng để giữ lại hai chữ số sau dấu thập phân
            String formattedNumber = df.format(sum);
            binding.foodDetailPrice.setText(formattedNumber);
        });
        binding.foodDetailMinusBtn.setOnClickListener(v -> {
            double price = Double.parseDouble(mSizePrice);
            if (quantity > 1){
                quantity-=1;
            }
            double sum = price * quantity;
            DecimalFormat df = new DecimalFormat("#.#"); // Định dạng để giữ lại hai chữ số sau dấu thập phân
            String formattedNumber = df.format(sum);
            binding.foodDetailPrice.setText(formattedNumber);
        });
        binding.BackBtn.setOnClickListener(v -> {
            HomeFragment homeFragment = HomeFragment.newInstance();
            changeFragment(homeFragment);
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