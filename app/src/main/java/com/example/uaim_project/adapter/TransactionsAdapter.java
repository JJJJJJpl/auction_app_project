package com.example.uaim_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaim_project.R;
import com.example.uaim_project.model.Transaction;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

    private List<Transaction> transactionsList;

    // Konstruktor adaptera
    public TransactionsAdapter(List<Transaction> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating layout for single transaction item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transactions, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        // Pobieranie danych dla danego elementu
        Transaction transaction = transactionsList.get(position);

        // Ustawianie danych w widokach
        holder.auctionTitleTextView.setText(transaction.getAuction_title());
        holder.paymentStatusTextView.setText("Payment Status: " + transaction.getPayment_status());
        holder.transactionTimeTextView.setText("Transaction Time: " + transaction.getTransaction_time());
    }

    @Override
    public int getItemCount() {
        return transactionsList.size(); // Liczba elementów w liście
    }

    // Klasa ViewHolder - przechowuje widoki dla jednego elementu
    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView auctionTitleTextView;
        TextView paymentStatusTextView;
        TextView transactionTimeTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicjalizacja widoków z layoutu
            auctionTitleTextView = itemView.findViewById(R.id.auctionTitleTextView);
            paymentStatusTextView = itemView.findViewById(R.id.paymentStatusTextView);
            transactionTimeTextView = itemView.findViewById(R.id.transactionTimeTextView);
        }
    }
}

