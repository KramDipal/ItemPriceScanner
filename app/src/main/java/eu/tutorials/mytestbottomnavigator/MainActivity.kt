package eu.tutorials.mytestbottomnavigator

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        databaseHelper = DatabaseHelper(this)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        //val btnScan: Button = findViewById(R.id.btnScan)
        //val imageViewQRCode: ImageView = findViewById(R.id.imageViewQRCode)



        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Handle home navigation
                    Toast.makeText(this, "Home Button", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.scanqrcode -> {
                    Toast.makeText(this, "Scan QR code Button", Toast.LENGTH_LONG).show()
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
        val dialogView = layoutInflater.inflate(R.layout.dialog_input, null)

        val editItemName = dialogView.findViewById<EditText>(R.id.editItemName)
        val editDescription = dialogView.findViewById<EditText>(R.id.editDescription)
        val editPrice = dialogView.findViewById<EditText>(R.id.editPrice)

        // Build the dialog
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle("Input Required")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Handle the input here
                val input1 = editItemName.text.toString()
                val input2 = editDescription.text.toString()
                val input3 = editPrice.text.toString()

                Log.i("Items","$input1 $input2 $input3")
                // Process the inputs as needed
                try {
                    val user = Items(0, input1, input2, input3)
                    val resultX = databaseHelper.addItem(user)

                }catch (e: Exception){
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


    private fun startQRScanner() {
        IntentIntegrator(this).initiateScan()
    }

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
            //displayQRCode(scannedData)
            Toast.makeText(this,"Scan successful $scannedData", Toast.LENGTH_LONG).show()

            // You can display or process the scanned data here
        }
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
}
