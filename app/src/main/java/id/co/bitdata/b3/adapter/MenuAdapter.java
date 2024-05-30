package id.co.bitdata.b3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.model.Menu;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.myMenu> {
    private static ArrayList<Menu> categories;
    private static Context context;

    public MenuAdapter(Context context) {
        MenuAdapter.context = context;
    }

    public void setCategory(ArrayList<Menu> categories) {
        MenuAdapter.categories = categories;
    }

    @NonNull
    @Override
    public MenuAdapter.myMenu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new myMenu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.myMenu holder, int position) {
        final Menu category = categories.get(position);

        holder.imgCategory.setImageResource(category.getImgMenu());
        holder.titleCategory.setText(category.getTitleMenu());

        holder.imgCategory.setOnClickListener(v -> {
//            switch (holder.getAdapterPosition()) {
//                case 0:
//                    Intent absent = new Intent(context, AbsenActivity.class);
//                    context.startActivity(absent);
//                    break;
//                case 1:
//                    Intent notification = new Intent(context, NotificationActivity.class);
//                    context.startActivity(notification);
//                    break;
//                case 2:
//                    Intent attendanceHistory = new Intent(context, AttendanceHistoryActivity.class);
//                    context.startActivity(attendanceHistory);
//                    break;
//                case 3:
//                    Intent report = new Intent(context, ReportHistoryActivity.class);
//                    context.startActivity(report);
//                    break;
//            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class myMenu extends RecyclerView.ViewHolder {
        private final ImageView imgCategory;
        private final TextView titleCategory;
        public myMenu(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.iconCategory);
            titleCategory = itemView.findViewById(R.id.titleCategory);
        }
    }
}
