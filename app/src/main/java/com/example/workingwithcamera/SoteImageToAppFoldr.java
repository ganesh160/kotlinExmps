package com.example.workingwithcamera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class SoteImageToAppFoldr extends AppCompatActivity
{

    //this code for creating a file directory in app package name  store and delete the files
    int CAMERA_RESULT =100;
    Button store_image,delete_button;
    ImageView mImage_view;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sote_image_to_app_foldr);
        delete_button=findViewById(R.id.delete_button);
        store_image=findViewById(R.id.store_image);
        mImage_view=findViewById(R.id.mImage_view);
        store_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                storeImae();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                File out = new File(getFilesDir(), "newImage.jpg");
                if (out.exists())
                {
                    out.delete();
                    Log.d("deletes",""+getFilesDir());
                }
            }
        });
    }
    public void storeImae()
    {
        PackageManager pm = getPackageManager();

        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);
            startActivityForResult(i, CAMERA_RESULT);
        } else {
            Toast.makeText(getBaseContext(), "Camera is not available", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_RESULT) {

            File out = new File(getFilesDir(), "newImage.jpg");

            if(!out.exists())
            {
                Toast.makeText(getBaseContext(), "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }

            Log.d("pathss",""+out.getAbsolutePath());
            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());

            mImage_view.setImageBitmap(mBitmap);

        }
    }
}
