package eu.tutorials.mytestbottomnavigator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private val PICK_IMAGE_REQUEST = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        databaseHelper = DatabaseHelper(this)

        //val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        //val btnScan: Button = findViewById(R.id.btnScan)
        //val imageViewQRCode: ImageView = findViewById(R.id.imageViewQRCode)
        val editSearchItem: EditText = findViewById(R.id.editSearchItem)
        val btnSearch: Button = findViewById(R.id.btnSearch)
        val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
        val textViewItemName: TextView = findViewById(R.id.textViewItemName)
        val textViewPrice: TextView = findViewById(R.id.textViewPrice)
        val textViewDescription: TextView = findViewById(R.id.textViewDescription)


        //val displayallitems = layoutInflater.inflate(R.layout.displayallitems, null)
        val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)

        loadImagesFromDatabase(linearLayoutImages)

        btnSearch.setOnClickListener {
            val itemName = editSearchItem.text.toString()

            //Toast.makeText(this,"Button search $itemName", Toast.LENGTH_LONG).show()
            loadImageFromDatabase(itemName, imageViewSearch, textViewItemName, textViewPrice, textViewDescription)
        }

        BottomNavigator()
    }

    private fun BottomNavigator() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Handle home navigation
                    Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show()
                    true
                }

                R.id.scanqrcode -> {
                    //Toast.makeText(this, "Scan QR code Button", Toast.LENGTH_LONG).show()
                    startQRScanner()


                    // Handle dashboard navigation
                    true
                }

                R.id.additem -> {
                    showInputDialog()
                    //Toast.makeText(this, "Add Item Button", Toast.LENGTH_LONG).show()
                    // Handle dashboard navigation
                    true
                }

                R.id.dashboard -> {
                    Toast.makeText(this, "Dashboard Button", Toast.LENGTH_LONG).show()
                    // Handle dashboard navigation
                    true
                }

                R.id.settings -> {
                    // Handle settings navigation
                    Toast.makeText(this, "Settings Button", Toast.LENGTH_LONG).show()
                    true
                }

                else -> false
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showInputDialog() {
        // Inflate the dialog with custom layout
        //val dialogView = layoutInflater.inflate(R.layout.dialog_input, null)

        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.imageView)

        val editItemName = dialogView.findViewById<EditText>(R.id.editItemName)
        val editDescription = dialogView.findViewById<EditText>(R.id.editDescription)
        val editPrice = dialogView.findViewById<EditText>(R.id.editPrice)
        //val btnOpenGallery = dialogView.findViewById<Button>(R.id.btnOpenGallery)


        //btnOpenGallery.setOnClickListener {
        Toast.makeText(this, "Button Gallery!", Toast.LENGTH_LONG).show()
        openGallery()
        // }


    }


    private fun startQRScanner() {
        IntentIntegrator(this).initiateScan()
    }

    /*
    This is qrcode scan
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)


        if (result.contents == null) {

            Toast.makeText(this,"Scan cancelled", Toast.LENGTH_LONG).show()
            // Handle cancellation
        } else {
            // Handle the scanned QR code


            val scannedData = result.contents
            val resultx = scannedData.split(" ")
            val partOne = resultx[0]      // Item



            Log.i("scannedData result","$resultx $partOne")
            //val partTwo = resultx[1]      // Description
            //val partThree = resultx[2]    //Price


            try {
                val searchItemsResult = databaseHelper.getUser(partOne)

                searchItemsResult?.let {
                    Log.i("scannedData result here","$searchItemsResult")
                    showPopup(scannedData, true)
                }


            }catch (e: Exception){
                println("An unexpected error occurred in Viewing a record: ${e.message}")
                //Toast.makeText(this, "No record found!", Toast.LENGTH_LONG).show()
                showPopup(scannedData, false)
            }




            //displayQRCode(scannedData)
            //Toast.makeText(this,"Scan successful $scannedData", Toast.LENGTH_LONG).show()

            // You can display or process the scanned data here
        }
    }

     */


    /*
    For Image display
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i("onActivityResult1","$requestCode $resultCode $data")
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data

            Log.i("onActivityResult2","$imageUri")
            imageUri?.let { uri ->

                Log.i("onActivityResult2-1","HERE")
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                Log.i("onActivityResult2-2","HERE")

                val dialogView = layoutInflater.inflate(R.layout.dialog_input, null)

                Log.i("onActivityResult2-3","HERE")
                val tvByteArray: TextView = dialogView.findViewById(R.id.tvByteArray)
                Log.i("onActivityResult2-4","HERE")
                val imageView: ImageView = dialogView.findViewById(R.id.imageView)

                Log.i("onActivityResult3","HERE")
                imageView.setImageBitmap(bitmap)
                val byteArray = bitmapToByteArray(bitmap)

                Log.i("onActivityResult4","$byteArray")

                tvByteArray.text = byteArray.joinToString(", ")// Display byte array in TextView
                Log.i("onActivityResult2-5","${tvByteArray.text}")

            // Optionally, save the byte array to the database here
            }
        }
    }

     */


    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_input, null)
        val imageView: ImageView = dialogView.findViewById(R.id.imageView)

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data

            imageUri?.let {
                showImageInDialog(imageUri)
            }
            //imageView.setImageURI(imageUri)

        }
    }

    private fun showImageInDialog(imageUri: Uri?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.imageView)

        val editItemName = dialogView.findViewById<EditText>(R.id.editItemName)
        val editDescription = dialogView.findViewById<EditText>(R.id.editDescription)
        val editPrice = dialogView.findViewById<EditText>(R.id.editPrice)
        val tvByteArray = dialogView.findViewById<TextView>(R.id.tvByteArray)


        //Log.i("Items","$editItemName\n $editDescription\n $editPrice\n $tvByteArray\n $imageView")


        imageView.setImageURI(imageUri)
        val drawable = imageView.drawable
        if (drawable != null) {
            Log.e("Errorss", "Drawable is not null")
        } else {
            Log.e("Errorss", "Drawable is null")
        }

        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val byteArray = stream.toByteArray()
        tvByteArray.text = byteArray.toString()


        // Build the dialog
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Enter required details")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Handle the input here
                val input1 = editItemName.text.toString()
                val input2 = editDescription.text.toString()
                val input3 = editPrice.text.toString()
                val input4 = tvByteArray.text.toString()

                Log.i("Items2", "$input1 $input2 $input3 $input4")
                // Process the inputs as needed
                try {
                    val user = Items(0, input1, input2, input3, byteArray)
                    val resultX = databaseHelper.addItem(user)

                } catch (e: Exception) {
                    println("An unexpected error occurred in adding a record: ${e.message}")
                }

                Toast.makeText(this, "Item Added", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        // Show the dialog
        val dialog = dialogBuilder.create()
        dialog.show()

    }


    /*
    fun displayQRCode(data: String) {

        val qrCodeBitmap: Bitmap? = generateQRCodeBitmap(data)
        if (qrCodeBitmap != null) {
            imageViewQRCode.setImageBitmap(qrCodeBitmap)
            imageViewQRCode.visibility = ImageView.VISIBLE
        }
    }

    private fun generateQRCodeBitmap(data: String): Bitmap? {
        val size = 512 // Size of the QR Code
        val bits = com.google.zxing.MultiFormatWriter().encode(data, com.google.zxing.BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bits[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }
        return bitmap
    }*/

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun showPopup(result: String, show: Boolean) {
        val resultx = result.split(" ")
        val partOne = resultx[0]      // Item
        val partTwo = resultx[1]      // Description
        val partThree = resultx[2]    //Price

        Log.i("showPopup", "$partOne $partTwo $partThree $show")


        if (show) {
            // Create the AlertDialog
            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("Product Details")
                .setMessage("Item: $partOne\nDescription: $partTwo\nPrice: $partThree")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            dialogBuilder.create().show()
        } else {
            val dialogBuilder = AlertDialog.Builder(this)
                .setTitle("Product Details")
                .setMessage("Item not found!\nPlease scan another product.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            dialogBuilder.create().show()
        }

        // Show the dialog

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
        }
        cursor.close()

    }

    private fun loadImagesFromDatabase(linearLayout: LinearLayout) {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.query("Items", arrayOf("itemImage"), null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("itemImage"))
                val imageBitmap =
                    BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)

                val imageView = ImageView(this).apply {
                    setImageBitmap(imageBitmap)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, 16)
                    }
                }

                linearLayout.addView(imageView)
            } while (cursor.moveToNext())
        } else {
            Log.e("Error", "No images found in the database")
        }
        cursor.close()


    }
}
