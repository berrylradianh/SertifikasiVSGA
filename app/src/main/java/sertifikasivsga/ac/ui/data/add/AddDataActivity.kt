package sertifikasivsga.ac.ui.data.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import sertifikasivsga.ac.R
import sertifikasivsga.ac.helper.DbHelper
import java.util.*

class AddDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editTextNama = findViewById<EditText>(R.id.editTextNama)
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
            val nama = editTextNama.text.toString()
            val tanggalLahir = editTextTanggalLahir.text.toString()
            val jenisKelamin = spinnerJenisKelamin.selectedItem.toString()
            val alamat = editTextAlamat.text.toString()

            val dbHelper = DbHelper(this)
            val success = dbHelper.insertUserData(nama, tanggalLahir, jenisKelamin, alamat)

            if (success) {
                finish()
            } else {
                // Handle failure
            }
        }

    }

    // Handle the back button action
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
