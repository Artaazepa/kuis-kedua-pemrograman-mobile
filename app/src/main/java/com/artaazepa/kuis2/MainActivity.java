package com.artaazepa.kuis2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private TransactionViewModel transactionViewModel;
    private TextView tvSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi TextView untuk Saldo
        tvSaldo = findViewById(R.id.tv_saldo);

        // Inisialisasi ListView dan Adapter
        ListView listView = findViewById(R.id.transaction_list);
        final TransactionAdapter adapter = new TransactionAdapter(this, R.layout.activity_transaction_item);
        listView.setAdapter(adapter);

        // Inisialisasi ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Mengamati perubahan saldo
        transactionViewModel.getSaldo().observe(this, saldo -> {
            tvSaldo.setText("Saldo: Rp. " + saldo);
        });

        // Mengamati perubahan transaksi dan update adapter
        transactionViewModel.getAllTransactions().observe(this, transactions -> {
            adapter.setTransactions(transactions);
        });

        // Tombol tambah transaksi
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahActivity.class);
            startActivity(intent);
        });
    }
}
