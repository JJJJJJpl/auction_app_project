package com.example.uaim_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaim_project.R;
import com.example.uaim_project.model.Bid;

import java.util.List;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.BidViewHolder> {
    private List<Bid> bidList;

    public BidAdapter(List<Bid> bidList) {
        this.bidList = bidList;
    }

    @NonNull
    @Override
    public BidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid, parent, false);
        return new BidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BidViewHolder holder, int position) {
        Bid bid = bidList.get(position);
        holder.bidPriceTextView.setText(String.format("%.2f PLN", bid.getBidPrice()));
        holder.auctionTitleTextView.setText(bid.getAuctionTitle());
        holder.bidTimeTextView.setText(bid.getBidTime());
    }

    @Override
    public int getItemCount() {
        return bidList.size();
    }

    static class BidViewHolder extends RecyclerView.ViewHolder {
        TextView bidPriceTextView, auctionTitleTextView, bidTimeTextView;

        public BidViewHolder(@NonNull View itemView) {
            super(itemView);
            bidPriceTextView = itemView.findViewById(R.id.bidPriceTextView);
            auctionTitleTextView = itemView.findViewById(R.id.auctionTitleTextView);
            bidTimeTextView = itemView.findViewById(R.id.bidTimeTextView);
        }
    }
}

