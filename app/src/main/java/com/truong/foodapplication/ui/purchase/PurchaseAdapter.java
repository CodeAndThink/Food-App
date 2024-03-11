package com.truong.foodapplication.ui.purchase;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.truong.foodapplication.R;
import com.truong.foodapplication.data.model.Food;
import com.truong.foodapplication.ui.home.HomeAdapter;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder>{
    private List<Food> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // Constructor
    PurchaseAdapter(Context context, List<Food> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // Inflate layout từ XML khi cần và trả về ViewHolder mới
    @NonNull
    @Override
    public PurchaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.food_card, parent, false);
        return new ViewHolder(view);
    }

    // Liên kết dữ liệu của mục với mục hiện tại trong RecyclerView
    @Override
    public void onBindViewHolder(@NonNull PurchaseAdapter.ViewHolder holder, int position) {
        Food item = mData.get(position);
        Context context = holder.imageView.getContext();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(item.getFoodUrl(), "drawable", context.getPackageName());
        if (resourceId != 0) {
            holder.imageView.setImageResource(resourceId);
        } else {
            holder.imageView.setImageResource(R.drawable.default_broken_image);
        }
    }

    // Trả về số lượng mục trong danh sách
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // Lưu và tái sử dụng các View trong RecyclerView để tiết kiệm bộ nhớ
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.food_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // Phương thức để cập nhật dữ liệu mới
    public void updateData(List<Food> newData) {
        mData.clear(); // Xóa dữ liệu cũ
        mData.addAll(newData); // Thêm dữ liệu mới
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật lại giao diện
    }
    // Phương thức cho phép click vào item
    void setClickListener(PurchaseAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // Giao diện để xử lý sự kiện click
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
