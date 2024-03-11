package com.truong.foodapplication.ui.fooddetail;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.ui.home.HomeAdapter;

import java.util.List;

public class FoodDetailAdapter extends RecyclerView.Adapter<FoodDetailAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private FoodDetailAdapter.ItemClickListener mClickListener;

    // Constructor
    FoodDetailAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Inflate layout từ XML khi cần và trả về ViewHolder mới
    @NonNull
    @Override
    public FoodDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.food_size, parent, false);
        return new FoodDetailAdapter.ViewHolder(view);
    }

    // Liên kết dữ liệu của mục với mục hiện tại trong RecyclerView
    @Override
    public void onBindViewHolder(@NonNull FoodDetailAdapter.ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.textView.setText(item);
    }

    // Trả về số lượng mục trong danh sách
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Lưu và tái sử dụng các View trong RecyclerView để tiết kiệm bộ nhớ
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.food_size_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // Phương thức để cập nhật dữ liệu mới
    public void updateData(List<String> newData) {
        mData.clear(); // Xóa dữ liệu cũ
        mData.addAll(newData); // Thêm dữ liệu mới
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật lại giao diện
    }
    // Phương thức cho phép click vào item
    void setClickListener(FoodDetailAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Giao diện để xử lý sự kiện click
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
