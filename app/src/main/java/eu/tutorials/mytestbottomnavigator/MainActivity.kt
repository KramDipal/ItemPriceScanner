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
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private var PICK_IMAGE_REQUEST = 1


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        databaseHelper = DatabaseHelper(this)

        //val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        //val btnScan: Button = findViewById(R.id.btnScan)
        //val imageViewQRCode: ImageView = findViewById(R.id.imageViewQRCode)
        val editSearchItem: EditText = findViewById(R.id.editSearchItem)
        val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
        val textViewItemName: TextView = findViewById(R.id.textViewItemName)
        val textViewPrice: TextView = findViewById(R.id.textViewPrice)
        val textViewDescription: TextView = findViewById(R.id.textViewDescription)

        val btnSearch: Button = findViewById(R.id.btnSearch)
        val btnExitViewSearch: Button = findViewById(R.id.btnExitViewSearch)


        //Load all images in IDLE state
        //val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)
        val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)
        val gridLayout: GridLayout = linearLayoutImages.findViewById(R.id.gridLayout)
        loadImagesFromDatabase(gridLayout)

        btnExitViewSearch.setOnClickListener {
            // Handle home navigation
            Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show()
            clearAll(imageViewSearch, editSearchItem, textViewItemName, textViewPrice, textViewDescription)

            try {
                val newGridLayout = GridLayout(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    columnCount = 4 // Example column count
                    id = R.id.gridLayout // Use the same ID
                }
                // Add the new GridLayout to the parent layout
                linearLayoutImages.addView(newGridLayout)
                loadImagesFromDatabase(newGridLayout)
            }catch (e: Exception){
                println("An unexpected error occurred upon click on HOME button: ${e.message}")
            }
        }

        btnSearch.setOnClickListener {
            val itemName = editSearchItem.text.toString()

            clearAll(imageViewSearch, editSearchItem, textViewItemName, textViewPrice, textViewDescription)


            //Toast.makeText(this,"Button search $itemName", Toast.LENGTH_LONG).show()
            try {
                loadImageFromDatabase(itemName, imageViewSearch, textViewItemName, textViewPrice, textViewDescription)


            }catch (e: Exception)
            {
                println("An unexpected error occurred in Viewing a record: ${e.message}")
            }

        }
        editSearchItem.setOnClickListener {

            try {
                //val parent = linearLayoutImages.parent as ViewGroup
                //parent.removeView(linearLayoutImages)

                val parent = gridLayout.parent as ViewGroup
                parent.removeView(gridLayout)


            }catch (e: Exception){
                println("An unexpected error occurred upon click on search Item: ${e.message}")
            }


            Toast.makeText(this, "editSearchItem clicked", Toast.LENGTH_LONG).show()
        }

        BottomNavigator()
    }

    private fun BottomNavigator() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {

                    val editSearchItem: EditText = findViewById(R.id.editSearchItem)
                    val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
                    val textViewItemName: TextView = findViewById(R.id.textViewItemName)
                    val textViewPrice: TextView = findViewById(R.id.textViewPrice)
                    val textViewDescription: TextView = findViewById(R.id.textViewDescription)
                    //val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)

                    // Handle home navigation
                    Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show()
                    clearAll(imageViewSearch, editSearchItem, textViewItemName, textViewPrice, textViewDescription)

                    try {

                        val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)
                        val newGridLayout = GridLayout(this).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            columnCount = 4 // Example column count
                            id = R.id.gridLayout // Use the same ID
                        }

                        // Add the new GridLayout to the parent layout
                        linearLayoutImages.addView(newGridLayout)
                        loadImagesFromDatabase(newGridLayout)


                    }catch (e: Exception){
                        println("An unexpected error occurred upon click on HOME button: ${e.message}")
                    }


                    true
                }

                R.id.scanqrcode -> {
                    //Toast.makeText(this, "Scan QR code Button", Toast.LENGTH_LONG).show()
                    val editSearchItem: EditText = findViewById(R.id.editSearchItem)
                    val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
                    val textViewItemName: TextView = findViewById(R.id.textViewItemName)
                    val textViewPrice: TextView = findViewById(R.id.textViewPrice)
                    val textViewDescription: TextView = findViewById(R.id.textViewDescription)
                    //val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)

                    // Handle home navigation
                    Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show()
                    clearAll(imageViewSearch, editSearchItem, textViewItemName, textViewPrice, textViewDescription)

                    startQRScanner()

                    // Handle dashboard navigation
                    true
                }

                R.id.additem -> {
                    val editSearchItem: EditText = findViewById(R.id.editSearchItem)
                    val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
                    val textViewItemName: TextView = findViewById(R.id.textViewItemName)
                    val textViewPrice: TextView = findViewById(R.id.textViewPrice)
                    val textViewDescription: TextView = findViewById(R.id.textViewDescription)
                    //val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)

                    // Handle home navigation
                    Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show()
                    clearAll(imageViewSearch, editSearchItem, textViewItemName, textViewPrice, textViewDescription)

                    showInputDialog()


                    //Toast.makeText(this, "Add Item Button", Toast.LENGTH_LONG).show()
                    // Handle dashboard navigation
                    true
                }

                R.id.dashboard -> {

                    Toast.makeText(this, "Dashboard Button", Toast.LENGTH_LONG).show()

                    val editSearchItem: EditText = findViewById(R.id.editSearchItem)
                    val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
                    val textViewItemName: TextView = findViewById(R.id.textViewItemName)
                    val textViewPrice: TextView = findViewById(R.id.textViewPrice)
                    val textViewDescription: TextView = findViewById(R.id.textViewDescription)
                    //val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)

                    // Handle home navigation
                    Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show()
                    clearAll(imageViewSearch, editSearchItem, textViewItemName, textViewPrice, textViewDescription)

                    // Handle dashboard navigation
                    true
                }

                R.id.settings -> {
                    // Handle settings navigation
                    Toast.makeText(this, "Settings Button", Toast.LENGTH_LONG).show()

                    val editSearchItem: EditText = findViewById(R.id.editSearchItem)
                    val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
                    val textViewItemName: TextView = findViewById(R.id.textViewItemName)
                    val textViewPrice: TextView = findViewById(R.id.textViewPrice)
                    val textViewDescription: TextView = findViewById(R.id.textViewDescription)
                    //val linearLayoutImages = findViewById<LinearLayout>(R.id.linearLayoutImages)

                    // Handle home navigation
                    Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show()
                    clearAll(imageViewSearch, editSearchItem, textViewItemName, textViewPrice, textViewDescription)


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

        //val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        //val imageView = dialogView.findViewById<ImageView>(R.id.imageView)

        //val editItemName = dialogView.findViewById<EditText>(R.id.editItemName)
        //val editDescription = dialogView.findViewById<EditText>(R.id.editDescription)
        //val editPrice = dialogView.findViewById<EditText>(R.id.editPrice)
        //val btnOpenGallery = dialogView.findViewById<Button>(R.id.btnOpenGallery)


        //btnOpenGallery.setOnClickListener {
        //Toast.makeText(this, "Button Gallery!", Toast.LENGTH_LONG).show()
        openGallery()
        // }
    }


    private fun startQRScanner() {
        IntentIntegrator(this).initiateScan()
        //startActivityForResult(intent, PICK_QRCODE_REQUEST)
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    //This is for qrcode scan
/*
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        Log.i("scannedData result","$requestCode $resultCode $data")
        Log.i("scannedData result","$result")

        if (result.contents == null) {

            Toast.makeText(this,"Scan cancelled", Toast.LENGTH_LONG).show()
            // Handle cancellation
        } else {
            // Handle the scanned QR code


            val scannedData = result.contents
            val resultx = scannedData.split(" ")
            val partOne = resultx[0]      // Item



           // Log.i("scannedData result","$resultx $partOne")
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




    //for common intent of qrcode and open gallery

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

        Log.i("onActivityResult/Start","$requestCode $resultCode $data")

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data

            Log.i("onActivityResult/PICK_IMAGE_REQUEST","$requestCode")

            imageUri?.let {
                showImageInDialog(imageUri)
            }
            //imageView.setImageURI(imageUri)

        }
        if (requestCode == 49374)
        {
            val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            Log.i("onActivityResult/PICK_QRCODE_REQUEST","$result")

            if (result.contents == null) {

                Toast.makeText(this,"Scan cancelled", Toast.LENGTH_LONG).show()
                // Handle cancellation
            } else {
                val scannedData = result.contents
                val resultx = scannedData.split(" ")
                val partOne = resultx[0]      // Item

                Log.i("scannedData result","$resultx $partOne")

                try {
                    val searchItemsResult = databaseHelper.getUser(partOne)

                    searchItemsResult?.let {
                        Log.i("scannedData result here","$searchItemsResult")
                        showPopup(scannedData, true)
                    }


                }catch (e: Exception){
                    println("An unexpected error occurred in Viewing a record: ${e.message}")
                    showPopup(scannedData, false)
                }

            }
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

    private fun loadImagesFromDatabase(gridLayout: GridLayout) {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.query("Items", arrayOf("itemImage"), null, null, null, null, null)


        //val gridLayout = findViewById<GridLayout>(R.id.gridLayout)

        Log.i("loadImagesFromDatabase","$gridLayout")

        if (cursor.moveToFirst()) {
            do {
                val imageByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("itemImage"))
                val imageBitmap =
                    BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)

                val imageView = ImageView(this).apply {
                    setImageBitmap(imageBitmap)
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 200
                        height = 200
                        setMargins(8, 8, 8, 8)
                    }
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    //tag = cursor.moveToFirst() // Set a unique tag/ get the index of each record
                    Log.d("SQLiteData", "Index: ${cursor.position}")
                    tag = cursor.position

                    setOnClickListener {view ->
                        onImageClick(view)
                    }
                }
                gridLayout.addView(imageView)
                //linearLayout.addView(imageView)
            } while (cursor.moveToNext())
        } else {
            Log.e("Error", "No images found in the database")
        }
        cursor.close()


    }

    private fun onImageClick(view: View) {
        val imageView = view as ImageView
        // Example action: display a toast with image info

        val imageIndex = imageView.tag as Int // Retrieve the tag

        Toast.makeText(this, "Image clicked! $imageIndex", Toast.LENGTH_SHORT).show()


        // You can also use the index to perform specific actions based on which image was clicked
        when (imageIndex) {
            0 -> { /* Handle first image click */
                openNewActivity()
            }
            1 -> { /* Handle second image click */
                openNewActivity()}
            2 -> { /* Handle third image click */
                openNewActivity()}
        }
    }

    private fun openNewActivity() {
        val intent = Intent(this, newWindow::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearAll(imageview: ImageView, editSearchItem: TextView, textViewItemName: TextView, textViewPrice: TextView, textViewDescription: TextView){
        imageview.setImageDrawable(null)
        textViewItemName.text = null
        textViewPrice.text = null
        textViewDescription.text = null
        editSearchItem.text = null

    }

}
