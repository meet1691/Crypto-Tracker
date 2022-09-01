package com.meet.cryptotracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
     ArrayList<CurrencyModel> currencyModelArrayList;
    Context context;
    // for formatting the price
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public CurrencyAdapter(ArrayList<CurrencyModel> currencyModelArrayList, Context context) {
        this.currencyModelArrayList = currencyModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.currencyitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.ViewHolder holder, int position) {
        CurrencyModel currencyModel = currencyModelArrayList.get(position);
        holder.rate.setText("$ "+df2.format(currencyModel.getPrice()));
        holder.name.setText(currencyModel.getCurrencyName());
        holder.symbol.setText(currencyModel.getSymbol());

    }

    @Override
    public int getItemCount() {
        return currencyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,symbol,rate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvCurrencyName);
            symbol = itemView.findViewById(R.id.tvCurrencySymbol);
            rate = itemView.findViewById(R.id.tvCurrencyRate);
        }
    }

    public void   filterList(ArrayList<CurrencyModel> filterList){
        currencyModelArrayList = filterList;
        notifyDataSetChanged();
    }
}
