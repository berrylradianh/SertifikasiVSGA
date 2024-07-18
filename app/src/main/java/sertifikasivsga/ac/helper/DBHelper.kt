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
                + COLUMN_NAMA + " TEXT NOT NULL,"
                + COLUMN_TANGGAL_LAHIR + " TEXT NOT NULL,"
                + COLUMN_JENIS_KELAMIN + " TEXT NOT NULL,"
                + COLUMN_ALAMAT + " TEXT NOT NULL" + ")")
        db.execSQL(SQL_CREATE_USER_TABLE)
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

    fun insertUserData(nama: String, tanggalLahir: String, jenisKelamin: String, alamat: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAMA, nama)
        values.put(COLUMN_TANGGAL_LAHIR, tanggalLahir)
        values.put(COLUMN_JENIS_KELAMIN, jenisKelamin)
        values.put(COLUMN_ALAMAT, alamat)
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        return result != -1L
    }

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "digitaltalent.db"
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NAMA = "nama"
        const val COLUMN_TANGGAL_LAHIR = "tanggal_lahir"
        const val COLUMN_JENIS_KELAMIN = "jenis_kelamin"
        const val COLUMN_ALAMAT = "alamat"
    }
}
