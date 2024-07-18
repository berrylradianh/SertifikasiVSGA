package sertifikasivsga.ac.ui.information

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sertifikasivsga.ac.R

class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Handle the back button action
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
