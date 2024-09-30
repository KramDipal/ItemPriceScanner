package eu.tutorials.mytestbottomnavigator

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "InventoryDatabase"
        private const val TABLE_USERS = "Items"
        private const val KEY_ID = "id"
        private const val KEY_ITEMNAME = "itemName"
        private const val KEY_ITEMDESC = "itemDescription"
        private const val KEY_ITEMPRICE = "itemPrice"
        private const val KEY_ITEMIMAGE = "itemImage"

    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_USERS ("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_ITEMNAME TEXT,"
                + "$KEY_ITEMDESC TEXT,"
                + "$KEY_ITEMPRICE TEXT, $KEY_ITEMIMAGE BLOB)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addItem(user: Items): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(KEY_ITEMNAME, user.itemName)
            put(KEY_ITEMDESC, user.itemDescription)
            put(KEY_ITEMPRICE, user.itemPrice)
            put(KEY_ITEMIMAGE, user.itemImage)
        }
        val success = db.insert(TABLE_USERS, null, contentValues)
        //db.close()
        return success
    }

    //fun getUser(txnID: String, lotID: String, txnDate: String): User? {
    fun getUser(itemName: String): Items? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(TABLE_USERS, arrayOf(KEY_ID,
            KEY_ITEMNAME,
            KEY_ITEMDESC,
            KEY_ITEMPRICE,
            KEY_ITEMIMAGE), "$KEY_ITEMNAME=?", arrayOf(itemName), null, null, null)
        cursor.moveToFirst()
        //cursor.moveToLast()
        val items = Items(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getBlob(4))
        cursor.close()
        return items
    }


}
