package com.example.workingwithcamera

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.R.attr.data
import android.content.Context
import androidx.core.app.NotificationCompat.getExtras
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.nio.file.Files.createFile
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity()
{


    //this code to create a separate file and create and delete a new files

    lateinit var capture_photo:Button
    lateinit var delete_button:Button
    lateinit var mImage_view:ImageView
    lateinit var bitmap:Bitmap
    private val CAMERA_REQUEST_CODE = 12345

    lateinit var   photoFile:File
    private lateinit var imageFilePath:String
    private var mCurrentPhotoPath: String? = null;

    lateinit var uri:Uri

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        capture_photo= findViewById(R.id.capture_photo)
        mImage_view=findViewById(R.id.mImage_view)
        delete_button=findViewById(R.id.delete_button)
        capture_photo.setOnClickListener { v ->
            captureImageLogic()
        }
        delete_button.setOnClickListener { v->

            val f1=File(imageFilePath)

            if(f1.exists())
            {
                f1.delete()
                Toast.makeText(this,"deleted success",Toast.LENGTH_LONG).show()
            }
        }
    }


    fun captureImageLogic()
    {
       /* val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)*/


        val pictureIntent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null)
        {
            //Create a file to store the image
            try {
                photoFile = createImageFile();
            } catch (e : IOException)
            {

            }
            if (photoFile != null) {
                val photoURI :Uri  = FileProvider.getUriForFile(this,"com.example.workingwithcamera.provider", photoFile)
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);
                startActivityForResult(pictureIntent,CAMERA_REQUEST_CODE)
            }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if (resultCode== Activity.RESULT_OK)
        {
            when (requestCode)
            {
                CAMERA_REQUEST_CODE ->
                {
                    Toast.makeText(applicationContext,""+imageFilePath,Toast.LENGTH_SHORT).show()
                    Log.e("path",""+imageFilePath)
                }
            }
        }
    }

    fun createDir(context: Context, dirName: String): File
    {
        val file = File(context.filesDir.toString() + File.separator + dirName)
        if (!file.exists()) {
            file.mkdir()
        }
        return file
    }


    @Throws(IOException::class)
    private fun createImageFile(): File
    {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, /* prefix */".jpg", /* suffix */storageDir      /* directory */)

        imageFilePath = image.absolutePath
        return image
    }

}
