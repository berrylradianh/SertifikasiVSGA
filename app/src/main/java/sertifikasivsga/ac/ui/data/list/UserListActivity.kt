package sertifikasivsga.ac.ui.data.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import sertifikasivsga.ac.R
import sertifikasivsga.ac.helper.DbHelper

class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d("UserListActivity", "UserListActivity started")

        val dbHelper = DbHelper(this)
        val userList = dbHelper.getAllUserNames()

        Log.d("UserListActivity", "User list retrieved: $userList")

        val listView = findViewById<ListView>(R.id.userListView)
        val emptyTextView = findViewById<TextView>(R.id.emptyTextView)

        if (userList.isNullOrEmpty()) {
            listView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        } else {
            listView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
            listView.adapter = adapter
        }
    }

    // Handle the back button action
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
