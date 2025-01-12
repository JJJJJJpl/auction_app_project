package com.example.uaim_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaim_project.R;
import com.example.uaim_project.activity.AuctionActivity;
import com.example.uaim_project.model.Auction;

import java.util.List;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> {

    private List<Auction> auctionList;
    private Context context;

    // Konstruktor adaptera
    public AuctionAdapter(Context context, List<Auction> auctionList) {
        this.context = context;
        this.auctionList = auctionList;
    }

    @NonNull
    @Override
    public AuctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tworzenie widoku na podstawie layoutu
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction, parent, false);
        return new AuctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuctionViewHolder holder, int position) {
        // Pobieranie danych o aukcji i przypisywanie do widoków
        Auction auction = auctionList.get(position);
        holder.titleTextView.setText(auction.getTitle());
        holder.descriptionTextView.setText(auction.getDescription());
        holder.priceTextView.setText(String.format("%.2f PLN", auction.getStartingPrice()));
        holder.endTimeTextView.setText(auction.getEndTime());

        // Obsługa kliknięcia elementu listy
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, AuctionActivity.class);
            intent.putExtra("auction_id", auction.getAuctionId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return auctionList.size(); // Liczba elementów w liście
    }

    // Klasa ViewHolder - przechowuje widoki dla jednego elementu listy
    static class AuctionViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        TextView endTimeTextView;

        public AuctionViewHolder(@NonNull View itemView) {
            super(itemView);
            // Przypisanie widoków z layoutu
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            endTimeTextView = itemView.findViewById(R.id.endTimeTextView);
        }
    }
}
