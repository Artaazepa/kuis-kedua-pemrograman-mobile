package com.artaazepa.kuis2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository transactionRepository;
    private LiveData<List<Transaction>> allTransactions;
    private MutableLiveData<Integer> saldo = new MutableLiveData<>(0); // Inisialisasi saldo

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        // Inisialisasi repository dan transaksi
        transactionRepository = new TransactionRepository(application);
        allTransactions = transactionRepository.getAllTransactions();

        // Menghitung saldo setiap kali ada perubahan transaksi
        allTransactions.observeForever(transactions -> calculateSaldo(transactions));
    }

    // Mendapatkan semua transaksi
    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    // Mendapatkan saldo
    public LiveData<Integer> getSaldo() {
        return saldo;
    }

    // Metode untuk menghitung saldo berdasarkan transaksi
    private void calculateSaldo(List<Transaction> transactions) {
        int totalSaldo = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getKategori().equals("Masuk")) {
                totalSaldo += transaction.getJumlah(); // Menambah jika kategori adalah "Masuk"
            } else if (transaction.getKategori().equals("Keluar")) {
                totalSaldo -= transaction.getJumlah(); // Mengurangi jika kategori adalah "Keluar"
            }
        }
        saldo.setValue(totalSaldo); // Update nilai saldo
    }

    // Metode untuk menambah transaksi baru
    public void insert(Transaction transaction) {
        transactionRepository.insert(transaction);  // Menyimpan transaksi ke database melalui repository
    }
}
