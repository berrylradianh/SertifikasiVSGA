package sertifikasivsga.ac.ui.data.add

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import sertifikasivsga.ac.R
import sertifikasivsga.ac.helper.DbHelper
import sertifikasivsga.ac.ui.data.list.UserListActivity
import java.util.*

class AddDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editTextNama = findViewById<EditText>(R.id.editTextNama)
        val editTextNim = findViewById<EditText>(R.id.editTextNim)
        val editTextTanggalLahir = findViewById<EditText>(R.id.editTextTanggalLahir)
        val spinnerJenisKelamin = findViewById<Spinner>(R.id.spinnerJenisKelamin)
        val editTextAlamat = findViewById<EditText>(R.id.editTextAlamat)
        val buttonSimpan = findViewById<Button>(R.id.buttonSimpan)

        // Set up the spinner
        val jenisKelaminAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_kelamin_array,
            android.R.layout.simple_spinner_item
        )
        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerJenisKelamin.adapter = jenisKelaminAdapter

        // Set up the date picker
        editTextTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                editTextTanggalLahir.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day)
            datePickerDialog.show()
        }

        // Set up the save button
        buttonSimpan.setOnClickListener {
            val nim = editTextNim.text.toString()
            val nama = editTextNama.text.toString()
            val tanggalLahir = editTextTanggalLahir.text.toString()
            val jenisKelamin = spinnerJenisKelamin.selectedItem.toString()
            val alamat = editTextAlamat.text.toString()

            val dbHelper = DbHelper(this)
            val success = dbHelper.insertUserData(nim, nama, tanggalLahir, jenisKelamin, alamat)

            if (success) {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserListActivity::class.java)
                startActivity(intent)
                finish() // Close the AddDataActivity
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle the back button action
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
