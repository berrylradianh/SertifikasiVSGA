package sertifikasivsga.ac.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_NAMA + " TEXT,"
                + COLUMN_NIM + " TEXT,"
                + COLUMN_TANGGAL_LAHIR + " TEXT,"
                + COLUMN_JENIS_KELAMIN + " TEXT,"
                + COLUMN_ALAMAT + " TEXT" + ")")
        db.execSQL(SQL_CREATE_USER_TABLE)

        // Seeder - Insert initial data
        seedData(db)
    }

    private fun seedData(db: SQLiteDatabase) {
        val users = listOf(
            ContentValues().apply {
                put(COLUMN_EMAIL, "berrylradian@gmail.com")
                put(COLUMN_PASSWORD, "123456789")
                put(COLUMN_NAMA, "Berryl Radian")
                put(COLUMN_NIM, "1235487532")
                put(COLUMN_TANGGAL_LAHIR, "2000-01-01")
                put(COLUMN_JENIS_KELAMIN, "Laki-laki")
                put(COLUMN_ALAMAT, "Alamat berryl radian hamesha")
            },
            ContentValues().apply {
                put(COLUMN_EMAIL, "dimas@exampler.com")
                put(COLUMN_PASSWORD, "123456789")
                put(COLUMN_NAMA, "Dimas Rizqi")
                put(COLUMN_NIM, "231564645848")
                put(COLUMN_TANGGAL_LAHIR, "2001-02-02")
                put(COLUMN_JENIS_KELAMIN, "Perempuan")
                put(COLUMN_ALAMAT, "Alamat Dimas Rizqi")
            }
        )

        for (user in users) {
            val result = db.insert(TABLE_USERS, null, user)
            if (result == -1L) {
                Log.e("DbHelper", "Error inserting user: $user")
            } else {
                Log.d("DbHelper", "User inserted: $user")
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun registerUser(email: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_PASSWORD, password)
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_USERS, arrayOf(COLUMN_ID), "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?", arrayOf(email, password), null, null, null)
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun insertUserData(nim: String, nama: String, tanggalLahir: String, jenisKelamin: String, alamat: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NIM, nim)
        values.put(COLUMN_NAMA, nama)
        values.put(COLUMN_TANGGAL_LAHIR, tanggalLahir)
        values.put(COLUMN_JENIS_KELAMIN, jenisKelamin)
        values.put(COLUMN_ALAMAT, alamat)
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    fun getAllUserNames(): List<String> {
        val userNames = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_USERS, arrayOf(COLUMN_NAMA), null, null, null, null, "$COLUMN_ID ASC")

        Log.d("DbHelper", "Cursor count: ${cursor.count}")

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA))
                if (name != null) {
                    Log.d("DbHelper", "User name retrieved: $name")
                    userNames.add(name)
                } else {
                    Log.e("DbHelper", "Null name found in database")
                }
            } while (cursor.moveToNext())
        } else {
            Log.e("DbHelper", "No data found in database")
        }

        cursor.close()
        db.close()
        Log.d("DbHelper", "All user names retrieved: $userNames")
        return userNames
    }


    companion object {
        private const val DATABASE_VERSION = 8
        private const val DATABASE_NAME = "digitaltalent.db"
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NAMA = "nama"
        const val COLUMN_NIM = "nim"
        const val COLUMN_TANGGAL_LAHIR = "tanggal_lahir"
        const val COLUMN_JENIS_KELAMIN = "jenis_kelamin"
        const val COLUMN_ALAMAT = "alamat"
    }
}