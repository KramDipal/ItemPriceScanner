package eu.tutorials.mytestbottomnavigator

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

class newWindow : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_window)


        // This is used to hide the status bar.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        databaseHelper = DatabaseHelper(this)
        val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch2)
        val textViewItemName: TextView = findViewById(R.id.textViewItemName2)
        val textViewPrice: TextView = findViewById(R.id.textViewPrice2)
        val textViewDescription: TextView = findViewById(R.id.textViewDescription2)


        //Toast.makeText(this, "activity_new_window", Toast.LENGTH_LONG).show()
        //Retrieve the index from mainActivity
        //val imageIndex = intent.getIntExtra("IMAGE_INDEX", 0)

        val itemName = intent.getStringExtra("ITEM_NAME")

        Log.i("itemName","$itemName")

        if (itemName != null) {
            loadImageFromDatabase(itemName, imageViewSearch, textViewItemName, textViewPrice, textViewDescription)
        }
        bottomNavigator2()
    }

    fun loadImageFromDatabase(itemName: String, imageView: ImageView, textViewItemName: TextView, textViewPrice: TextView, textViewDescription: TextView) {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "Items",
            arrayOf("itemImage, itemName, itemPrice, itemDescription"),
            "itemName=?",
            arrayOf(itemName),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("itemImage"))
            val item = cursor.getString(cursor.getColumnIndexOrThrow("itemName"))
            val price = cursor.getString(cursor.getColumnIndexOrThrow("itemPrice"))
            val description = cursor.getString(cursor.getColumnIndexOrThrow("itemDescription"))

            val imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)

            imageView.setImageBitmap(imageBitmap)
            textViewItemName.text = "Item Name: " + item
            textViewPrice.text = "Item Price: Php " + price
            textViewDescription.text = "Item Description: " + description


        } else {
            Log.e("Error", "No image found for this ID")
            //Toast.makeText(this,"Item not found!", Toast.LENGTH_LONG).show()

            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("Product Details")
                .setMessage("Item not found!\nPlease search for another product.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss()
                }
            dialogBuilder.create().show()

        }
        cursor.close()

    }

    private fun bottomNavigator2() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation2)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnHome -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    true
                }
                R.id.btnSettings -> {

                    true
                }
                else -> false
            }
        }
    }

}