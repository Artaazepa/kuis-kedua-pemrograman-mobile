package com.artaazepa.kuis2;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TransactionRepository {
    private static TransactionRepository instance;
    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> allTransactions;
    private Executor executor;

    public TransactionRepository(Application application) {
        TransactionDatabase db = TransactionDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
        executor = Executors.newSingleThreadExecutor();
    }

    public static synchronized TransactionRepository getInstance(Application application) {
        if (instance == null) {
            instance = new TransactionRepository(application);
        }
        return instance;
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return allTransactions;
    }

    public LiveData<Transaction> getTransactionById(int id) {
        return transactionDao.getTransactionById(id);
    }

    public void insert(Transaction transaction) {
        executor.execute(() -> transactionDao.insert(transaction));
    }

    public void update(Transaction transaction) {
        executor.execute(() -> transactionDao.update(transaction));
    }

    public void delete(Transaction transaction) {
        executor.execute(() -> transactionDao.delete(transaction));
    }
}
