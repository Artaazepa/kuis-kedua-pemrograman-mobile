package com.artaazepa.kuis2;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Transaction.class}, version = 1, exportSchema = false)
public abstract class TransactionDatabase extends RoomDatabase {
    private static TransactionDatabase instance;

    public abstract TransactionDao transactionDao();

    public static synchronized TransactionDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TransactionDatabase.class, "transaction_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
