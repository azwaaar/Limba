package id.co.bitdata.b3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.co.bitdata.b3.R;
import id.co.bitdata.b3.model.Deposit;

public class DepositAdapter extends RecyclerView.Adapter<DepositAdapter.myDeposit> {

    private static ArrayList<Deposit> deposits;
    private static Context context;

    public DepositAdapter(Context context) {
        DepositAdapter.context = context;
    }

    public void setCategory(ArrayList<Deposit> deposit) {
        DepositAdapter.deposits = deposit;
    }

    @NonNull
    @Override
    public DepositAdapter.myDeposit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deposit, parent, false);
        return new myDeposit(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepositAdapter.myDeposit holder, int position) {
        final Deposit deposit = deposits.get(position);

        holder.imageDeposit.setImageResource(deposit.getImg());
        holder.timeDeposit.setText(deposit.getTime());
        holder.descriptionDeposit.setText(deposit.getDescription());
        holder.priceDeposit.setText(deposit.getPrice());

        switch (deposit.getStatus()) {
            case "Pending":
                holder.statusDeposit.setText(deposit.getStatus());
                holder.statusDeposit.setTextColor(ContextCompat.getColor(context, R.color.yellowDark));
                break;
            case "Dibatalkan":
                holder.statusDeposit.setText(deposit.getStatus());
                holder.statusDeposit.setTextColor(ContextCompat.getColor(context, R.color.redDark));
                break;
            case "Driver Pickup":
                holder.statusDeposit.setText(deposit.getStatus());
                holder.statusDeposit.setTextColor(ContextCompat.getColor(context, R.color.orangeDark));
                break;
            default:
                holder.statusDeposit.setText(deposit.getStatus());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return deposits.size();
    }

    public static class myDeposit extends RecyclerView.ViewHolder {
        private final ImageView imageDeposit;
        private final TextView timeDeposit, descriptionDeposit, statusDeposit, priceDeposit;
        public myDeposit(@NonNull View itemView) {
            super(itemView);
            imageDeposit = itemView.findViewById(R.id.imageDeposit);
            timeDeposit = itemView.findViewById(R.id.timeDeposit);
            descriptionDeposit = itemView.findViewById(R.id.descriptionDeposit);
            statusDeposit = itemView.findViewById(R.id.statusDeposit);
            priceDeposit = itemView.findViewById(R.id.priceDeposit);
        }
    }
}
