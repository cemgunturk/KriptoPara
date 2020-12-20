package com.example.kriptopara.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kriptopara.Model.CryptoModel;
import com.example.kriptopara.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<CryptoModel> cryptoList;
    private String[] colors = {"#ff6347","#ff6347","#ff6347","#ff6347","#ff6347"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(cryptoList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView nameText,fiyatText;

        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void bind(CryptoModel cryptoModel,String[] colors,Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 5]));
            nameText = itemView.findViewById(R.id.nameText);
            fiyatText = itemView.findViewById(R.id.fiyatText);
            nameText.setText(cryptoModel.currency);
            fiyatText.setText(cryptoModel.price);
        }
    }
}
