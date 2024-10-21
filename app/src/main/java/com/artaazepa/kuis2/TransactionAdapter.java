package com.artaazepa.kuis2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    public TransactionAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void setTransactions(List<Transaction> transactions) {
        clear();
        addAll(transactions);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_transaction_item, parent, false);
        }

        Transaction transaction = getItem(position);

        // Find the views from your layout

        TextView jumlahTextView = convertView.findViewById(R.id.tvJumlah);
        TextView keteranganTextView = convertView.findViewById(R.id.tvKeterangan);

        // Set the data from the transaction object
        if (transaction != null) {
            jumlahTextView.setText("Rp. " + transaction.getJumlah());
            keteranganTextView.setText(transaction.getKeterangan());
        }

        return convertView;
    }
}
