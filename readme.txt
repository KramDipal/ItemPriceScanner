1. With
- QR code scanner
- Database
- Insert table

09/30/24
1. Added intent for both OpenGallery and Qrcode scan in mainActivity
if (requestCode == 49374)

2. Added clearing of imageview, edittext and textfields

3. added KEY_ITEMIMAGE to getUser table.


4. Added GridLayout to limit column display.
5. Also added it to loadImagesFromDatabase
linearLayout.addView(imageView)
val imageView = ImageView(this).apply {
setImageBitmap(imageBitmap)
layoutParams = GridLayout.LayoutParams().apply {
width = 200
height = 200
setMargins(8, 8, 8, 8)
}
scaleType = ImageView.ScaleType.CENTER_CROP
}
gridLayout.addView(imageView)


10/01/24
6. With fir app crash when reloading ALL images.
7. Added exit button in Item search/view and reloading All images.
8. added onImageClick event for every image.
9. Added newWindow on onImageClick
10. added bottom_navigation2
11. Replaced tag value for index as itemName instead of cursor.position(index)
12. When item click on main menu, will display in newWindow.