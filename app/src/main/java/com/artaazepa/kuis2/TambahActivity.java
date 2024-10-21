package com.artaazepa.kuis2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class TambahActivity extends AppCompatActivity {
    private EditText etTanggal, etJumlah, etKeterangan;
    private Spinner spKategori;
    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        // Inisialisasi view
        etTanggal = findViewById(R.id.etTanggal);
        etJumlah = findViewById(R.id.etJumlah);
        etKeterangan = findViewById(R.id.etKeterangan);
        spKategori = findViewById(R.id.spKategori);

        // Inisialisasi ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Inisialisasi Spinner dengan adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kategori_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKategori.setAdapter(adapter);

        // Date picker untuk memilih tanggal
        etTanggal.setOnClickListener(v -> showDatePickerDialog());

        // Tombol simpan
        Button btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(v -> {
            if (validateInput()) {
                String tanggal = etTanggal.getText().toString();
                String keterangan = etKeterangan.getText().toString();
                int jumlah = Integer.parseInt(etJumlah.getText().toString());
                String kategori = spKategori.getSelectedItem().toString();

                // Membuat objek transaksi baru
                Transaction transaction = new Transaction(tanggal, keterangan, jumlah, kategori);

                // Menyimpan transaksi ke dalam database melalui ViewModel
                transactionViewModel.insert(transaction);

                // Tampilkan pesan berhasil dan tutup activity
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // Menampilkan dialog pemilihan tanggal
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            etTanggal.setText(selectedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    // Validasi input sebelum disimpan
    private boolean validateInput() {
        if (etTanggal.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tanggal harus diisi", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etJumlah.getText().toString().isEmpty()) {
            Toast.makeText(this, "Jumlah harus diisi", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Integer.parseInt(etJumlah.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Jumlah harus berupa angka", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etKeterangan.getText().toString().isEmpty()) {
            Toast.makeText(this, "Keterangan harus diisi", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spKategori.getSelectedItem() == null) {
            Toast.makeText(this, "Kategori harus dipilih", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
